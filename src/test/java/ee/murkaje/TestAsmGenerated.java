package ee.murkaje;

import org.junit.jupiter.api.Test;
import org.objectweb.asm.Opcodes;

public class TestAsmGenerated extends TestBase implements Opcodes {

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

  @Test
  void testDup2Form1() throws Exception {
    GeneratedBase testClass = genTestClass(mv -> {
      mv.visitVarInsn(ALOAD, 0);
      mv.visitInsn(ACONST_NULL);
      mv.visitInsn(DUP2);
      mv.visitInsn(POP);
      mv.visitInsn(POP);
      mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/String", "toLowerCase", "()Ljava/lang/String;", false);
      mv.visitInsn(POP);
      mv.visitInsn(POP);
      mv.visitInsn(RETURN);
    });

    assertNpeMessage(testClass::run, "Invoking java.lang.String#toLowerCase on null constant");
  }

  @Test
  void testDup2Form2() throws Exception {
    GeneratedBase testClass = genTestClass(mv -> {
      mv.visitInsn(ACONST_NULL);
      mv.visitInsn(LCONST_1);
      mv.visitInsn(DUP2);
      mv.visitInsn(POP2);
      mv.visitInsn(POP2);
      mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/String", "toLowerCase", "()Ljava/lang/String;", false);
      mv.visitInsn(POP);
      mv.visitInsn(RETURN);
    });

    assertNpeMessage(testClass::run, "Invoking java.lang.String#toLowerCase on null constant");
  }

  @Test
  void testDup2x1Form1() throws Exception {
    GeneratedBase testClass = genTestClass(mv -> {
      mv.visitVarInsn(ALOAD, 0);
      mv.visitVarInsn(ALOAD, 0);
      mv.visitInsn(ACONST_NULL);
      mv.visitInsn(DUP2_X1);
      mv.visitInsn(POP);
      mv.visitInsn(POP);
      mv.visitInsn(POP);
      mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/String", "toLowerCase", "()Ljava/lang/String;", false);
      mv.visitInsn(POP);
      mv.visitInsn(POP);
      mv.visitInsn(RETURN);
    });

    assertNpeMessage(testClass::run, "Invoking java.lang.String#toLowerCase on null constant");
  }

  @Test
  void testDup2x1Form2() throws Exception {
    GeneratedBase testClass = genTestClass(mv -> {
      mv.visitInsn(ACONST_NULL);
      mv.visitVarInsn(ALOAD, 0);
      mv.visitInsn(LCONST_1);
      mv.visitInsn(DUP2_X1);
      mv.visitInsn(POP2);
      mv.visitInsn(POP);
      mv.visitInsn(POP2);
      mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/String", "toLowerCase", "()Ljava/lang/String;", false);
      mv.visitInsn(POP);
      mv.visitInsn(RETURN);
    });

    assertNpeMessage(testClass::run, "Invoking java.lang.String#toLowerCase on null constant");
  }

  @Test
  void testDup2x2Form1() throws Exception {
    GeneratedBase testClass = genTestClass(mv -> {
      mv.visitVarInsn(ALOAD, 0);
      mv.visitVarInsn(ALOAD, 0);
      mv.visitInsn(ACONST_NULL);
      mv.visitVarInsn(ALOAD, 0);
      mv.visitInsn(DUP2_X2);
      mv.visitInsn(POP);
      mv.visitInsn(POP);
      mv.visitInsn(POP);
      mv.visitInsn(POP);
      mv.visitInsn(POP);
      mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/String", "toLowerCase", "()Ljava/lang/String;", false);
      mv.visitInsn(POP);
      mv.visitInsn(RETURN);
    });

    assertNpeMessage(testClass::run, "Invoking java.lang.String#toLowerCase on null constant");
  }

  @Test
  void testDup2x2Form2() throws Exception {
    GeneratedBase testClass = genTestClass(mv -> {
      mv.visitInsn(ACONST_NULL);
      mv.visitVarInsn(ALOAD, 0);
      mv.visitVarInsn(ALOAD, 0);
      mv.visitInsn(LCONST_1);
      mv.visitInsn(DUP2_X2);
      mv.visitInsn(POP2);
      mv.visitInsn(POP);
      mv.visitInsn(POP);
      mv.visitInsn(POP2);
      mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/String", "toLowerCase", "()Ljava/lang/String;", false);
      mv.visitInsn(POP);
      mv.visitInsn(RETURN);
    });

    assertNpeMessage(testClass::run, "Invoking java.lang.String#toLowerCase on null constant");
  }

  @Test
  void testDup2x2Form3() throws Exception {
    GeneratedBase testClass = genTestClass(mv -> {
      mv.visitInsn(LCONST_1);
      mv.visitInsn(ACONST_NULL);
      mv.visitVarInsn(ALOAD, 0);
      mv.visitInsn(DUP2_X2);
      mv.visitInsn(POP);
      mv.visitInsn(POP);
      mv.visitInsn(POP2);
      mv.visitInsn(POP);
      mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/String", "toLowerCase", "()Ljava/lang/String;", false);
      mv.visitInsn(POP);
      mv.visitInsn(RETURN);
    });

    assertNpeMessage(testClass::run, "Invoking java.lang.String#toLowerCase on null constant");
  }

  @Test
  void testDup2x2Form4() throws Exception {
    GeneratedBase testClass = genTestClass(mv -> {
      mv.visitInsn(ACONST_NULL);
      mv.visitInsn(LCONST_1);
      mv.visitInsn(LCONST_1);
      mv.visitInsn(DUP2_X2);
      mv.visitInsn(POP2);
      mv.visitInsn(POP2);
      mv.visitInsn(POP2);
      mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/String", "toLowerCase", "()Ljava/lang/String;", false);
      mv.visitInsn(POP);
      mv.visitInsn(RETURN);
    });

    assertNpeMessage(testClass::run, "Invoking java.lang.String#toLowerCase on null constant");
  }
}
