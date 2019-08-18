package ee.murkaje;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;

//TODO: Implicit null checks(invokevirtual getClass() + pop)(Objects.requireNonNull in 9+) for indy, inner <init>, etc.
@SuppressWarnings({"ConstantConditions", "ResultOfMethodCallIgnored", "RedundantCast", "CodeBlock2Expr", "UnnecessaryLocalVariable",
    "PointlessArithmeticExpression", "unused"})
public class TestJavassistGenerated extends TestBase {

  @Test
  void testConstant() throws Exception {
    GeneratedBase testClass = genTestClass("" +
        "((String)null).toLowerCase();");

    assertNpeMessage(testClass::run, "Invoking java.lang.String#toLowerCase on null constant");
  }

  @Test
  void testLocalVariable() throws Exception {
    GeneratedBase testClass = genTestClass("" +
        "String myString = null;" +
        "myString.toLowerCase();");

    assertNpeMessage(testClass::run, "Invoking java.lang.String#toLowerCase on null local variable in slot 8");
  }

  @Test
  void testLocalVariableWithDebug() throws Exception {
    GeneratedBase testClass = (i1, i2, o1, i3, l, o2) -> {
      String myVar = null;
      myVar.toLowerCase();
    };

    assertNpeMessage(testClass::run, "Invoking java.lang.String#toLowerCase on null local variable myVar:java.lang.String");
  }

  @Test
  void testMethodParam() throws Exception {
    GeneratedBase testClass = genTestClass("" +
        "((String)$6).toLowerCase();");

    assertNpeMessage(testClass::run, "Invoking java.lang.String#toLowerCase on null method parameter at index 6");
  }

  @Test
  void testMethodParamWithDebug() throws Exception {
    GeneratedBase testClass = (i1, i2, o1, i3, l, nullString) -> {
      ((String) nullString).toLowerCase();
    };

    assertNpeMessage(testClass::run, "Invoking java.lang.String#toLowerCase on null method parameter nullString:java.lang.String");
  }

  @Test
  void testMethodParamLong() throws Exception {
    GeneratedBase testClass = genTestClass("" +
        "((String)$3).toLowerCase();");

    assertNpeMessage(testClass::run, "Invoking java.lang.String#toLowerCase on null method parameter at index 3");
  }

  @Test
  void testChainedMethod() throws Exception {
    GeneratedBase testClass = genTestClass("" +
        "((GeneratedBase)this).getNullString().toLowerCase();");

    assertNpeMessage(testClass::run, "Invoking java.lang.String#toLowerCase on null object returned from ee.murkaje.GeneratedBase#getNullString");
  }

  @Test
  void testChainedMethodWithDebug() throws Exception {
    GeneratedBase testClass = new GeneratedBase() {
      @Override
      public void consume(int i1, int i2, Object o1, int i3, long l, String o2) {
        ((GeneratedBase) this).getNullString().compareTo(getEmptyString());
      }
    };

    assertNpeMessage(testClass::run, "Invoking java.lang.String#compareTo on null object returned from ee.murkaje.GeneratedBase#getNullString");
  }

  @Test
  @EnabledIfSystemProperty(named = "suite", matches = "full")
  void testTernaryBranchMethodChaining() throws Exception {
    GeneratedBase testClass = genTestClass("" +
        "boolean test = true;" +
        "(test ? ((GeneratedBase)this).getNullString() : ((GeneratedBase)this).getEmptyString()).toLowerCase();");

    assertNpeMessage(testClass::run, "Invoking java.lang.String#toLowerCase on null object returned from ee.murkaje.GeneratedBase#getNullString");
  }

  @Test
  @EnabledIfSystemProperty(named = "suite", matches = "full")
  void testExplicitNullCheck() throws Exception {
    GeneratedBase testClass = (i1, i2, o1, i3, l, nullString) -> {
      if(nullString == null) {
        throw new NullPointerException();
      }
    };

    assertNpeMessage(testClass::run, "Explicitly thrown after null check of local variable nullString:java.lang.String");
  }

