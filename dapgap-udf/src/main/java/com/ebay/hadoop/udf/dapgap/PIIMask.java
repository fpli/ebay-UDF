package com.ebay.hadoop.udf.dapgap;

import static org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory.javaByteArrayObjectInspector;
import static org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory.javaByteObjectInspector;
import static org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory.javaDateObjectInspector;
import static org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory.javaIntObjectInspector;
import static org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory.javaStringObjectInspector;
import static org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorUtils.PrimitiveGrouping.BINARY_GROUP;
import static org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorUtils.PrimitiveGrouping.DATE_GROUP;
import static org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorUtils.PrimitiveGrouping.NUMERIC_GROUP;
import static org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorUtils.PrimitiveGrouping.STRING_GROUP;
import static org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorUtils.PrimitiveGrouping.VOID_GROUP;
import static org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorUtils.getPrimitiveGrouping;

import java.util.Base64;
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
        + "-> SELECT _FUNC_(1300012125);\n" + "-777\n"
        + "-> SELECT _FUNC_('abc@gmail.com');\n"
        + "-> SELECT _FUNC_((date/timestamp)'2023-01-01') --> '1800-01-01';\n"
        + "-> SELECT _FUNC_(binary) --> base64 encode new byte[]{-,7,7,7};\n"
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
    checkArgGroups(arguments, 0, inputTypes, NUMERIC_GROUP, STRING_GROUP, DATE_GROUP, BINARY_GROUP, VOID_GROUP);
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
        return evaluateNumberGroup(valuePrimitiveCategory.equals(PrimitiveCategory.BYTE));
      case STRING_GROUP:
        setupCipher(arguments, 1, secretFileOI);
        return evaluateStringGroup();
      case DATE_GROUP:
        return evaluateDateGroup();
      case BINARY_GROUP:
        return evaluateBinaryGroup();
      default:
        throw new HiveException(String.format("Don't support %s type", valuePrimitiveCategory));
    }
  }

  protected Object evaluateNumberGroup(boolean isByte) throws HiveException {
    if (isByte) {
      return returnOIResolver.convertIfNecessary(MASK_BYTE_NUMBER, javaByteObjectInspector);
    }
    return returnOIResolver.convertIfNecessary(MASK_NUMBER, javaIntObjectInspector);
  }

  protected Object evaluateDateGroup() throws HiveException {
    return returnOIResolver.convertIfNecessary(MASK_DATE, javaDateObjectInspector);
  }

  protected Object evaluateBinaryGroup() throws HiveException {
    return returnOIResolver.convertIfNecessary(Base64.getEncoder().encode(MASK_BINARY), javaByteArrayObjectInspector);
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
