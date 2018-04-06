package ee.murkaje;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.objectweb.asm.Opcodes.ACC_PUBLIC;
import static org.objectweb.asm.Opcodes.ALOAD;
import static org.objectweb.asm.Opcodes.INVOKESPECIAL;
import static org.objectweb.asm.Opcodes.RETURN;
import static org.objectweb.asm.Opcodes.V1_8;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

public class TestBase {

  private static AtomicInteger counter = new AtomicInteger(0);

  private static DefiningClassLoader cl = new DefiningClassLoader(TestBase.class.getClassLoader());

  @SuppressWarnings("unchecked")
  public static GeneratedBase genTestClass(String runMethodSrc) throws Exception {
    ClassPool cp = ClassPool.getDefault();
    CtClass cc = cp.makeClass("ee.murkaje.TestJavassistGenerated$Gen" + counter.getAndIncrement());

    cp.importPackage("ee.murkaje");
    cc.addInterface(cp.get(GeneratedBase.class.getName()));

    cc.addMethod(CtMethod.make("public void consume(Object o) {" + runMethodSrc + "}", cc));

    Path classDebugPath = Paths.get("target/generated/ee/murkaje/TestJavassistGenerated$Gen.class");
    Files.createDirectories(classDebugPath.getParent());
    Files.write(classDebugPath, cc.toBytecode());

    return (GeneratedBase) cc.toClass().getDeclaredConstructor().newInstance();
  }

  @SuppressWarnings("unchecked")
  public static GeneratedBase genTestClass(Consumer<MethodVisitor> codeVisitor) throws Exception {
    String classSlashName = "ee/murkaje/TestJavassistGenerated$Gen" + counter.getAndIncrement();

    ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
    cw.visit(V1_8,
        ACC_PUBLIC,
        classSlashName,
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
    Class<? extends GeneratedBase> generatedClass = cl.defineClass(classSlashName.replace('/', '.'), bytes);

    return generatedClass.getDeclaredConstructor().newInstance();
  }

  public static void assertNpeMessage(Runnable testCase, String expectedMessage) {
    assertNpeMessage(testCase, exceptionMessage -> assertEquals(expectedMessage, exceptionMessage));
  }

  public static void assertNpeMessage(Runnable testCase, Consumer<String> messageVerifier) {
    NullPointerException npe = assertThrows(NullPointerException.class, testCase::run);

    StackTraceElement[] filteredTrace = Arrays.stream(npe.getStackTrace())
        .filter(e -> e.getClassName().startsWith("ee.murkaje"))
        .toArray(StackTraceElement[]::new);

    npe.setStackTrace(filteredTrace);
    npe.printStackTrace(System.out);

    messageVerifier.accept(npe.getMessage());
  }
}