  //region array store
  @Test
  void testObjectArrayStore() throws Exception {
    GeneratedBase testClass = genTestClass("" +
        "String[] arr = null;" +
        "arr[0] = \"hello\";");

    assertNpeMessage(testClass::run, "Storing object to null array - local variable in slot 8");
  }

  @Test
  void testIntArrayStore() throws Exception {
    GeneratedBase testClass = genTestClass("" +
        "int[] arr = null;" +
        "arr[0] = 1;");

    assertNpeMessage(testClass::run, "Storing int to null array - local variable in slot 8");
  }

  @Test
  void testLongArrayStore() throws Exception {
    GeneratedBase testClass = genTestClass("" +
        "long[] arr = null;" +
        "arr[0] = 1L;");

    assertNpeMessage(testClass::run, "Storing long to null array - local variable in slot 8");
  }

  @Test
  void testFloatArrayStore() throws Exception {
    GeneratedBase testClass = genTestClass("" +
        "float[] arr = null;" +
        "arr[0] = 42.f;");

    assertNpeMessage(testClass::run, "Storing float to null array - local variable in slot 8");
  }

  @Test
  void testDoubleArrayStore() throws Exception {
    GeneratedBase testClass = genTestClass("" +
        "double[] arr = null;" +
        "arr[0] = 42.0;");

    assertNpeMessage(testClass::run, "Storing double to null array - local variable in slot 8");
  }

  @Test
  void testBooleanArrayStore() throws Exception {
    GeneratedBase testClass = genTestClass("" +
        "boolean[] arr = null;" +
        "arr[0] = true;");

    assertNpeMessage(testClass::run, "Storing byte/boolean to null array - local variable in slot 8");
  }

  @Test
  void testCharArrayStore() throws Exception {
    GeneratedBase testClass = genTestClass("" +
        "char[] arr = null;" +
        "arr[0] = '∠';");

    assertNpeMessage(testClass::run, "Storing char to null array - local variable in slot 8");
  }

  @Test
  void testShortArrayStore() throws Exception {
    GeneratedBase testClass = genTestClass("" +
        "short[] arr = null;" +
        "arr[0] = (short) 32760;");

    assertNpeMessage(testClass::run, "Storing short to null array - local variable in slot 8");
  }
  //endregion array store

  //region array load
  @Test
  void testObjectArrayLoad() throws Exception {
    GeneratedBase testClass = genTestClass("" +
        "String[] myStrings = null;" +
        "myStrings[0];");

    assertNpeMessage(testClass::run, "Loading object from null array - local variable in slot 8");
  }

  @Test
  void testIntArrayLoad() throws Exception {
    GeneratedBase testClass = genTestClass("" +
        "int[] arr = null;" +
        "arr[0];");

    assertNpeMessage(testClass::run, "Loading int from null array - local variable in slot 8");
  }

  @Test
  void testLongArrayLoad() throws Exception {
    GeneratedBase testClass = genTestClass("" +
        "long[] arr = null;" +
        "arr[0];");

    assertNpeMessage(testClass::run, "Loading long from null array - local variable in slot 8");
  }

  @Test
  void testFloatArrayLoad() throws Exception {
    GeneratedBase testClass = genTestClass("" +
        "float[] arr = null;" +
        "arr[0];");

    assertNpeMessage(testClass::run, "Loading float from null array - local variable in slot 8");
  }

  @Test
  void testDoubleArrayLoad() throws Exception {
    GeneratedBase testClass = genTestClass("" +
        "double[] arr = null;" +
        "arr[0];");

    assertNpeMessage(testClass::run, "Loading double from null array - local variable in slot 8");
  }

  @Test
  void testBooleanArrayLoad() throws Exception {
    GeneratedBase testClass = genTestClass("" +
        "boolean[] arr = null;" +
        "arr[0];");

    assertNpeMessage(testClass::run, "Loading byte/boolean from null array - local variable in slot 8");
  }

  @Test
  void testCharArrayLoad() throws Exception {
    GeneratedBase testClass = genTestClass("" +
        "char[] arr = null;" +
        "arr[0];");

    assertNpeMessage(testClass::run, "Loading char from null array - local variable in slot 8");
  }

