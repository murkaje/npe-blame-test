package ee.murkaje;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.objectweb.asm.Opcodes.ACC_PUBLIC;
import static org.objectweb.asm.Opcodes.ACONST_NULL;
import static org.objectweb.asm.Opcodes.ALOAD;
import static org.objectweb.asm.Opcodes.INVOKEINTERFACE;
import static org.objectweb.asm.Opcodes.INVOKESPECIAL;
import static org.objectweb.asm.Opcodes.INVOKEVIRTUAL;
import static org.objectweb.asm.Opcodes.POP;
import static org.objectweb.asm.Opcodes.RETURN;
import static org.objectweb.asm.Opcodes.SWAP;
import static org.objectweb.asm.Opcodes.V1_8;

import java.lang.invoke.MethodHandles;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

import org.junit.jupiter.api.Test;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

//TODO: Implicit null checks(invokevirtual getClass() + pop)(Objects.requireNonNull in 9+) for indy, inner <init>, etc.
public class TestNullPtr {

  private static AtomicInteger counter = new AtomicInteger(0);

  @SuppressWarnings("unchecked")
  private static GeneratedBase genTestClass(String runMethodSrc) throws Exception {
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

  @SuppressWarnings("unchecked")
  private static GeneratedBase genTestClass(Consumer<MethodVisitor> codeVisitor) throws Exception {
    ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
    cw.visit(V1_8,
        ACC_PUBLIC,
        "ee/murkaje/TestNullPtr$Gen" + counter.getAndIncrement(),
        null,
        "java/lang/Object",
        new String[]{GeneratedBase.class.getName().replace('.', '/')});

    MethodVisitor constructor = cw.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);

    constructor.visitCode();
    constructor.visitVarInsn(ALOAD, 0);
    constructor.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
    constructor.visitInsn(RETURN);
    constructor.visitMaxs(-1, -1);
    constructor.visitEnd();

    MethodVisitor consumeMethod = cw.visitMethod(ACC_PUBLIC, "consume", "(Ljava/lang/Object;)V", null, null);

    consumeMethod.visitCode();
    codeVisitor.accept(consumeMethod);
    consumeMethod.visitMaxs(-1, -1);
    consumeMethod.visitEnd();

    cw.visitEnd();

    byte[] bytes = cw.toByteArray();
    MethodHandles.Lookup lookup = MethodHandles.lookup();
    Class<? extends GeneratedBase> generatedClass = (Class<? extends GeneratedBase>) lookup.defineClass(bytes);
    return generatedClass.getDeclaredConstructor().newInstance();
  }

  private static void assertNpeMessage(Runnable testCase, String expectedMessage) {
    assertNpeMessage(testCase, exceptionMessage -> assertEquals(expectedMessage, exceptionMessage));
  }

  private static void assertNpeMessage(Runnable testCase, Consumer<String> messageVerifier) {
    NullPointerException npe = assertThrows(NullPointerException.class, testCase::run);

    StackTraceElement[] filteredTrace = Arrays.stream(npe.getStackTrace())
        .filter(e -> e.getClassName().startsWith("ee.murkaje"))
        .toArray(StackTraceElement[]::new);

    npe.setStackTrace(filteredTrace);
    npe.printStackTrace(System.out);

    messageVerifier.accept(npe.getMessage());
  }

  @Test
  void testConstant() throws Exception {
    GeneratedBase testClass = genTestClass("" +
        "((String)null).toLowerCase();");

    assertNpeMessage(testClass::run, "Invoking java.lang.String#toLowerCase on null constant");
  }

  @Test
  void testLocalVariable() throws Exception {
    GeneratedBase testClass = genTestClass("" +
        "String myString = null;" +
        "myString.toLowerCase();");

    assertNpeMessage(testClass::run, "Invoking java.lang.String#toLowerCase on null local variable in slot 2");
  }

  @Test
  void testLocalVariableWithDebug() throws Exception {
    GeneratedBase testClass = genTestClass("");

    assertNpeMessage(testClass::generateNpeDebug, "Invoking java.lang.String#toLowerCase on null local variable myVar:java.lang.String");
  }

  @Test
  void testMethodParam() throws Exception {
    GeneratedBase testClass = genTestClass("" +
        "((String)$1).toLowerCase();");

    assertNpeMessage(testClass::run, "Invoking java.lang.String#toLowerCase on null method parameter at index 1");
  }

  @Test
  void testChainedMethod() throws Exception {
    GeneratedBase testClass = genTestClass("" +
        "((GeneratedBase)this).getNullString().toLowerCase();");

    assertNpeMessage(testClass::run, "Invoking java.lang.String#toLowerCase on null object returned from ee.murkaje.GeneratedBase#getNullString");
  }

