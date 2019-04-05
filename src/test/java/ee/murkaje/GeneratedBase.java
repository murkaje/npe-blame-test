package ee.murkaje;

@FunctionalInterface
public interface GeneratedBase {

  default String[] getNullStringArray() {
    return null;
  }

  default String getNullString() {
    return null;
  }

  default String getEmptyString() {
    return "";
  }

  default void run() {
    consume(0, 0, null, 0, 0, null);
  }

  void consume(int i1, int i2, Object o1, int i3, long l, String o2) throws RuntimeException;
}
