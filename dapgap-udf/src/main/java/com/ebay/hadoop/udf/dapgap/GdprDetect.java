package com.ebay.hadoop.udf.dapgap;

import static org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorUtils.PrimitiveGrouping.NUMERIC_GROUP;
import static org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorUtils.PrimitiveGrouping.STRING_GROUP;
import static org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorUtils.PrimitiveGrouping.VOID_GROUP;

import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.PrimitiveObjectInspector.PrimitiveCategory;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorUtils;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorUtils.PrimitiveGrouping;
import org.apache.hadoop.io.Text;

@Description(name = "gdpr_detect",
    value = "_FUNC_(a1, a2, ...) - Returns gdpr detect result for this row.",
    extended = "Example\n"
        + "-> SELECT _FUNC_(123, 'abc@gmail.com');\n" + "Have column not encrypted\n"
        + "-> SELECT _FUNC_(123, 'NRl/5n95st+Y5fmVHrHZ/Q==');\n" + "Have column not deleted\n"
        + "-> SELECT _FUNC_(null, null);\n" + "All columns are null\n"
        + "-> SELECT _FUNC_(null, 'Deleted');\n" + "All columns are encrypted and deleted\n")
public class GdprDetect extends GdprGenericUDF {

  private static final String[] FAILED_CAUSE = new String[]{
      "Have column not encrypted",
      "Have column not deleted",
      "All columns are null",
      "All columns are encrypted and deleted",
      "Unknown"
  };
  private final Text output = new Text();
  private PrimitiveCategory[] inputTypes;

  public ObjectInspector initialize(ObjectInspector[] arguments) throws UDFArgumentException {
    checkArgsSize(arguments, 1, 30);

    inputTypes = new PrimitiveCategory[arguments.length];
    for (int i = 0; i < arguments.length; i++) {
      checkArgPrimitive(arguments, i);
      checkArgGroups(arguments, i, inputTypes, STRING_GROUP, NUMERIC_GROUP, VOID_GROUP);
    }
    aes = initAesEncrypterDecrypter();
    return PrimitiveObjectInspectorFactory.writableStringObjectInspector;
  }

  public Object evaluate(DeferredObject[] arguments) throws HiveException {
    boolean allPIINull = true;
    for (int i = 0; i < arguments.length; i++) {
      DeferredObject argument = arguments[i];
      Object o = argument.get();
      if (o != null) {
        allPIINull = false;
        PrimitiveGrouping primitiveGrouping = PrimitiveObjectInspectorUtils
            .getPrimitiveGrouping(inputTypes[i]);
        switch (primitiveGrouping) {
          case NUMERIC_GROUP:
            //TODO Because currently we don't have a standard of how de/encrypt number column
            //     So we can assume that all number column is not encrypted yet
            if (!String.valueOf(DELETED_NUMBER).equals(o.toString())) {
              //PII column not deleted
              output.set(FAILED_CAUSE[1]);
              return output;
            }
            break;
          case STRING_GROUP:
            //FIXME Currently some teams didn't encrypt `deleted` when did GDPR deletion
            if (!DELETED_STRING.equalsIgnoreCase(o.toString())) {
              try {
                byte[] decrypted = decrypt(o);
                if (!DELETED_STRING.equalsIgnoreCase(new String(decrypted))) {
                  //PII column not deleted
                  output.set(FAILED_CAUSE[1]);
                  return output;
                }
              } catch (Exception e) {
                //PII column not encrypted
                output.set(FAILED_CAUSE[0]);
                return output;
              }
            }
            break;
          default:
            throw new HiveException(String.format("Don't support %s type", primitiveGrouping));
        }
      }
    }
    if (allPIINull) {
      // All pii columns are null
      output.set(FAILED_CAUSE[2]);
    } else {
      // All pii columns are encrypted and deleted
      output.set(FAILED_CAUSE[3]);
    }
    return output;
  }

  public String getDisplayString(String[] children) {
    return getStandardDisplayString("gdpr_detect", children);
  }

}
