package com.ebay.hadoop.udf.dapgap;

import static org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory.javaIntObjectInspector;
import static org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory.javaStringObjectInspector;
import static org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorUtils.PrimitiveGrouping.NUMERIC_GROUP;
import static org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorUtils.PrimitiveGrouping.STRING_GROUP;
import static org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorUtils.PrimitiveGrouping.VOID_GROUP;
import static org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorUtils.getPrimitiveGrouping;

import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDFUtils.ReturnObjectInspectorResolver;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.PrimitiveObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.PrimitiveObjectInspector.PrimitiveCategory;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorUtils.PrimitiveGrouping;

@Description(name = "pii_mask",
    value = "_FUNC_(value, secret_file) - Returns mask value for the input",
    extended = "Example \n"
        + "-> SELECT _FUNC_(1300012125);\n" + "777\n"
        + "-> SELECT _FUNC_('abc@gmail.com');\n"
        + "value equals to encrypt('" + PIIUDFBase.MASK_TEXT + "', 'aes.properties')\n"
        + "-> SELECT _FUNC_('abc@gmail.com', 'other_aes.properties');\n"
        + "value equals to encrypt('" + PIIUDFBase.MASK_TEXT + "', 'other_aes.properties')\n")
public class PIIMask extends PIIUDFBase {

  private final PrimitiveCategory[] inputTypes = new PrimitiveCategory[2];

  private PrimitiveObjectInspector secretFileOI;

  private ReturnObjectInspectorResolver returnOIResolver;

  @Override
  public ObjectInspector initialize(ObjectInspector[] arguments) throws UDFArgumentException {
    checkArgsSize(arguments, 1, 2);

    checkArgPrimitive(arguments, 0);
    checkArgGroups(arguments, 0, inputTypes, NUMERIC_GROUP, STRING_GROUP, VOID_GROUP);
    if (arguments.length > 1) {
      checkArgPrimitive(arguments, 1);
      checkArgGroups(arguments, 1, inputTypes, STRING_GROUP, VOID_GROUP);
      secretFileOI = (PrimitiveObjectInspector) arguments[1];
    } else {
      secretFileOI = null;
    }

    returnOIResolver = new ReturnObjectInspectorResolver(true);
    returnOIResolver.update(arguments[0]);
    return returnOIResolver.get();
  }

  @Override
  public Object evaluate(DeferredObject[] arguments) throws HiveException {
    PrimitiveCategory valuePrimitiveCategory = inputTypes[0];
    PrimitiveGrouping valuePrimitiveGrouping = getPrimitiveGrouping(valuePrimitiveCategory);
    switch (valuePrimitiveGrouping) {
      case NUMERIC_GROUP:
        return evaluateNumberGroup();
      case STRING_GROUP:
        setupCipher(arguments, 1, secretFileOI);
        return evaluateStringGroup();
      default:
        throw new HiveException(String.format("Don't support %s type", valuePrimitiveCategory));
    }
  }

  protected Object evaluateNumberGroup() throws HiveException {
    return returnOIResolver.convertIfNecessary(MASK_NUMBER, javaIntObjectInspector);
  }

  protected Object evaluateStringGroup() throws HiveException {
    return returnOIResolver.convertIfNecessary(encryptedMaskValue(), javaStringObjectInspector);
  }

  @Override
  public String getDisplayString(String[] children) {
    return getStandardDisplayString("pii_mask", children);
  }

  @Override
  protected String getFuncName() {
    return "pii_mask";
  }
}
