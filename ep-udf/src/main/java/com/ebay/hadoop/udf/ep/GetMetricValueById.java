package com.ebay.hadoop.udf.ep;

import com.ebay.hadoop.udf.ep.avro.core.MetricIdValue;
import com.google.common.annotations.VisibleForTesting;
import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF;
import org.apache.hadoop.hive.serde2.io.DoubleWritable;
import org.apache.hadoop.hive.serde2.objectinspector.ListObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.PrimitiveObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.StructObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;

/**
 * @author zilchen
 */
public class GetMetricValueById extends GenericUDF {
    private ListObjectInspector listInputObjectInspector;
    private PrimitiveObjectInspector primitiveObjectInspector;
    private StructObjectInspector structObjectInspector;

    public ObjectInspector initialize(ObjectInspector[] args) throws UDFArgumentException {
        assert (args.length == 2); // This UDF accepts 2 argument
        // The first argument is a list
        assert (args[0].getCategory() == ObjectInspector.Category.LIST);
        assert (args[1].getCategory() == ObjectInspector.Category.PRIMITIVE);
        listInputObjectInspector = (ListObjectInspector) args[0];
        primitiveObjectInspector = (PrimitiveObjectInspector) args[1];
        structObjectInspector = getStructObjectInspector();
        assert (MetricIdValue.class.getSimpleName().equalsIgnoreCase(structObjectInspector.getTypeName()));
        return PrimitiveObjectInspectorFactory.writableDoubleObjectInspector;
    }

    @VisibleForTesting
    protected StructObjectInspector getStructObjectInspector() {
        if (structObjectInspector == null) {
            structObjectInspector = (StructObjectInspector) listInputObjectInspector.getListElementObjectInspector();
        }
        return structObjectInspector;
    }

    public Object evaluate(GenericUDF.DeferredObject[] args) throws HiveException {
        if (args.length != 2) {
            return null;
        }
        Object oin = args[0].get();
        int wantedMetricId = (int) primitiveObjectInspector.getPrimitiveJavaObject(args[1].get());

        if (oin == null) {
            return null;
        }
        Double value = null;
        int nbElements = listInputObjectInspector.getListLength(oin);
        for (int i = 0; i < nbElements; i++) {
            Object metricIdValue = listInputObjectInspector.getListElement(oin, i);
            int metricId = (int) structObjectInspector.getStructFieldData(metricIdValue, structObjectInspector.getStructFieldRef("id"));
            if (metricId == wantedMetricId) {
                value = (double) structObjectInspector.getStructFieldData(metricIdValue, structObjectInspector.getStructFieldRef("value"));
                break;
            }
        }
        if (value == null) {
            return null;
        }
        return new DoubleWritable(value);
    }

    @Override
    public String getDisplayString(String[] children) {
        return "GetMetricValueById";
    }
}
