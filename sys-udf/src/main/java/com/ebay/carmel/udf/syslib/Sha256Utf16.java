package com.ebay.carmel.udf.syslib;

import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.exec.UDFArgumentLengthException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.StringObjectInspector;
import org.apache.log4j.Logger;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Sha256Utf16 extends GenericUDF {

    private static final Logger logger = Logger.getLogger(Sha256Utf16.class);

    StringObjectInspector input;
    @Override
    public ObjectInspector initialize(ObjectInspector[] arguments) throws UDFArgumentException {
        if (arguments.length  != 1) {
            throw new UDFArgumentLengthException(
                    "The function requires at one argument, got "
                            + arguments.length);
        }
        if (!(arguments[0] instanceof StringObjectInspector)) {
            throw new UDFArgumentException("Only string supported.");
        }
        this.input = (StringObjectInspector) arguments[0];
        return PrimitiveObjectInspectorFactory.javaByteArrayObjectInspector;
    }

    @Override
    public Object evaluate(DeferredObject[] arguments) throws HiveException {
        String strValue = this.input.getPrimitiveJavaObject(arguments[0].get());
        if(null == strValue) return null;
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new HiveException(e.getMessage());
        }
        byte[] encodedhash = digest.digest(strValue.getBytes());
        return encodedhash;
    }

    @Override
    public String getDisplayString(String[] children) {
        assert children.length  > 0;
        StringBuilder sb = new StringBuilder();
        sb.append("Sha256UtfByteGenericUDF(");
        sb.append(children[0]);
        sb.append(")");
        return sb.toString();
    }
}
