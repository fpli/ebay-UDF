package com.ebay.hadoop.udf.dapgap;

import static org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorUtils.PrimitiveGrouping.NUMERIC_GROUP;
import static org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorUtils.PrimitiveGrouping.STRING_GROUP;
import static org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorUtils.PrimitiveGrouping.VOID_GROUP;

import java.io.UnsupportedEncodingException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import org.apache.hadoop.hive.common.type.HiveDecimal;
import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDFUtils;
import org.apache.hadoop.hive.serde2.io.HiveDecimalWritable;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspectorConverters;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspectorConverters.Converter;
import org.apache.hadoop.hive.serde2.objectinspector.PrimitiveObjectInspector.PrimitiveCategory;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorUtils;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorUtils.PrimitiveGrouping;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.ShortWritable;
import org.apache.hadoop.io.Text;

@Description(name = "gdpr_remediate",
    value = "_FUNC_(user, value) - Returns remediated value for this value according to its user",
    extended = "Example \n"
        + "-> SELECT _FUNC_(123, 'abc@gmail.com');\n" + "NRl/5n95st+Y5fmVHrHZ/Q==\n"
        + "-> SELECT _FUNC_(null, 'abc@gmail.com');\n" + "shGkpol9mzL5Ul3hkJ+SEA==\n"
        + "-> SELECT _FUNC_(123, 1300012125);\n" + "777\n"
        + "-> SELECT _FUNC_(null, 1300012125);\n" + "1300012125\n")
public class GdprRemediate extends GdprGenericUDF {

  private transient GenericUDFUtils.ReturnObjectInspectorResolver returnOIResolver;
  private transient PrimitiveCategory[] inputTypes = new PrimitiveCategory[2];
  private transient Converter converter;

  public ObjectInspector initialize(ObjectInspector[] arguments) throws UDFArgumentException {
    checkArgsSize(arguments, 2, 2);

    checkArgPrimitive(arguments, 0);
    checkArgPrimitive(arguments, 1);

    checkArgGroups(arguments, 0, inputTypes, NUMERIC_GROUP, VOID_GROUP);
    checkArgGroups(arguments, 1, inputTypes, NUMERIC_GROUP, STRING_GROUP, VOID_GROUP);

    aes = initAesEncrypterDecrypter();
    returnOIResolver = new GenericUDFUtils.ReturnObjectInspectorResolver(true);

    returnOIResolver.update(arguments[1]);

    ObjectInspector outOI = returnOIResolver.get();
    converter = ObjectInspectorConverters.getConverter(arguments[1], outOI);

    return outOI;
  }

  public Object evaluate(DeferredObject[] arguments) throws HiveException {
    Object o = arguments[0].get();
    if (o != null) {// need to do gdpr deletion
      switch (inputTypes[1]) {
        case SHORT:
          return new ShortWritable((short) DELETED_NUMBER);
        case INT:
          return new IntWritable(DELETED_NUMBER);
        case LONG:
          return new LongWritable(DELETED_NUMBER);
        case DECIMAL:
          return new HiveDecimalWritable(HiveDecimal.create(DELETED_NUMBER));
        case DOUBLE:
          return new DoubleWritable(DELETED_NUMBER);
        case FLOAT:
          return new FloatWritable(DELETED_NUMBER);
        case STRING:
        default:
          return new Text(DELETED_AES_STRING);
      }
    } else {
      // user is not in deleted list,
      //  if the pii column is string and not encrypted yet, encrypted it and return encrypted value
      //  if the pii column is number type, just return the origin value.
      PrimitiveGrouping primitiveGrouping = PrimitiveObjectInspectorUtils
          .getPrimitiveGrouping(inputTypes[1]);
      Object obj = arguments[1].get();
      switch (primitiveGrouping) {
        case STRING_GROUP:
          try {
            // To check if the string value is encrypted or not
            decrypt(obj);
          } catch (BadPaddingException | IllegalBlockSizeException e) {
            // the value is not encrypted, encrypt it!
            try {
              return new Text(encrypt(obj));
            } catch (BadPaddingException | IllegalBlockSizeException |
                UnsupportedEncodingException ee) {
              throw new HiveException("Error occurs when did aes encryption.", ee);
            }
          }
          return converter.convert(obj);
        case NUMERIC_GROUP:
          //TODO For now, just return the origin value without encryption for number pii column
          return converter.convert(obj);
        default:
          throw new HiveException("Don't support remediate type " + primitiveGrouping);
      }
    }
  }

  public String getDisplayString(String[] children) {
    return getStandardDisplayString("gdpr_remediate", children);
  }

}
