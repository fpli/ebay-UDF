package com.ebay.hadoop.udf.dapgap;

import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF.DeferredJavaObject;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF.DeferredObject;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;
import org.apache.hadoop.io.Text;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class GdprDetectTest {

  private static GdprDetect gdprDetect;

  @BeforeClass
  public static void before() throws UDFArgumentException {
    gdprDetect = new GdprDetect();
    ObjectInspector[] arguments = new ObjectInspector[]{
        PrimitiveObjectInspectorFactory.javaIntObjectInspector,
        PrimitiveObjectInspectorFactory.javaStringObjectInspector,
        PrimitiveObjectInspectorFactory.javaStringObjectInspector};
    gdprDetect.initialize(arguments);
  }

  @Test
  public void testHavePiiColumnNotEncrypted() {
    Text expected = new Text("Have column not encrypted");
    DeferredObject[] deferredObject1 = new DeferredObject[]{
        new DeferredJavaObject(777),
        new DeferredJavaObject("abc"),
        new DeferredJavaObject("Deleted")
    };
    assertEquals(expected, deferredObject1);

    DeferredObject[] deferredObject2 = new DeferredObject[]{
        new DeferredJavaObject(null),
        new DeferredJavaObject("abc"),
        new DeferredJavaObject("def")
    };
    assertEquals(expected, deferredObject2);
  }

  @Test
  public void testHavePiiColumnNotDeleted() {
    Text expected = new Text("Have column not deleted");
    DeferredObject[] deferredObject1 = new DeferredObject[]{
        new DeferredJavaObject(123),
        new DeferredJavaObject("Deleted"),
        new DeferredJavaObject(null)
    };
    assertEquals(expected, deferredObject1);

    DeferredObject[] deferredObject2 = new DeferredObject[]{
        new DeferredJavaObject(123),
        new DeferredJavaObject("NRl/5n95st+Y5fmVHrHZ/Q=="),
        new DeferredJavaObject("abc")
    };
    assertEquals(expected, deferredObject2);
  }

  @Test
  public void testAllPiiColumnsAreNull() {
    Text expected = new Text("All columns are null");
    DeferredObject[] deferredObjects = new DeferredObject[]{
        new DeferredJavaObject(null),
        new DeferredJavaObject(null),
        new DeferredJavaObject(null)
    };
    assertEquals(expected, deferredObjects);
  }

  @Test
  public void testAllPiiColumnsAreEncryptedAndDeleted() {
    Text expected = new Text("All columns are encrypted and deleted");
    DeferredObject[] deferredObject1 = new DeferredObject[]{
        new DeferredJavaObject(null),
        new DeferredJavaObject("NRl/5n95st+Y5fmVHrHZ/Q=="),
        new DeferredJavaObject("Deleted")
    };
    assertEquals(expected, deferredObject1);

    DeferredObject[] deferredObject2 = new DeferredObject[]{
        new DeferredJavaObject(777),
        new DeferredJavaObject("NRl/5n95st+Y5fmVHrHZ/Q=="),
        new DeferredJavaObject("Deleted")
    };
    assertEquals(expected, deferredObject2);

    DeferredObject[] deferredObject3 = new DeferredObject[]{
        new DeferredJavaObject(null),
        new DeferredJavaObject("Deleted"),
        new DeferredJavaObject(null)
    };
    assertEquals(expected, deferredObject3);
  }

  private void assertEquals(Text text, DeferredObject[] deferredObjects) {
    try {
      Object result = gdprDetect.evaluate(deferredObjects);
      Assert.assertEquals(text, result);
    } catch (HiveException e) {
      e.printStackTrace();
    }
  }

}
