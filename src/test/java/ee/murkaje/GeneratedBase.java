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

  default void run() {
    consume(null);
  }

  void consume(Object o);
}
