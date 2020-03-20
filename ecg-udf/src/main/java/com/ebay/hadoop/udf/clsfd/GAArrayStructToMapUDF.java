package com.ebay.hadoop.udf.clsfd;


import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.exec.UDFArgumentLengthException;
import org.apache.hadoop.hive.ql.exec.UDFArgumentTypeException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF;
import org.apache.hadoop.hive.serde2.objectinspector.*;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.IntObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.JavaStringObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import java.util.LinkedHashMap;

@Description(name="ga_array_struct_to_map", value="_FUNC_(array) - Creates a map of index/value pairs from an array of structs ", extended="structs must contain 'index' and 'value' fields")
public class GAArrayStructToMapUDF extends GenericUDF
{
    private static final Logger logger = Logger.getLogger(GAArrayStructToMapUDF.class);
    private final LinkedHashMap<Object, Object> ret = new LinkedHashMap();
    private transient ListObjectInspector loi;
    private transient StructObjectInspector soi;
    private transient ObjectInspector idxoi;
    private transient ObjectInspector valoi;

    public ObjectInspector initialize(ObjectInspector[] arguments) throws UDFArgumentException
    {
        //logger.setLevel(Level.INFO);
        //logger.addAppender(org.apache.log4j.ConsoleAppender);
        BasicConfigurator.configure();
        if (arguments.length != 1) {
            logger.error("ga_array_struct_to_map() accepts exactly one argument.");
            throw new UDFArgumentLengthException("ga_array_struct_to_map() accepts exactly one argument.");
        }

        if (arguments[0].getCategory() != ObjectInspector.Category.LIST) {
            logger.error("The single argument to GAArrayStructToMap should be Arraybut " + arguments[0].getTypeName() + " is found");
            throw new UDFArgumentTypeException(0, "The single argument to GAArrayStructToMap should be Arraybut " + arguments[0]
                    .getTypeName() + " is found");
        }

        if (((ListObjectInspector)arguments[0]).getListElementObjectInspector().getCategory() != ObjectInspector.Category.STRUCT) {
            logger.error("The elements of array should be STRUCT, but" + ((ListObjectInspector)arguments[0]).getListElementObjectInspector().getTypeName() + " is found");
            throw new UDFArgumentTypeException(0, "The elements of array should be STRUCT, but" + ((ListObjectInspector)arguments[0]).getListElementObjectInspector().getTypeName() + " is found");
        }

        this.loi = ((ListObjectInspector)arguments[0]);
        this.soi = ((StructObjectInspector)this.loi.getListElementObjectInspector());

        if (this.soi.getAllStructFieldRefs().size() != 2){
            logger.error("Incorrect number of fields in the struct.  The number of fields should be 2, but " + this.soi.getAllStructFieldRefs().size() + " fields are found");
            throw new UDFArgumentTypeException(0, "Incorrect number of fields in the struct.  The number of fields should be 2, but " + this.soi
                    .getAllStructFieldRefs().size() + " fields are found");
        }

        StructField index = this.soi.getStructFieldRef("index");
        StructField value = this.soi.getStructFieldRef("value");

        if (index == null) {
            logger.error("index field is not found in struct");
            throw new UDFArgumentTypeException(0, "index field is not found in struct");
        }

        if (value == null) {
            logger.error("value field is not found in struct");
            throw new UDFArgumentTypeException(0, "value field is not found in struct");
        }

        this.idxoi = index.getFieldObjectInspector();
        this.valoi = value.getFieldObjectInspector();

        if (this.idxoi.getCategory() != ObjectInspector.Category.PRIMITIVE) {
            logger.error("index field should be primitive type, but " + this.idxoi.getTypeName() + " is found");
            throw new UDFArgumentTypeException(0, "index field should be primitive type, but " + this.idxoi.getTypeName() + " is found");
        }

        if (this.valoi.getCategory() != ObjectInspector.Category.PRIMITIVE) {
            logger.error("value field should be primitive, but " + this.valoi.getTypeName() + " is found");
            throw new UDFArgumentTypeException(0, "value field should be primitive, but " + this.valoi.getTypeName() + " is found");
        }

        MapObjectInspector mi2 = ObjectInspectorFactory.getStandardMapObjectInspector(PrimitiveObjectInspectorFactory.javaIntObjectInspector, PrimitiveObjectInspectorFactory.javaStringObjectInspector);

        return mi2;
    }

    public Object evaluate(GenericUDF.DeferredObject[] arguments) throws HiveException
    {
        this.ret.clear();

        if (arguments.length != 1) {
            logger.warn("input arguments length not equals 1");
            return null;
        }

        if (arguments[0].get() == null) {
            logger.warn("input arguments value is null");
            return null;
        }

        int nelements = this.loi.getListLength(arguments[0].get());

        for (int i = 0; i < nelements; i++)
        {
            Object o = this.loi.getListElement(arguments[0].get(), i);
            StructField structField = this.soi.getStructFieldRef("index");
            Object idxObj = this.soi.getStructFieldData(o, structField);

            int index = ((IntObjectInspector)this.idxoi).get(idxObj);

            Object valObj = this.soi.getStructFieldData(this.loi.getListElement(arguments[0].get(), i), this.soi.getStructFieldRef("value"));
            String value = ((JavaStringObjectInspector)this.valoi).getPrimitiveJavaObject(valObj);

            this.ret.put(Integer.valueOf(index), value.replace("\n", ""));

        }

      return this.ret;
    }

    public String getDisplayString(String[] children)
    {
        assert (children.length > 0);

        StringBuilder sb = new StringBuilder();
        sb.append("GAArrayStructToMapUDF()");
        sb.append(children[0]);
        sb.append(")");
        logger.error(sb.toString());

        return sb.toString();
    }
}