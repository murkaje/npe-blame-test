package ee.murkaje;

public class TestClass {

  public static String staticStringField;

  public String stringField;

  public static void invokeEmptyMethod(TestClass test) {
    ((Runnable) test::emptyMethod).run();
  }

  public void emptyMethod() {}
}
