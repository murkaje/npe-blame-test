package ee.murkaje;

@FunctionalInterface
public interface GeneratedBase<T> {

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
    boolean test = true;
    (test ? getNullString() : getEmptyString()).compareTo(getEmptyString());
  }

  default void generateNpeDebug(T o) {
    String myVar = o.toString();
    myVar = null;
    myVar.toLowerCase();
  }

  void consume(T o);
}
