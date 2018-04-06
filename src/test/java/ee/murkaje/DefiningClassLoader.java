package ee.murkaje;

public class DefiningClassLoader extends ClassLoader {

  public DefiningClassLoader(ClassLoader parent) {
    super(parent);
  }

  @SuppressWarnings("unchecked")
  public Class<? extends GeneratedBase> defineClass(String name, byte[] bytes) {
    return (Class<? extends GeneratedBase>) defineClass(name, bytes, 0, bytes.length);
  }
}
