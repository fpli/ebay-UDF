package com.ebay.hadoop.udf.clsfd;


import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspectorConverters;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.objectinspector.PrimitiveObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorUtils;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import java.util.LinkedHashMap;

@Description(name="str_to_map2", value="_FUNC_(text, delimiter1, delimiter2) - Creates a map by parsing text ", extended="Split text into key-value pairs using two delimiters. The first delimiter seperates pairs, and the second delimiter sperates key and value. If only one parameter is given, default delimiters are used: ',' as delimiter1 and '=' as delimiter2.")
public class GenericUDFStringToMap2 extends GenericUDF
{
    private static final Logger logger = Logger.getLogger(GenericUDFStringToMap2.class);
    private final LinkedHashMap<Object, Object> ret = new LinkedHashMap();
    private transient ObjectInspectorConverters.Converter soi_text;
    private transient ObjectInspectorConverters.Converter soi_de1 = null;
    private transient ObjectInspectorConverters.Converter soi_de2 = null;
    static final String default_de1 = ",";
    static final String default_de2 = ":";

    public ObjectInspector initialize(ObjectInspector[] arguments) throws UDFArgumentException
    {
        //logger.setLevel(Level.INFO);
        //logger.addAppender(org.apache.log4j.ConsoleAppender);
        BasicConfigurator.configure();
        for (int idx = 0; idx < Math.min(arguments.length, 3); idx++) {
            if ( (arguments[idx].getCategory() != ObjectInspector.Category.PRIMITIVE) ||
                    ( PrimitiveObjectInspectorUtils.getPrimitiveGrouping(( (PrimitiveObjectInspector)arguments[idx] ).getPrimitiveCategory()) !=
                            PrimitiveObjectInspectorUtils.PrimitiveGrouping.STRING_GROUP)
            ){
                logger.error("All argument should be string/character type");
                throw new UDFArgumentException("All argument should be string/character type");
            }
        }
        this.soi_text = ObjectInspectorConverters.getConverter(arguments[0], PrimitiveObjectInspectorFactory.javaStringObjectInspector);

        if (arguments.length > 1) {
            this.soi_de1 = ObjectInspectorConverters.getConverter(arguments[1], PrimitiveObjectInspectorFactory.javaStringObjectInspector);
        }

        if (arguments.length > 2) {
            this.soi_de2 = ObjectInspectorConverters.getConverter(arguments[2], PrimitiveObjectInspectorFactory.javaStringObjectInspector);
        }

        return ObjectInspectorFactory.getStandardMapObjectInspector(PrimitiveObjectInspectorFactory.javaStringObjectInspector, PrimitiveObjectInspectorFactory.javaStringObjectInspector);
    }

    public Object evaluate(GenericUDF.DeferredObject[] arguments) throws HiveException
    {
        this.ret.clear();
        String text = (String)this.soi_text.convert(arguments[0].get());
        String delimiter1 = this.soi_de1 == null ? "," : (String)this.soi_de1.convert(arguments[1].get());
        String delimiter2 = this.soi_de2 == null ? ":" : (String)this.soi_de2.convert(arguments[2].get());

        String[] keyValuePairs = text.split(delimiter1);

        for (String keyValuePair : keyValuePairs) {
            String[] keyValue = keyValuePair.split(delimiter2, 2);
            if (keyValue.length < 2) {
                if (!this.ret.containsKey(keyValue[0])) {
                    this.ret.put(keyValuePair, null);
                }
            }
            else if (this.ret.containsKey(keyValue[0])) {
                String val = this.ret.get(keyValue[0]).toString() + ";" + keyValue[1];
                this.ret.put(keyValue[0], val);
            } else {
                this.ret.put(keyValue[0], keyValue[1]);
            }
        }

        return this.ret;
    }

    public String getDisplayString(String[] children)
    {
        assert (children.length > 0);

        StringBuilder sb = new StringBuilder();
        sb.append("GenericUDFStringToMap2()");
        sb.append(children[0]);
        sb.append(")");
        logger.error(sb.toString());

        return sb.toString();
    }
}
