package com.ebay.hadoop.udf.dapgap;

import org.apache.hadoop.hive.common.type.HiveChar;
import org.apache.hadoop.hive.common.type.HiveDecimal;
import org.apache.hadoop.hive.common.type.HiveVarchar;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF.DeferredJavaObject;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF.DeferredObject;
import org.apache.hadoop.hive.serde2.io.HiveDecimalWritable;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.junit.Assert;
import org.junit.Test;

public class GdprRemediateTest {

  @Test
  public void testRemediateStringValue() throws HiveException {
    GdprRemediate gdprRemediate = new GdprRemediate();
    ObjectInspector[] arguments = new ObjectInspector[]{
        PrimitiveObjectInspectorFactory.javaHiveDecimalObjectInspector,
        PrimitiveObjectInspectorFactory.javaStringObjectInspector};
    gdprRemediate.initialize(arguments);

    DeferredObject[] deferredObjects = new DeferredObject[]{
        new DeferredJavaObject(HiveDecimal.create(123456789)),
        new DeferredJavaObject("abc@gmail.com")
    };
    Object result = gdprRemediate.evaluate(deferredObjects);
    Assert.assertEquals(new Text("NRl/5n95st+Y5fmVHrHZ/Q=="), result);

    DeferredObject[] deferredObjects1 = new DeferredObject[]{
        new DeferredJavaObject(HiveDecimal.create(123456789)),
        new DeferredJavaObject(null)
    };
    Object result1 = gdprRemediate.evaluate(deferredObjects1);
    Assert.assertEquals(new Text("NRl/5n95st+Y5fmVHrHZ/Q=="), result1);

    DeferredObject[] deferredObjects2 = new DeferredObject[]{
        new DeferredJavaObject(null),
        new DeferredJavaObject("abc@gmail.com")// user id is not in deleted list, just encrypt it
    };
    Object result2 = gdprRemediate.evaluate(deferredObjects2);
    Assert.assertEquals(new Text("shGkpol9mzL5Ul3hkJ+SEA=="), result2);

    DeferredObject[] deferredObjects3 = new DeferredObject[]{
        new DeferredJavaObject(null),
        new DeferredJavaObject("abcd@gmail.com")// user id is not in deleted list, just encrypt it
    };
    Object result3 = gdprRemediate.evaluate(deferredObjects3);
    Assert.assertEquals(new Text("iIj+Wz5GFsey/lGAWRYHXA=="), result3);

    DeferredObject[] deferredObjects4 = new DeferredObject[]{
        new DeferredJavaObject(null),
        new DeferredJavaObject(null)// user id is not in deleted list, just encrypt it
    };
    Object result4 = gdprRemediate.evaluate(deferredObjects4);
    Assert.assertNull(result4);
  }

  @Test
  public void testRemediateCharValue() throws HiveException {
    GdprRemediate gdprRemediate = new GdprRemediate();
    ObjectInspector[] arguments = new ObjectInspector[]{
        PrimitiveObjectInspectorFactory.javaHiveDecimalObjectInspector,
        PrimitiveObjectInspectorFactory.javaHiveCharObjectInspector};
    gdprRemediate.initialize(arguments);

    DeferredObject[] deferredObjects = new DeferredObject[]{
        new DeferredJavaObject(HiveDecimal.create(123456789)),
        new DeferredJavaObject(new HiveChar("abc@gmail.com", -1))
    };
    Object result = gdprRemediate.evaluate(deferredObjects);
    Assert.assertEquals(new Text("NRl/5n95st+Y5fmVHrHZ/Q=="), result);

    DeferredObject[] deferredObjects1 = new DeferredObject[]{
        new DeferredJavaObject(HiveDecimal.create(123456789)),
        new DeferredJavaObject(null)
    };
    Object result1 = gdprRemediate.evaluate(deferredObjects1);
    Assert.assertEquals(new Text("NRl/5n95st+Y5fmVHrHZ/Q=="), result1);

    DeferredObject[] deferredObjects2 = new DeferredObject[]{
        new DeferredJavaObject(null),
        // user id is not in deleted list, just encrypt it
        new DeferredJavaObject(new HiveChar("abc@gmail.com", -1))
    };
    Object result2 = gdprRemediate.evaluate(deferredObjects2);
    Assert.assertEquals(new Text("shGkpol9mzL5Ul3hkJ+SEA=="), result2);

    DeferredObject[] deferredObjects3 = new DeferredObject[]{
        new DeferredJavaObject(null),
        new DeferredJavaObject("abcd@gmail.com")// user id is not in deleted list, just encrypt it
    };
    Object result3 = gdprRemediate.evaluate(deferredObjects3);
    Assert.assertEquals(new Text("iIj+Wz5GFsey/lGAWRYHXA=="), result3);
  }