  @Test
  void testChainedMethodWithDebug() throws Exception {
    GeneratedBase testClass = genTestClass("");

    assertNpeMessage(testClass::generateNpeDebugMethodChaining,
        "Invoking java.lang.String#compareTo on null object returned from ee.murkaje.GeneratedBase#getNullString");
  }

  @Test
  void testTernaryBranchMethodChaining() throws Exception {
    GeneratedBase testClass = genTestClass("" +
        "boolean test = true;" +
        "(test ? ((GeneratedBase)this).getNullString() : ((GeneratedBase)this).getEmptyString()).toLowerCase();");

    assertNpeMessage(testClass::run, "Invoking java.lang.String#toLowerCase on null object returned from ee.murkaje.GeneratedBase#getNullString");
  }

  @Test
  void testExplicitNullCheck() throws Exception {
    GeneratedBase testClass = genTestClass("" +
        "String nullString = null;" +
        "if(nullString == null) {" +
        "  throw new NullPointerException();" +
        "}");

    assertNpeMessage(testClass::run, "Explicitly thrown after null check of local variable in slot 2");
  }

  @Test
  void testArrayStore() throws Exception {
    GeneratedBase testClass = genTestClass("" +
        "String[] myStrings = getNullStringArray();" +
        "String str = getEmptyString();" +
        "myStrings[0] = str;");

    assertNpeMessage(testClass::run, "Storing array value to null local variable in slot 2");
  }

  @Test
  void testArrayLength() throws Exception {
    GeneratedBase testClass = genTestClass("" +
        "String[] myStrings = getNullStringArray();" +
        "myStrings.length;");

    assertNpeMessage(testClass::run, "Getting array length of null local variable in slot 2");
  }

  @Test
  void testNullInstanceFieldGet() throws Exception {
    GeneratedBase testClass = genTestClass("" +
        "TestClass test = null;" +
        "test.stringField;");

    assertNpeMessage(testClass::run, "Getting field ee.murkaje.TestClass.stringField of null local variable in slot 2");
  }

  @Test
  void testInstanceFieldPut() throws Exception {
    GeneratedBase testClass = genTestClass("" +
        "TestClass test = null;" +
        "test.stringField = \"\";");

    assertNpeMessage(testClass::run, "Setting field ee.murkaje.TestClass.stringField of null local variable in slot 2");
  }

  @Test
  void testInstanceNullFieldGet() throws Exception {
    GeneratedBase testClass = genTestClass("" +
        "TestClass test = new TestClass();" +
        "test.stringField.toLowerCase();");

    assertNpeMessage(testClass::run, "Invoking java.lang.String#toLowerCase on null instance field ee.murkaje.TestClass.stringField");
  }

  @Test
  void testStaticFieldGet() throws Exception {
    GeneratedBase testClass = genTestClass("" +
        "TestClass.staticStringField.toLowerCase();");

    assertNpeMessage(testClass::run, "Invoking java.lang.String#toLowerCase on null static field ee.murkaje.TestClass.staticStringField");
  }

  @Test
  void testNullExceptionThrow() throws Exception {
    GeneratedBase testClass = genTestClass("");
    assertNpeMessage(testClass::throwNull, "Throwing null constant");
  }

  @Test
  void testMethodReference() throws Exception {
    GeneratedBase testClass = genTestClass("" +
        "TestClass testClass = null;" +
        "TestClass.invokeEmptyMethod(testClass);");

    assertNpeMessage(testClass::run, "Getting method reference ee.murkaje.TestClass::emptyMethod on null method parameter test");
  }

  @Test
  void testMultipleStackAlteringArgs() throws Exception {
    GeneratedBase testClass = genTestClass("");

    assertNpeMessage(testClass::consumeStackAlteringExpressions, "Invoking ee.murkaje.TestClass#consumeAll on null local variable testClass:ee.murkaje.TestClass");
  }

  @Test
  void testSwap() throws Exception {
    GeneratedBase testClass = genTestClass(mv -> {
      mv.visitInsn(ACONST_NULL);
      mv.visitVarInsn(ALOAD, 0);
      mv.visitMethodInsn(INVOKEINTERFACE, "ee/murkaje/GeneratedBase", "getEmptyString", "()Ljava/lang/String;", true);
      mv.visitInsn(SWAP);
      mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/String", "toLowerCase", "()Ljava/lang/String;", false);
      mv.visitInsn(POP);
      mv.visitInsn(POP);
      mv.visitInsn(RETURN);
    });

    assertNpeMessage(testClass::run, "Invoking java.lang.String#toLowerCase on null constant");
  }
}
