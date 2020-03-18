package com.ebay.hadoop.udf.soj;

import java.util.LinkedHashMap;

import com.ebay.hadoop.udf.utils.SojUtils;
import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.exec.UDFArgumentLengthException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.StringObjectInspector;

public class SojFunctionNoDecode extends GenericUDF {
    StringObjectInspector urlOI;
    StringObjectInspector inputOI;
    LinkedHashMap<Object, Object> result = new LinkedHashMap<Object, Object>();

    @Override
    public Object evaluate(DeferredObject[] args) throws HiveException {
        String payload = urlOI.getPrimitiveJavaObject(args[0].get());
        String keywords = inputOI.getPrimitiveJavaObject(args[1].get());
        if (payload == null || keywords == null) return null;

        return SojUtils.parsePayload(payload, keywords, 0);
    }

    @Override
    public String getDisplayString(String[] args) {
        return "SojFunction to parse key-value pairs from behavior URL, no decoding";
    }

    @Override
    public ObjectInspector initialize(ObjectInspector[] args) throws UDFArgumentException {
        if (args.length != 2) {
            throw new UDFArgumentLengthException("SojFunction takes two Strings as input, first is url, second is keys seperated by comma");
        }
        ObjectInspector a = args[0];
        ObjectInspector b = args[1];

        if (!(a instanceof StringObjectInspector) || !(b instanceof StringObjectInspector)) {
            throw new UDFArgumentException("two arguments must be string");
        }

        this.urlOI = (StringObjectInspector) a;
        this.inputOI = (StringObjectInspector) b;

        return ObjectInspectorFactory.getStandardMapObjectInspector(PrimitiveObjectInspectorFactory.javaStringObjectInspector,
                PrimitiveObjectInspectorFactory.javaStringObjectInspector);
    }

    ;
}