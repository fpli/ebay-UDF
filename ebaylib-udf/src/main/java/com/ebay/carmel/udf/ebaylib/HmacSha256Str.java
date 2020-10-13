package com.ebay.carmel.udf.ebaylib;

import com.ebay.carmel.udf.ebaylib.util.Sha256;
import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.exec.UDFArgumentLengthException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.PrimitiveObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.BinaryObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.StringObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.VoidObjectInspector;

import static com.ebay.carmel.udf.ebaylib.util.Constant.SHA256_DIGEST_LENGTH;

public class HmacSha256Str extends GenericUDF {

    private StringObjectInspector arg1;
    private PrimitiveObjectInspector arg2;

    public String evaluate(String textStr, String keyStr) {
        if(null == textStr || null == keyStr) return null;
        byte[] text = textStr.getBytes();
        byte[] key = keyStr.getBytes();
        byte[] digest = new byte[SHA256_DIGEST_LENGTH];
        Sha256.evaluate(text, text.length, key, key.length, digest);
        return toHexString(digest);
    }

    @Override
    public ObjectInspector initialize(ObjectInspector[] arguments) throws UDFArgumentException {
        if (arguments.length != 2) {
            throw new UDFArgumentLengthException(
                    "The function requires two argument, got "
                            + arguments.length);
        }
        if (arguments[0] instanceof VoidObjectInspector) return PrimitiveObjectInspectorFactory.javaStringObjectInspector;

        if (!(arguments[0] instanceof StringObjectInspector)) {
            throw new UDFArgumentException("first arg must be string type.");
        }
        arg1 = (StringObjectInspector) arguments[0];

        if (arguments[1] instanceof VoidObjectInspector) return PrimitiveObjectInspectorFactory.javaStringObjectInspector;
        if (!(arguments[1] instanceof StringObjectInspector || arguments[1] instanceof BinaryObjectInspector)) {
            throw new UDFArgumentException("second arg must be string or binary type.");
        }

        arg2 = (PrimitiveObjectInspector)arguments[1];
        return PrimitiveObjectInspectorFactory.javaStringObjectInspector;
    }

    @Override
    public Object evaluate(DeferredObject[] arguments) throws HiveException {
        if (null == arguments[0] ||
                null == arguments[0].get() ||
                null == arguments[1] ||
                null == arguments[1].get())
            return null;

        String textStr = this.arg1.getPrimitiveJavaObject(arguments[0].get());
        byte[] key;
        if (arg2 instanceof StringObjectInspector) {
            key = ((StringObjectInspector)arg2).getPrimitiveJavaObject(arguments[1].get()).getBytes();
        } else if (arg2 instanceof BinaryObjectInspector) {
            key = ((BinaryObjectInspector)arg2).getPrimitiveJavaObject(arguments[1].get());
        } else {
            key = new byte[0];
        }
        byte[] text = textStr.getBytes();
        byte[] digest = new byte[32];
        Sha256.evaluate(text, text.length, key, key.length, digest);
        return toHexString(digest);
    }

    private String toHexString(byte[] digest) {
        String result = "";
        for(int i = 0 ; i < digest.length ; i ++) {
            String hexStr = Long.toHexString(digest[i] & 0xff);
            if(hexStr.length() == 1) {
                hexStr = "0"+ hexStr;
            }
            result += hexStr;
        }
        return result.toUpperCase();
    }

    @Override
    public String getDisplayString(String[] children) {
        StringBuilder sb = new StringBuilder();
        sb.append("HmacSha256Str(");
        sb.append(children[0]);
        sb.append(children[1]);
        sb.append(")");
        return sb.toString();
    }
}
