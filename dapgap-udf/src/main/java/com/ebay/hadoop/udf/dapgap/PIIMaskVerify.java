package com.ebay.hadoop.udf.dapgap;

import static org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorUtils.PrimitiveGrouping.NUMERIC_GROUP;
import static org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorUtils.PrimitiveGrouping.STRING_GROUP;
import static org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorUtils.PrimitiveGrouping.VOID_GROUP;
import static org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorUtils.getPrimitiveGrouping;

import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.PrimitiveObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.PrimitiveObjectInspector.PrimitiveCategory;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorUtils;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorUtils.PrimitiveGrouping;
import org.apache.hadoop.io.IntWritable;

@Description(name = "pii_mask_verify",
    value = "_FUNC_(value, secret_file) - Returns a flag for the input:\n"
        + "flag=0: value equals to encrypt('" + PIIUDFBase.MASK_TEXT + "', secret_file) or 777\n"
        + "flag=1: value doesn't equal to encrypt('" + PIIUDFBase.MASK_TEXT
        + "', secret_file) and 777"
        + "flag=2: value is null",
    extended = "Example \n"
        + "-> SELECT _FUNC_('abc@gmail.com');\n" + "1\n"
        + "-> SELECT _FUNC_('abc@gmail.com', 'other_aes.properties');\n" + "1\n"
        + "-> SELECT _FUNC_(encrypt('" + PIIUDFBase.MASK_TEXT + "', 'aes.properties'), null);\n"
        + "0\n"
        + "-> SELECT _FUNC_(encrypt('" + PIIUDFBase.MASK_TEXT
        + "', 'other_aes.properties'), 'other_aes.properties');\n"
        + "0\n"
        + "-> SELECT _FUNC_(123);\n" + "1\n"
        + "-> SELECT _FUNC_(777);\n" + "0\n")
public class PIIMaskVerify extends PIIUDFBase {

  private final PrimitiveCategory[] inputTypes = new PrimitiveCategory[2];

  private PrimitiveObjectInspector valueOI;
  private PrimitiveObjectInspector secretFileOI;

  @Override
  public ObjectInspector initialize(ObjectInspector[] arguments) throws UDFArgumentException {
    checkArgsSize(arguments, 1, 2);

    checkArgPrimitive(arguments, 0);
    checkArgGroups(arguments, 0, inputTypes, NUMERIC_GROUP, STRING_GROUP, VOID_GROUP);
    valueOI = (PrimitiveObjectInspector) arguments[0];
    if (arguments.length > 1) {
      checkArgPrimitive(arguments, 1);
      checkArgGroups(arguments, 1, inputTypes, STRING_GROUP, VOID_GROUP);
      secretFileOI = (PrimitiveObjectInspector) arguments[1];
    } else {
      secretFileOI = null;
    }

    return PrimitiveObjectInspectorFactory.writableIntObjectInspector;
  }

  @Override
  public Object evaluate(DeferredObject[] arguments) throws HiveException {
    Object value = arguments[0].get();
    if (value == null) {
      return new IntWritable(2);
    }

    boolean isMaskValue = false;
    PrimitiveCategory valuePrimitiveCategory = inputTypes[0];
    PrimitiveGrouping valuePrimitiveGrouping = getPrimitiveGrouping(valuePrimitiveCategory);
    switch (valuePrimitiveGrouping) {
      case NUMERIC_GROUP:
        isMaskValue = verifyNumberGroup(value);
        break;
      case STRING_GROUP:
        setupCipher(arguments, 1, secretFileOI);
        isMaskValue = verifyStringGroup(value);
        break;
      default:
        throw new HiveException(String.format("Don't support %s type", valuePrimitiveCategory));
    }

    if (isMaskValue) {
      return new IntWritable(0);
    } else {
      return new IntWritable(1);
    }
  }

  protected boolean verifyNumberGroup(Object value) {
    int numValue = PrimitiveObjectInspectorUtils.getInt(value, valueOI);
    return DELETED_NUMBER_LIST.stream()
        .anyMatch(number -> number == numValue);
  }

  protected boolean verifyStringGroup(Object value) throws HiveException {
    String strValue = PrimitiveObjectInspectorUtils.getString(value, valueOI);
    return StringUtils.equals(strValue, encryptedMaskValue());
  }

  @Override
  public String getDisplayString(String[] children) {
    return getStandardDisplayString("pii_mask_verify", children);
  }

  @Override
  protected String getFuncName() {
    return "pii_mask_verify";
  }
}
