package ee.murkaje;

@FunctionalInterface
public interface GeneratedBase {

  default void throwNull() throws NullPointerException {
    throw null;
  }

  default String[] getNullStringArray() {
    return null;
  }

  default String getNullString() {
    return null;
  }

  default String getEmptyString() {
    return "";
  }

  default void generateNpeDebugMethodChaining() {
    getNullString().compareTo(getEmptyString());
  }

  default void generateNpeDebug() {
    String myVar = null;
    myVar.toLowerCase();
  }

  default void consumeStackAlteringExpressions() {
    int[] arr = new int[]{1, 2, 3, 4, 5, 6, 7};
    TestClass testClass = null;
    int intVal = 1;
    long longVal = 1L;
    float floatVal = 1.f;
    double doubleVal = 1.0;
    testClass.consumeAll(1, 137, intVal + 1, intVal - 1, intVal * 1, intVal / 1, intVal % 1,
        1L, 137L, longVal + 1L, longVal - 1L, longVal * 1L, longVal / 1L, longVal % 1L,
        1.f, 137.f, floatVal + 1.f, floatVal - 1.f, floatVal * 1.f, floatVal / 1.f, floatVal % 1.f,
        1.0, 137.0, doubleVal + 1.0, doubleVal - 1.0, doubleVal * 1.0, doubleVal / 1.0, doubleVal % 1.0,
        new int[1][2][3], new Object(), new int[1], arr[2], arr[6], ((Object) "").getClass(), null);
  }

  default void run() {
    consume(0, 0, null, 0, 0, null);
  }

  void consume(int i1, int i2, Object o1, int i3, long l, Object o2);
}
