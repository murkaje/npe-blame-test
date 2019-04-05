package ee.murkaje;

import org.junit.jupiter.api.Test;
import org.objectweb.asm.Label;
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
  void testDupx1Injected() throws Exception {
    GeneratedBase testClass = genTestClass(mv -> {
      mv.visitVarInsn(ALOAD, 0);
      mv.visitVarInsn(ALOAD, 0);
      mv.visitInsn(ACONST_NULL);
      mv.visitInsn(DUP_X1);
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
  void testDupx1Far() throws Exception {
    GeneratedBase testClass = genTestClass(mv -> {
      mv.visitVarInsn(ALOAD, 0);
      mv.visitInsn(ACONST_NULL);
      mv.visitVarInsn(ALOAD, 0);
      mv.visitVarInsn(ALOAD, 0);
      mv.visitInsn(DUP_X1);
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
  void testDupx2Form1Injected() throws Exception {
    GeneratedBase testClass = genTestClass(mv -> {
      mv.visitVarInsn(ALOAD, 0);
      mv.visitVarInsn(ALOAD, 0);
      mv.visitInsn(ACONST_NULL);
      mv.visitInsn(DUP_X2);
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
  void testDupx2Form1Far() throws Exception {
    GeneratedBase testClass = genTestClass(mv -> {
      mv.visitInsn(ACONST_NULL);
      mv.visitVarInsn(ALOAD, 0);
      mv.visitVarInsn(ALOAD, 0);
      mv.visitVarInsn(ALOAD, 0);
      mv.visitInsn(DUP_X2);
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
  void testDupx2Form2Injected() throws Exception {
    GeneratedBase testClass = genTestClass(mv -> {
      mv.visitVarInsn(ALOAD, 0);
      mv.visitInsn(LCONST_1);
      mv.visitInsn(ACONST_NULL);
      mv.visitInsn(DUP_X2);
      mv.visitInsn(POP);
      mv.visitInsn(POP2);
      mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/String", "toLowerCase", "()Ljava/lang/String;", false);
      mv.visitInsn(POP);
      mv.visitInsn(POP);
      mv.visitInsn(RETURN);
    });

    assertNpeMessage(testClass::run, "Invoking java.lang.String#toLowerCase on null constant");
  }

  @Test
  void testDupx2Form2Far() throws Exception {
    GeneratedBase testClass = genTestClass(mv -> {
      mv.visitVarInsn(ALOAD, 0);
      mv.visitInsn(ACONST_NULL);
      mv.visitInsn(LCONST_1);
      mv.visitVarInsn(ALOAD, 0);
      mv.visitInsn(DUP_X2);
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
  void testDup2Form1() throws Exception {
    GeneratedBase testClass = genTestClass(mv -> {
      mv.visitInsn(ACONST_NULL);
      mv.visitVarInsn(ALOAD, 0);
      mv.visitInsn(DUP2);
      mv.visitInsn(POP);
      mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/String", "toLowerCase", "()Ljava/lang/String;", false);
      mv.visitInsn(POP);
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

  @Test
  void testWideOpsInMiddleAndPush() throws Exception {
    GeneratedBase testClass = genTestClass(mv -> {
      mv.visitInsn(ACONST_NULL);
      mv.visitVarInsn(ASTORE, 256); // WIDE ASTORE  0x0100
      mv.visitVarInsn(ALOAD, 256);  // WIDE ALOAD   0x0100
      mv.visitInsn(ICONST_0);
      mv.visitVarInsn(ISTORE, 9);
      mv.visitIincInsn(9, 256);     // WIDE IINC    0x0009 0x0100
      mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/String", "toLowerCase", "()Ljava/lang/String;", false);
      mv.visitInsn(POP);
      mv.visitInsn(RETURN);
    });

    assertNpeMessage(testClass::run, "Invoking java.lang.String#toLowerCase on null local variable in slot 256");
  }

  @Test
  void testTableSwitchInMiddle() throws Exception {
    GeneratedBase testClass = genTestClass(mv -> {
      mv.visitInsn(ACONST_NULL);
      mv.visitInsn(ICONST_0);
      Label defaultJump = new Label();
      Label jump0 = new Label();
      Label jump1 = new Label();
      Label jump2 = new Label();
      mv.visitTableSwitchInsn(12, 14, defaultJump, jump0, jump1, jump2);
      mv.visitLabel(jump0);
      mv.visitJumpInsn(GOTO, defaultJump);
      mv.visitLabel(jump1);
      mv.visitJumpInsn(GOTO, defaultJump);
      mv.visitLabel(jump2);
      mv.visitJumpInsn(GOTO, defaultJump);
      mv.visitLabel(defaultJump);
      mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/String", "toLowerCase", "()Ljava/lang/String;", false);
      mv.visitInsn(POP);
      mv.visitInsn(RETURN);
    });

    assertNpeMessage(testClass::run, "Invoking java.lang.String#toLowerCase on null constant");
  }

  @Test
  void testTableSwitchBranchPushes() throws Exception {
    GeneratedBase testClass = genTestClass(mv -> {
      mv.visitIntInsn(BIPUSH, 13);
      Label defaultJump = new Label();
      Label jump0 = new Label();
      Label jump1 = new Label();
      Label jump2 = new Label();
      Label switchEnd = new Label();
      mv.visitTableSwitchInsn(12, 14, defaultJump, jump0, jump1, jump2);
      mv.visitLabel(jump0);
      mv.visitVarInsn(ALOAD, 0);
      mv.visitMethodInsn(INVOKEINTERFACE, "ee/murkaje/GeneratedBase", "getEmptyString", "()Ljava/lang/String;", true);
      mv.visitJumpInsn(GOTO, switchEnd);
      mv.visitLabel(jump1);
      mv.visitVarInsn(ALOAD, 0);
      mv.visitMethodInsn(INVOKEINTERFACE, "ee/murkaje/GeneratedBase", "getNullString", "()Ljava/lang/String;", true);
      mv.visitJumpInsn(GOTO, switchEnd);
      mv.visitLabel(jump2);
      mv.visitVarInsn(ALOAD, 0);
      mv.visitMethodInsn(INVOKEINTERFACE, "ee/murkaje/GeneratedBase", "getEmptyString", "()Ljava/lang/String;", true);
      mv.visitJumpInsn(GOTO, switchEnd);
      mv.visitLabel(defaultJump);
      mv.visitVarInsn(ALOAD, 0);
      mv.visitMethodInsn(INVOKEINTERFACE, "ee/murkaje/GeneratedBase", "getEmptyString", "()Ljava/lang/String;", true);
      mv.visitLabel(switchEnd);
      mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/String", "toLowerCase", "()Ljava/lang/String;", false);
      mv.visitInsn(POP);
      mv.visitInsn(RETURN);
    });

    assertNpeMessage(testClass::run, "Invoking java.lang.String#toLowerCase on null object returned from ee.murkaje.GeneratedBase#getNullString");
  }

  @Test
  void testLookupSwitchInMiddle() throws Exception {
    GeneratedBase testClass = genTestClass(mv -> {
      mv.visitInsn(ACONST_NULL);
      mv.visitInsn(ICONST_0);
      Label defaultJump = new Label();
      Label jump0 = new Label();
      Label jump1 = new Label();
      Label jump2 = new Label();
      mv.visitLookupSwitchInsn(defaultJump, new int[]{1, 13, 1337}, new Label[]{jump0, jump1, jump2});
      mv.visitLabel(jump0);
      mv.visitJumpInsn(GOTO, defaultJump);
      mv.visitLabel(jump1);
      mv.visitJumpInsn(GOTO, defaultJump);
      mv.visitLabel(jump2);
      mv.visitJumpInsn(GOTO, defaultJump);
      mv.visitLabel(defaultJump);
      mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/String", "toLowerCase", "()Ljava/lang/String;", false);
      mv.visitInsn(POP);
      mv.visitInsn(RETURN);
    });

    assertNpeMessage(testClass::run, "Invoking java.lang.String#toLowerCase on null constant");
  }
}
