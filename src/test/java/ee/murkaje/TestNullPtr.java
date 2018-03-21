package ee.murkaje;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

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
  public static GeneratedBase genTestClass(String runMethodSrc) throws Exception {
    ClassPool cp = ClassPool.getDefault();
    CtClass cc = cp.makeClass("ee.murkaje.TestNullPtr$Gen" + counter.getAndIncrement());

    cp.importPackage("ee.murkaje");
    cc.addInterface(cp.get(GeneratedBase.class.getName()));

    cc.addMethod(CtMethod.make("public void consume(Object o) {" + runMethodSrc + "}", cc));

    Path classDebugPath = Paths.get("target/generated/ee/murkaje/TestNullPtr$Gen.class");
    Files.createDirectories(classDebugPath.getParent());
    Files.write(classDebugPath, cc.toBytecode());

    return (GeneratedBase) cc.toClass().getDeclaredConstructor().newInstance();
  }

  public static void assertNpeMessage(Runnable testCase, String expectedMessage) {
    assertNpeMessage(testCase, exceptionMessage -> assertEquals(expectedMessage, exceptionMessage));
  }

  public static void assertNpeMessage(Runnable testCase, Consumer<String> messageVerifier) {
    NullPointerException npe = assertThrows(NullPointerException.class, testCase::run);
    npe.printStackTrace(System.out);
    messageVerifier.accept(npe.getMessage());
  }

  @Test
  public void testLocalVariable() throws Exception {
    GeneratedBase testClass = genTestClass("" +
        "String myString = null;" +
        "myString.toLowerCase();");

    assertNpeMessage(testClass::run, "Invoking java.lang.String#toLowerCase on null local variable in slot 2");
  }

  @Test
  public void testLocalVariableWithDebug() throws Exception {
    GeneratedBase testClass = genTestClass("");

    assertNpeMessage(testClass::generateNpeDebug, "Invoking java.lang.String#toLowerCase on null local variable myVar:java.lang.String");
  }

  @Test
  public void testMethodParam() throws Exception {
    GeneratedBase testClass = genTestClass("" +
        "((String)$1).toLowerCase();");

    assertNpeMessage(testClass::run, "Invoking java.lang.String#toLowerCase on null method parameter at index 1");
  }

  @Test
  public void testChainedMethod() throws Exception {
    GeneratedBase testClass = genTestClass("" +
        "getNullString().toLowerCase();");

    NullPointerException npe = assertThrows(NullPointerException.class, testClass::run);
    npe.printStackTrace(System.out);

    //Generated class has changing index, split before and after it
    assertTrue(npe.getMessage().startsWith("Invoking java.lang.String#toLowerCase on null object returned from ee.murkaje.TestNullPtr$Gen"));
    assertTrue(npe.getMessage().endsWith("#getNullString"));
  }

  @Test
  public void testChainedMethodWithDebug() throws Exception {
    GeneratedBase testClass = genTestClass("");

    assertNpeMessage(testClass::generateNpeDebugMethodChaining,
        "Invoking java.lang.String#compareTo on null object returned from ee.murkaje.GeneratedBase#getNullString");
  }

  @Test
  public void testTernaryBranchMethodChaining() throws Exception {
    GeneratedBase testClass = genTestClass("" +
        "boolean test = true;" +
        "(test ? GeneratedBase.super.getNullString() : GeneratedBase.super.getEmptyString()).toLowerCase();");

    assertNpeMessage(testClass::run, "Invoking java.lang.String#toLowerCase on null object returned from ee.murkaje.GeneratedBase#getNullString");
  }

  @Test
  public void testExplicitNullCheck() throws Exception {
    GeneratedBase testClass = genTestClass("" +
        "String nullString = null;" +
        "if(nullString == null) {" +
        "  throw new NullPointerException();" +
        "}");

    assertNpeMessage(testClass::run, "Explicitly thrown after null check of local variable in slot 2");
  }

  @Test
  public void testArrayStore() throws Exception {
    GeneratedBase testClass = genTestClass("" +
        "String[] myStrings = getNullStringArray();" +
        "String str = getEmptyString();" +
        "myStrings[0] = str;");

    assertNpeMessage(testClass::run, "Storing array value to null local variable in slot 2");
  }

  @Test
  public void testArrayLength() throws Exception {
    GeneratedBase testClass = genTestClass("" +
        "String[] myStrings = getNullStringArray();" +
        "myStrings.length;");

    assertNpeMessage(testClass::run, "Getting array length of null local variable in slot 2");
  }

  @Test
  public void testNullInstanceFieldGet() throws Exception {
    GeneratedBase testClass = genTestClass("" +
        "TestClass test = null;" +
        "test.stringField;");

    assertNpeMessage(testClass::run, "Getting field ee.murkaje.TestClass.stringField of null local variable in slot 2");
  }

  @Test
  public void testInstanceFieldPut() throws Exception {
    GeneratedBase testClass = genTestClass("" +
        "TestClass test = null;" +
        "test.stringField = \"\";");

    assertNpeMessage(testClass::run, "Setting field ee.murkaje.TestClass.stringField of null local variable in slot 2");
  }

  @Test
  public void testInstanceNullFieldGet() throws Exception {
    GeneratedBase testClass = genTestClass("" +
        "TestClass test = new TestClass();" +
        "test.stringField.toLowerCase();");

    assertNpeMessage(testClass::run, "Invoking java.lang.String#toLowerCase on null instance field ee.murkaje.TestClass.stringField");
  }

  @Test
  public void testStaticFieldGet() throws Exception {
    GeneratedBase testClass = genTestClass("" +
        "TestClass.staticStringField.toLowerCase();");

    assertNpeMessage(testClass::run, "Invoking java.lang.String#toLowerCase on null static field ee.murkaje.TestClass.staticStringField");
  }

  @Test
  public void testNullExceptionThrow() throws Exception {
    GeneratedBase testClass = genTestClass("");
    assertNpeMessage(testClass::throwNull, "Throwing null exception object");
  }

  @Test
  public void testMethodReference() throws Exception {
    GeneratedBase testClass = genTestClass("" +
        "TestClass testClass = null;" +
        "TestClass.invokeEmptyMethod(testClass);");

    assertNpeMessage(testClass::run, "Getting method reference ee.murkaje.TestClass::emptyMethod on null method parameter test");
  }
}
