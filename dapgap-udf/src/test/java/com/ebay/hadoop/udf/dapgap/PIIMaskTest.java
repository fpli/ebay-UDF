package com.ebay.hadoop.udf.dapgap;

import static org.junit.Assert.assertEquals;

import com.ebay.hadoop.udf.common.encrypt.AesEncrypterDecrypter;
import org.apache.hadoop.hive.common.type.HiveChar;
import org.apache.hadoop.hive.common.type.HiveVarchar;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF.DeferredJavaObject;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF.DeferredObject;
import org.apache.hadoop.hive.serde2.io.DoubleWritable;
import org.apache.hadoop.hive.serde2.io.HiveCharWritable;
import org.apache.hadoop.hive.serde2.io.HiveVarcharWritable;
import org.apache.hadoop.hive.serde2.io.ShortWritable;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.PrimitiveObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.junit.Test;

public class PIIMaskTest {

  PIIMask piiMask = new PIIMask();

  @Test
  public void testMaskNumber() throws HiveException {
    assertMaskNumber(PrimitiveObjectInspectorFactory.javaShortObjectInspector, (short) 1,
        new ShortWritable((short) 777));
    assertMaskNumber(PrimitiveObjectInspectorFactory.javaIntObjectInspector, 1,
        new IntWritable(777));
    assertMaskNumber(PrimitiveObjectInspectorFactory.javaLongObjectInspector, 1L,
        new LongWritable(777L));
    assertMaskNumber(PrimitiveObjectInspectorFactory.javaFloatObjectInspector, 1F,
        new FloatWritable(777F));
    assertMaskNumber(PrimitiveObjectInspectorFactory.javaDoubleObjectInspector, 1D,
        new DoubleWritable(777D));
  }

  @Test
  public void testMaskString() throws Exception {
    assertMaskString(PrimitiveObjectInspectorFactory.javaStringObjectInspector,
        new Text("ZP0P9buEEnMkQ3quUrhOpQ=="));
    assertMaskString(PrimitiveObjectInspectorFactory.javaHiveCharObjectInspector,
        new HiveCharWritable(new HiveChar("ZP0P9buEEnMkQ3quUrhOpQ==", 256)));
    assertMaskString(PrimitiveObjectInspectorFactory.javaHiveVarcharObjectInspector,
        new HiveVarcharWritable(new HiveVarchar("ZP0P9buEEnMkQ3quUrhOpQ==", 256)));
  }


  void assertMaskNumber(PrimitiveObjectInspector valueOI, Object input, Object expectValue)
      throws HiveException {
    piiMask.initialize(new ObjectInspector[]{valueOI});
    Object result = piiMask.evaluate(new DeferredObject[]{new DeferredJavaObject(input)});
    assertEquals(expectValue, result);
  }

  void assertMaskString(PrimitiveObjectInspector valueOI, Object expectValue)
      throws Exception {
    piiMask.initialize(new ObjectInspector[]{valueOI});
    piiMask.cipher = new AesEncrypterDecrypter("DDIsx8K8IhIhd54D4QCgQFYUK8GNc9tibKCzuE97M/g=",
        "18f876ed20a561c2");
    Object result = piiMask.evaluateStringGroup();
    assertEquals(expectValue, result);
  }

}