  @Test
  void testShortArrayLoad() throws Exception {
    GeneratedBase testClass = genTestClass("" +
        "short[] arr = null;" +
        "arr[0];");

    assertNpeMessage(testClass::run, "Loading short from null array - local variable in slot 8");
  }
  //endregion array load

  @Test
  void testArrayLength() throws Exception {
    GeneratedBase testClass = genTestClass("" +
        "String[] myStrings = getNullStringArray();" +
        "myStrings.length;");

    assertNpeMessage(testClass::run, "Getting array length of null local variable in slot 8");
  }

  @Test
  void testNullInstanceFieldGet() throws Exception {
    GeneratedBase testClass = genTestClass("" +
        "TestClass test = null;" +
        "test.stringField;");

    assertNpeMessage(testClass::run, "Getting field ee.murkaje.TestClass.stringField of null local variable in slot 8");
  }

  @Test
  void testInstanceFieldPut() throws Exception {
    GeneratedBase testClass = genTestClass("" +
        "TestClass test = null;" +
        "test.stringField = \"\";");

    assertNpeMessage(testClass::run, "Setting field ee.murkaje.TestClass.stringField of null local variable in slot 8");
  }

  @Test
  void testInstanceNullFieldGet() throws Exception {
    GeneratedBase testClass = genTestClass("" +
        "TestClass test = new TestClass();" +
        "test.stringField.toLowerCase();");

    assertNpeMessage(testClass::run, "Invoking java.lang.String#toLowerCase on null instance field ee.murkaje.TestClass.stringField");
  }

  @Test
  void testStaticFieldGet() throws Exception {
    GeneratedBase testClass = genTestClass("" +
        "TestClass.staticStringField.toLowerCase();");

    assertNpeMessage(testClass::run, "Invoking java.lang.String#toLowerCase on null static field ee.murkaje.TestClass.staticStringField");
  }

  @Test
  void testNullExceptionThrow() throws Exception {
    GeneratedBase testClass = (i1, i2, o1, i3, l, o2) -> {
      RuntimeException ex = null;
      throw ex;
    };
    assertNpeMessage(testClass::run, "Throwing null local variable ex:java.lang.RuntimeException");
  }

  @Test
  @EnabledIfSystemProperty(named = "suite", matches = "full")
  void testMethodReference() throws Exception {
    GeneratedBase testClass = genTestClass("" +
        "TestClass testClass = null;" +
        "TestClass.invokeEmptyMethod(testClass);");

    assertNpeMessage(testClass::run, "Getting method reference ee.murkaje.TestClass::emptyMethod on null method parameter test");
  }

  @Test
  void testSynchronize() throws Exception {
    GeneratedBase testClass = genTestClass("" +
        "String monitorKey = null;" +
        "synchronized (monitorKey) {" +
        "  System.out.println(monitorKey);" +
        "}");

    assertNpeMessage(testClass::run, "Synchronizing on null local variable in slot 8");
  }

  @Test
  void testMultipleStackAlteringArgs() throws Exception {
    GeneratedBase testClass = (int i1, int i2, Object o1, int i3, long l, String o2) -> {
      int[] arr = new int[]{1, 2, 3, 4, 5, 6, 7};
      TestClass test = null;
      int intVal = 1;
      long longVal = 1L;
      float floatVal = 1.f;
      double doubleVal = 1.0;
      test.consumeAll(1, 137, intVal + 1, intVal - 1, intVal * 1, intVal / 1, intVal % 1,
          1L, 137L, longVal + 1L, longVal - 1L, longVal * 1L, longVal / 1L, longVal % 1L,
          1.f, 137.f, floatVal + 1.f, floatVal - 1.f, floatVal * 1.f, floatVal / 1.f, floatVal % 1.f,
          1.0, 137.0, doubleVal + 1.0, doubleVal - 1.0, doubleVal * 1.0, doubleVal / 1.0, doubleVal % 1.0,
          new int[1][2][3], new Object(), new int[1], arr[2], arr[6], ((Object) "").getClass(), null);
    };

    assertNpeMessage(testClass::run, "Invoking ee.murkaje.TestClass#consumeAll on null local variable test:ee.murkaje.TestClass");
  }
}
