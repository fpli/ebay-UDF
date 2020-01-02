package com.ebay.hadoop.udf.common;


import com.ebay.hadoop.udf.common.encrypt.Base64Ebay;
import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.exec.UDFArgumentLengthException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspectorConverters;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;
import org.apache.hadoop.io.Text;

import java.util.ArrayList;
import java.util.List;

@Description(
        name = "DecodeAppFlags",
        value = "_FUNC_(app_flags) - Decode Sojourner app flags into an array of boolean")
public class DecodeAppFlags extends GenericUDF {

    private ObjectInspectorConverters.Converter converter;
    private List<Boolean> result = new ArrayList<Boolean>();

    @Override
    public ObjectInspector initialize(ObjectInspector[] arguments) throws UDFArgumentException {
        if (arguments.length != 1) {
            throw new UDFArgumentLengthException("The function decode_app_flags takes exactly 1 argument.");
        }

        converter = ObjectInspectorConverters.getConverter(arguments[0],
                PrimitiveObjectInspectorFactory.writableStringObjectInspector);

        return ObjectInspectorFactory.getStandardListObjectInspector(
                PrimitiveObjectInspectorFactory.javaBooleanObjectInspector);
    }

    @Override
    public Object evaluate(DeferredObject[] arguments) throws HiveException {
        assert (arguments.length == 1);
        result.clear();

        if (arguments[0].get() == null) {
            return result;
        }

        Text s = (Text) converter.convert(arguments[0].get());
        String fieldValue = s.toString();

        byte[] bytes = Base64Ebay.decode(fieldValue);
        for (int i = 0; i < bytes.length; i++) {
            int b = bytes[i];
            for (int j = 0; j < 8; j++) {
                boolean bit = (b & 0x00000080) == 0 ? false : true;
                result.add(bit);
                b = b << 1;
            }
        }
        return result;
    }

    @Override
    public String getDisplayString(String[] children) {
        assert (children.length == 1);
        return "decode_app_flags(" + children[0] + ")";
    }
}