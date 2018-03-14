package ee.murkaje;

import org.junit.jupiter.api.Test;

public class TestClass {

  public static String staticStringField;

  public String stringField;

  public static void invokeEmptyMethod(TestClass test) {
    ((Runnable)test::emptyMethod).run();
  }

  public void emptyMethod() {}
}
