package com.ebay.hadoop.udf.soj;


import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspectorConverters;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.objectinspector.PrimitiveObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorUtils;

import java.util.LinkedHashMap;

/**
 * SojStringToMap.
 */
public class SojStringToMap extends GenericUDF {
    // Must be deterministic order map for consistent q-test output across Java versions - see HIVE-9161
    private final LinkedHashMap<Object, Object> ret = new LinkedHashMap<Object, Object>();
    private transient ObjectInspectorConverters.Converter soi_text, soi_de1 = null;
    final static String default_de1 = ",";

    @Override
    public ObjectInspector initialize(ObjectInspector[] arguments) throws UDFArgumentException {

        for (int idx = 0; idx < Math.min(arguments.length, 3); ++idx) {
            if (arguments[idx].getCategory() != ObjectInspector.Category.PRIMITIVE
                    || PrimitiveObjectInspectorUtils.getPrimitiveGrouping(
                    ((PrimitiveObjectInspector) arguments[idx]).getPrimitiveCategory())
                    != PrimitiveObjectInspectorUtils.PrimitiveGrouping.STRING_GROUP) {
                throw new UDFArgumentException("All argument should be string/character type");
            }
        }
        soi_text = ObjectInspectorConverters.getConverter(arguments[0],
                PrimitiveObjectInspectorFactory.javaStringObjectInspector);
        if (arguments.length > 1) {
            soi_de1 = ObjectInspectorConverters.getConverter(arguments[1],
                    PrimitiveObjectInspectorFactory.javaStringObjectInspector);
        }

        return ObjectInspectorFactory.getStandardMapObjectInspector(
                PrimitiveObjectInspectorFactory.javaIntObjectInspector,
                PrimitiveObjectInspectorFactory.javaStringObjectInspector);
    }

    @Override
    public Object evaluate(DeferredObject[] arguments) throws HiveException {
        ret.clear();
        String text = (String) soi_text.convert(arguments[0].get());
        if (text == null) {
            return ret;
        }

        String delimiter1 = (soi_de1 == null) ?
                default_de1 : (String) soi_de1.convert(arguments[1].get());

        if (delimiter1 == null) {
            delimiter1 = default_de1;
        }


        String[] values = text.split(delimiter1);

        for (int i = 0; i < values.length; i++) {

            if (StringUtils.isBlank(values[i])) {
                continue;
            }


            ret.put(i + 1, values[i]);
        }

        return ret;
    }

    @Override
    public String getDisplayString(String[] children) {
        assert (children.length <= 2);
        return getStandardDisplayString("str_to_map", children, ",");
    }
}
