package com.ebay.hadoop.udf.dapgap;

import static com.ebay.hadoop.udf.dapgap.PIIUDFBase.MASK_BINARY;
import static com.ebay.hadoop.udf.dapgap.PIIUDFBase.MASK_DATE;
import static org.junit.Assert.assertEquals;

import com.ebay.hadoop.udf.common.encrypt.AesEncrypterDecrypter;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Base64;
import org.apache.hadoop.hive.common.type.HiveChar;
import org.apache.hadoop.hive.common.type.HiveVarchar;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF.DeferredJavaObject;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF.DeferredObject;
import org.apache.hadoop.hive.serde2.io.DateWritable;
import org.apache.hadoop.hive.serde2.io.DoubleWritable;
import org.apache.hadoop.hive.serde2.io.HiveCharWritable;
import org.apache.hadoop.hive.serde2.io.HiveVarcharWritable;
import org.apache.hadoop.hive.serde2.io.ShortWritable;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.PrimitiveObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;
import org.apache.hadoop.io.ByteWritable;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.junit.Test;

public class PIIMaskTest {

  PIIMask piiMask = new PIIMask();

  @Test
  public void testMaskNumber() throws HiveException {
    assertMaskNumber(PrimitiveObjectInspectorFactory.javaByteObjectInspector, (byte) 1,
        new ByteWritable((byte) -77));
    assertMaskNumber(PrimitiveObjectInspectorFactory.javaShortObjectInspector, (short) 1,
        new ShortWritable((short) -777));
    assertMaskNumber(PrimitiveObjectInspectorFactory.javaIntObjectInspector, 1,
        new IntWritable(-777));
    assertMaskNumber(PrimitiveObjectInspectorFactory.javaLongObjectInspector, 1L,
        new LongWritable(-777L));
    assertMaskNumber(PrimitiveObjectInspectorFactory.javaFloatObjectInspector, 1F,
        new FloatWritable(-777F));
    assertMaskNumber(PrimitiveObjectInspectorFactory.javaDoubleObjectInspector, 1D,
        new DoubleWritable(-777D));
  }

  @Test
  public void testMaskDate() throws HiveException {
    assertMaskObject(PrimitiveObjectInspectorFactory.javaDateObjectInspector, new Date(536120000L),
        new DateWritable(MASK_DATE));
    assertMaskObject(PrimitiveObjectInspectorFactory.javaDateObjectInspector,
        new Timestamp(55454320000L), new DateWritable(MASK_DATE));
  }

  @Test
  public void testMaskBinary() throws HiveException {
    byte[] testCase = {'3', '3', '3'};
    assertMaskObject(PrimitiveObjectInspectorFactory.javaByteArrayObjectInspector,
        Base64.getEncoder().encode(testCase),
        new BytesWritable(Base64.getEncoder().encode(MASK_BINARY)));
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

  void assertMaskObject(PrimitiveObjectInspector valueOI, Object input, Object expectValue)
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
