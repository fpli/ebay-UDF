package com.ebay.hadoop.udf.dapgap;

import static org.junit.Assert.assertEquals;

import com.ebay.hadoop.udf.common.encrypt.AesEncrypterDecrypter;
import org.apache.hadoop.hive.common.type.HiveChar;
import org.apache.hadoop.hive.common.type.HiveVarchar;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF.DeferredJavaObject;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF.DeferredObject;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.PrimitiveObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;
import org.apache.hadoop.io.IntWritable;
import org.junit.Test;

public class PIIMaskVerifyTest {

  PIIMaskVerify piiMaskVerify = new PIIMaskVerify();


  @Test
  public void testVerifyNumber() throws HiveException {
    assertVerifyNumber(PrimitiveObjectInspectorFactory.javaShortObjectInspector, (short) 777, 0);
    assertVerifyNumber(PrimitiveObjectInspectorFactory.javaIntObjectInspector, 777, 0);
    assertVerifyNumber(PrimitiveObjectInspectorFactory.javaLongObjectInspector, 777L, 0);
    assertVerifyNumber(PrimitiveObjectInspectorFactory.javaFloatObjectInspector, 777F, 0);
    assertVerifyNumber(PrimitiveObjectInspectorFactory.javaDoubleObjectInspector, 777D, 0);

    assertVerifyNumber(PrimitiveObjectInspectorFactory.javaShortObjectInspector, (short) 1, 1);
    assertVerifyNumber(PrimitiveObjectInspectorFactory.javaIntObjectInspector, 2, 1);
    assertVerifyNumber(PrimitiveObjectInspectorFactory.javaLongObjectInspector, 3L, 1);
    assertVerifyNumber(PrimitiveObjectInspectorFactory.javaFloatObjectInspector, 3.1F, 1);
    assertVerifyNumber(PrimitiveObjectInspectorFactory.javaDoubleObjectInspector, 4.4D, 1);
  }

  @Test
  public void testVerifyNullValue() throws HiveException {
    assertVerifyNull(PrimitiveObjectInspectorFactory.javaShortObjectInspector);
    assertVerifyNull(PrimitiveObjectInspectorFactory.javaIntObjectInspector);
    assertVerifyNull(PrimitiveObjectInspectorFactory.javaLongObjectInspector);
    assertVerifyNull(PrimitiveObjectInspectorFactory.javaFloatObjectInspector);
    assertVerifyNull(PrimitiveObjectInspectorFactory.javaDoubleObjectInspector);
    assertVerifyNull(PrimitiveObjectInspectorFactory.javaStringObjectInspector);
    assertVerifyNull(PrimitiveObjectInspectorFactory.javaHiveVarcharObjectInspector);
    assertVerifyNull(PrimitiveObjectInspectorFactory.javaHiveCharObjectInspector);
  }

  @Test
  public void testVerifyString() throws Exception {
    assertVerifyString(PrimitiveObjectInspectorFactory.javaStringObjectInspector, "abc", false);
    assertVerifyString(PrimitiveObjectInspectorFactory.javaHiveVarcharObjectInspector,
        new HiveVarchar("abc", 256),
        false);
    assertVerifyString(PrimitiveObjectInspectorFactory.javaHiveCharObjectInspector, new HiveChar(
        "123", 256), false);

    assertVerifyString(PrimitiveObjectInspectorFactory.javaStringObjectInspector,
        "ZP0P9buEEnMkQ3quUrhOpQ==", true);
    assertVerifyString(PrimitiveObjectInspectorFactory.javaHiveVarcharObjectInspector,
        new HiveVarchar("ZP0P9buEEnMkQ3quUrhOpQ==", 256),
        true);
    assertVerifyString(PrimitiveObjectInspectorFactory.javaHiveCharObjectInspector, new HiveChar(
        "ZP0P9buEEnMkQ3quUrhOpQ==", 256), true);
  }

  void assertVerifyNumber(PrimitiveObjectInspector valueOI, Object input, int expectedFlag)
      throws HiveException {
    piiMaskVerify.initialize(new ObjectInspector[]{valueOI});
    Object result = piiMaskVerify.evaluate(new DeferredObject[]{new DeferredJavaObject(input)});
    assertEquals(new IntWritable(expectedFlag), result);
  }

  void assertVerifyNull(PrimitiveObjectInspector valueOI)
      throws HiveException {
    piiMaskVerify.initialize(new ObjectInspector[]{valueOI});
    Object result;
    result = piiMaskVerify.evaluate(new DeferredObject[]{new DeferredJavaObject(null)});
    assertEquals(new IntWritable(2), result);
  }

  void assertVerifyString(PrimitiveObjectInspector valueOI, Object input, boolean matched)
      throws Exception {
    piiMaskVerify.initialize(new ObjectInspector[]{valueOI});
    piiMaskVerify.cipher = new AesEncrypterDecrypter(
        "DDIsx8K8IhIhd54D4QCgQFYUK8GNc9tibKCzuE97M/g=",
        "18f876ed20a561c2");
    boolean result = piiMaskVerify.verifyStringGroup(input);
    assertEquals(matched, result);
  }
}
