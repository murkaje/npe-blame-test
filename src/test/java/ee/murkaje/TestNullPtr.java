package ee.murkaje;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.Test;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

//TODO: Implicit null checks(invokevirtual getClass() + pop)(Objects.requireNonNull in 9+) for indy, inner <init>, etc.
public class TestNullPtr {

  private static AtomicInteger counter = new AtomicInteger(0);

  // For testing odd method signatures
  public <T extends Map<String, String>> void weirdMethod(T arg, Class<? super Object> arg2) throws IllegalStateException {

  }

  @SuppressWarnings("unchecked")
  public GeneratedBase<String> genTestClass(String runMethodSrc) throws Exception {
    ClassPool cp = ClassPool.getDefault();
    CtClass cc = cp.makeClass("ee.murkaje.TestNullPtr$Gen" + counter.getAndIncrement());

    cp.importPackage("ee.murkaje");
    cc.addInterface(cp.get(GeneratedBase.class.getName()));

    cc.addMethod(CtMethod.make("public void consume(Object o) {" + runMethodSrc + "}", cc));

    Path classDebugPath = Paths.get("target/generated/ee/murkaje/TestNullPtr$Gen.class");
    Files.createDirectories(classDebugPath.getParent());
    Files.write(classDebugPath, cc.toBytecode());

    return (GeneratedBase<String>) cc.toClass().newInstance();
  }

  @Test
  public void testLocalVariable() throws Exception {
    GeneratedBase<String> testClass = genTestClass("" +
        "String myString = null;" +
        "myString.toLowerCase();");

    NullPointerException npe = assertThrows(NullPointerException.class, () -> testClass.consume(null));
    npe.printStackTrace(System.out);

    assertEquals("Invoking java.lang.String#toLowerCase on null local variable in slot 2", npe.getMessage());
  }

  @Test
  public void testLocalVariableWithDebug() throws Exception {
    GeneratedBase<String> testClass = genTestClass("");

    NullPointerException npe = assertThrows(NullPointerException.class, () -> testClass.generateNpeDebug(""));
    npe.printStackTrace(System.out);

    assertEquals("Invoking java.lang.String#toLowerCase on null local variable myVar:java.lang.String", npe.getMessage());
  }

  @Test
  public void testMethodParam() throws Exception {
    GeneratedBase<String> testClass = genTestClass("" +
        "((String)$1).toLowerCase();");

    NullPointerException npe = assertThrows(NullPointerException.class, () -> testClass.consume(null));
    npe.printStackTrace(System.out);

    assertEquals("Invoking java.lang.String#toLowerCase on null method parameter at index 1", npe.getMessage());
  }

  @Test
  public void testChainedMethod() throws Exception {
    GeneratedBase<String> testClass = genTestClass("" +
        "getNullString().toLowerCase();");

    NullPointerException npe = assertThrows(NullPointerException.class, () -> testClass.consume(null));
    npe.printStackTrace(System.out);

    //Generated class has changing index, split before and after it
    assertTrue(npe.getMessage().startsWith("Invoking java.lang.String#toLowerCase on null object returned from ee.murkaje.TestNullPtr$Gen"));
    assertTrue(npe.getMessage().endsWith("#getNullString"));
  }

  @Test
  public void testChainedMethodWithDebug() throws Exception {
    GeneratedBase<String> testClass = genTestClass("");

    NullPointerException npe = assertThrows(NullPointerException.class, testClass::generateNpeDebugMethodChaining);
    npe.printStackTrace(System.out);

    assertEquals("Invoking java.lang.String#compareTo on null object returned from ee.murkaje.GeneratedBase#getNullString", npe.getMessage());
  }

  @Test
  public void testExplicitNullCheck() throws Exception {
    GeneratedBase<String> testClass = genTestClass("" +
        "String nullString = null;" +
        "if(nullString == null) {" +
        "  throw new NullPointerException();" +
        "}");

    NullPointerException npe = assertThrows(NullPointerException.class, () -> testClass.consume(null));
    npe.printStackTrace(System.out);

    assertEquals("Explicitly thrown after null check of local variable in slot 2", npe.getMessage());
  }

  @Test
  public void testArrayStore() throws Exception {
    GeneratedBase<String> testClass = genTestClass("" +
        "String[] myStrings = getNullStringArray();" +
        "String str = getEmptyString();" +
        "myStrings[0] = str;");

    NullPointerException npe = assertThrows(NullPointerException.class, () -> testClass.consume(null));
    npe.printStackTrace(System.out);

    assertEquals("Storing array value to null local variable in slot 2", npe.getMessage());
  }

  @Test
  public void testArrayLength() throws Exception {
    GeneratedBase<String> testClass = genTestClass("" +
        "String[] myStrings = getNullStringArray();" +
        "myStrings.length;");

    NullPointerException npe = assertThrows(NullPointerException.class, () -> testClass.consume(null));
    npe.printStackTrace(System.out);

    assertEquals("Getting array length of null local variable in slot 2", npe.getMessage());
  }

  @Test
  public void testNullInstanceFieldGet() throws Exception {
    GeneratedBase<String> testClass = genTestClass("" +
        "TestClass test = null;" +
        "test.stringField;");

    NullPointerException npe = assertThrows(NullPointerException.class, () -> testClass.consume(null));
    npe.printStackTrace(System.out);

    assertEquals("Getting field ee.murkaje.TestClass.stringField of null local variable in slot 2", npe.getMessage());
  }

  @Test
  public void testInstanceFieldPut() throws Exception {
    GeneratedBase<String> testClass = genTestClass("" +
        "TestClass test = null;" +
        "test.stringField = \"\";");

    NullPointerException npe = assertThrows(NullPointerException.class, () -> testClass.consume(null));
    npe.printStackTrace(System.out);

    assertEquals("Setting field ee.murkaje.TestClass.stringField of null local variable in slot 2", npe.getMessage());
  }

  @Test
  public void testInstanceNullFieldGet() throws Exception {
    GeneratedBase<String> testClass = genTestClass("" +
        "TestClass test = new TestClass();" +
        "test.stringField.toLowerCase();");

    NullPointerException npe = assertThrows(NullPointerException.class, () -> testClass.consume(null));
    npe.printStackTrace(System.out);

    assertEquals("Invoking java.lang.String#toLowerCase on null instance field ee.murkaje.TestClass.stringField", npe.getMessage());
  }

  @Test
  public void testStaticFieldGet() throws Exception {
    GeneratedBase<String> testClass = genTestClass("" +
        "TestClass.staticStringField.toLowerCase();");

    NullPointerException npe = assertThrows(NullPointerException.class, () -> testClass.consume(null));
    npe.printStackTrace(System.out);

    assertEquals("Invoking java.lang.String#toLowerCase on null static field ee.murkaje.TestClass.staticStringField", npe.getMessage());
  }

  @Test
  public void testNullExceptionThrow() throws Exception {
    GeneratedBase<String> testClass = genTestClass("");
    NullPointerException npe = assertThrows(NullPointerException.class, testClass::throwNull);
    npe.printStackTrace(System.out);

    assertEquals("Throwing null exception object", npe.getMessage());
  }

  @Test
  public void testMethodReference() throws Exception {
    GeneratedBase<String> testClass = genTestClass("" +
        "TestClass testClass = null;" +
        "TestClass.invokeEmptyMethod(testClass);");

    NullPointerException npe = assertThrows(NullPointerException.class, () -> testClass.consume(null));
    npe.printStackTrace(System.out);

    assertEquals("Getting method reference ee.murkaje.TestClass::emptyMethod on null method parameter test", npe.getMessage());
  }
}