  @Test
  public void testRemediateVarcharValue() throws HiveException {
    GdprRemediate gdprRemediate = new GdprRemediate();
    ObjectInspector[] arguments = new ObjectInspector[]{
        PrimitiveObjectInspectorFactory.javaHiveDecimalObjectInspector,
        PrimitiveObjectInspectorFactory.javaHiveVarcharObjectInspector};
    gdprRemediate.initialize(arguments);

    DeferredObject[] deferredObjects = new DeferredObject[]{
        new DeferredJavaObject(HiveDecimal.create(123456789)),
        new DeferredJavaObject(new HiveVarchar("abc@gmail.com", -1))
    };
    Object result = gdprRemediate.evaluate(deferredObjects);
    Assert.assertEquals(new Text("NRl/5n95st+Y5fmVHrHZ/Q=="), result);

    DeferredObject[] deferredObjects1 = new DeferredObject[]{
        new DeferredJavaObject(HiveDecimal.create(123456789)),
        new DeferredJavaObject(null)
    };
    Object result1 = gdprRemediate.evaluate(deferredObjects1);
    Assert.assertEquals(new Text("NRl/5n95st+Y5fmVHrHZ/Q=="), result1);

    DeferredObject[] deferredObjects2 = new DeferredObject[]{
        new DeferredJavaObject(null),
        // user id is not in deleted list, just encrypt it
        new DeferredJavaObject(new HiveVarchar("abc@gmail.com", -1))
    };
    Object result2 = gdprRemediate.evaluate(deferredObjects2);
    Assert.assertEquals(new Text("shGkpol9mzL5Ul3hkJ+SEA=="), result2);

    DeferredObject[] deferredObjects3 = new DeferredObject[]{
        new DeferredJavaObject(null),
        new DeferredJavaObject("abcd@gmail.com")// user id is not in deleted list, just encrypt it
    };
    Object result3 = gdprRemediate.evaluate(deferredObjects3);
    Assert.assertEquals(new Text("iIj+Wz5GFsey/lGAWRYHXA=="), result3);
  }

  @Test
  public void testRemediateDecimalValue() throws HiveException {
    GdprRemediate gdprRemediate = new GdprRemediate();
    ObjectInspector[] arguments = new ObjectInspector[]{
        PrimitiveObjectInspectorFactory.javaHiveDecimalObjectInspector,
        PrimitiveObjectInspectorFactory.javaHiveDecimalObjectInspector};
    gdprRemediate.initialize(arguments);
    DeferredObject[] deferredObjects = new DeferredObject[]{
        new DeferredJavaObject(HiveDecimal.create(123456789)),
        new DeferredJavaObject(HiveDecimal.create(1234567890))
    };
    Object result = gdprRemediate.evaluate(deferredObjects);
    Assert.assertEquals(new HiveDecimalWritable(HiveDecimal.create(777)), result);

    DeferredObject[] deferredObjects1 = new DeferredObject[]{
        new DeferredJavaObject(null),
        new DeferredJavaObject(HiveDecimal.create(1234567890))
    };
    Object result1 = gdprRemediate.evaluate(deferredObjects1);
    Assert.assertEquals(new HiveDecimalWritable(HiveDecimal.create(1234567890)), result1);
  }

  @Test
  public void testRemediateLongValue() throws HiveException {
    GdprRemediate gdprRemediate = new GdprRemediate();
    ObjectInspector[] arguments = new ObjectInspector[]{
        PrimitiveObjectInspectorFactory.javaHiveDecimalObjectInspector,
        PrimitiveObjectInspectorFactory.javaLongObjectInspector};
    gdprRemediate.initialize(arguments);
    DeferredObject[] deferredObjects = new DeferredObject[]{
        new DeferredJavaObject(HiveDecimal.create(123456789)),
        new DeferredJavaObject(1234567890L)
    };
    Object result = gdprRemediate.evaluate(deferredObjects);
    Assert.assertEquals(new LongWritable(777), result);

    DeferredObject[] deferredObjects1 = new DeferredObject[]{
        new DeferredJavaObject(null),
        new DeferredJavaObject(1234567890L)
    };
    Object result1 = gdprRemediate.evaluate(deferredObjects1);
    Assert.assertEquals(new LongWritable(1234567890), result1);
  }

}
