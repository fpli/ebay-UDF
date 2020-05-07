package com.ebay.hadoop.udf.ep;

import com.ebay.hadoop.udf.ep.avro.core.TreatmentMetricSummary;
import com.ebay.hadoop.udf.ep.meta.TreatmentType;
import com.ebay.hadoop.udf.ep.stats.ReadoutConfig;
import com.ebay.hadoop.udf.ep.stats.module.MetricStatsEssence;
import com.ebay.hadoop.udf.ep.stats.module.MetricStatsSummary;
import com.ebay.hadoop.udf.ep.stats.module.MetricSummary;
import com.ebay.hadoop.udf.ep.stats.module.MetricSummaryGroup;
import com.ebay.hadoop.udf.ep.stats.module.PITTreatmentMetricSummary;
import com.ebay.hadoop.udf.ep.stats.module.StatsAlgorithmType;
import com.ebay.hadoop.udf.ep.stats.module.TreatmentMetricsSummary;
import com.google.common.annotations.VisibleForTesting;
import org.apache.commons.lang.mutable.MutableInt;
import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF;
import org.apache.hadoop.hive.serde2.objectinspector.ListObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.objectinspector.PrimitiveObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.StandardStructObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.StructObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.LongWritable;
import com.ebay.hadoop.udf.ep.codec.TrtmtCombinationCodec;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GetTTestStatsValue extends GenericUDF {
    private ListObjectInspector listInputObjectInspector;
    private PrimitiveObjectInspector reportIdInspector;
    private PrimitiveObjectInspector timeKeyInspector;
    private PrimitiveObjectInspector metricIdInspector;
    private StructObjectInspector structObjectInspector;

    public ObjectInspector initialize(ObjectInspector[] args) throws UDFArgumentException {
        if (!(args.length == 4)) {
            throw new UDFArgumentException("have " + args.length + " arguments, expect 4 arguments");
            // This UDF accepts 4 argument
        }
        if (!(args[0].getCategory() == ObjectInspector.Category.PRIMITIVE)) {
            throw new UDFArgumentException("1st arg type is  " + args[0].getCategory().name() + "expect PRIMITIVE.");
        }
        if (!(args[1].getCategory() == ObjectInspector.Category.PRIMITIVE)) {
            throw new UDFArgumentException("2nd arg type is  " + args[1].getCategory().name() + "expect PRIMITIVE.");
        }
        if (!(args[2].getCategory() == ObjectInspector.Category.PRIMITIVE)) {
            throw new UDFArgumentException("3rd arg type is  " + args[2].getCategory().name() + "expect PRIMITIVE.");
        }
        if (!(args[3].getCategory() == ObjectInspector.Category.LIST)) {
            throw new UDFArgumentException("4th arg type is  " + args[3].getCategory().name() + "expect LIST.");
        }
        reportIdInspector = (PrimitiveObjectInspector) args[0];
        timeKeyInspector = (PrimitiveObjectInspector) args[1];
        metricIdInspector = (PrimitiveObjectInspector) args[2];
        listInputObjectInspector = (ListObjectInspector) args[3];
        structObjectInspector = (StructObjectInspector) listInputObjectInspector.getListElementObjectInspector();
        List<String> structFieldNames = new ArrayList<>();
        List<ObjectInspector> structFieldObjectInspectors = new ArrayList<>();
        // fill struct field names
        structFieldNames.add("combinationid");
        structFieldObjectInspectors.add(PrimitiveObjectInspectorFactory.writableLongObjectInspector);
        structFieldNames.add("normalized");
        structFieldObjectInspectors.add(PrimitiveObjectInspectorFactory.writableDoubleObjectInspector);
        structFieldNames.add("lift");
        structFieldObjectInspectors.add(PrimitiveObjectInspectorFactory.writableDoubleObjectInspector);
        structFieldNames.add("cp");
        structFieldObjectInspectors.add(PrimitiveObjectInspectorFactory.writableDoubleObjectInspector);
        structFieldNames.add("p_value");
        structFieldObjectInspectors.add(PrimitiveObjectInspectorFactory.writableDoubleObjectInspector);
        structFieldNames.add("lower_ci");
        structFieldObjectInspectors.add(PrimitiveObjectInspectorFactory.writableDoubleObjectInspector);
        structFieldNames.add("upper_ci");
        structFieldObjectInspectors.add(PrimitiveObjectInspectorFactory.writableDoubleObjectInspector);
        StandardStructObjectInspector structObjectInspector = ObjectInspectorFactory.getStandardStructObjectInspector(structFieldNames,
                structFieldObjectInspectors);
        return ObjectInspectorFactory.getStandardListObjectInspector(structObjectInspector);
    }

    public Object evaluate(DeferredObject[] args) throws HiveException {
        if (args.length != 4) {
            return null;
        }
        int reportId = (int) reportIdInspector.getPrimitiveJavaObject(args[0].get());
        long timeKey = (long) timeKeyInspector.getPrimitiveJavaObject(args[1].get());
        int metricId = (int) metricIdInspector.getPrimitiveJavaObject(args[2].get());
        Object oin = args[3].get();
        if (oin == null) {
            return null;
        }
        ReadoutConfig readoutConfig = new ReadoutConfig(StatsAlgorithmType.TTEST, 0.1d);
        List<Long> combinationIdList = new ArrayList<>();
        MetricSummaryGroup metricSummaryGroup = new MetricSummaryGroup(reportId, metricId, timeKey);
        int nbElements = listInputObjectInspector.getListLength(oin);
        for (int i = 0; i < nbElements; i++) {
            Object treatmentMetricSummary = listInputObjectInspector.getListElement(oin, i);
            StructObjectInspector structObjectInspector = getStructObjectInspector();
            long combinationId = (long) structObjectInspector.getStructFieldData(treatmentMetricSummary,
                    structObjectInspector.getStructFieldRef("combinationId"));
            double trafficPct = (double) structObjectInspector.getStructFieldData(treatmentMetricSummary,
                    structObjectInspector.getStructFieldRef("trafficPct"));
            double sum = (double) structObjectInspector.getStructFieldData(treatmentMetricSummary,
                    structObjectInspector.getStructFieldRef("sum"));
            double sumOfSquare = (double) structObjectInspector.getStructFieldData(treatmentMetricSummary,
                    structObjectInspector.getStructFieldRef("sumofsquare"));
            long sampleCount = (long) structObjectInspector.getStructFieldData(treatmentMetricSummary,
                    structObjectInspector.getStructFieldRef("sampleCount"));
            long metricCount = (long) structObjectInspector.getStructFieldData(treatmentMetricSummary,
                    structObjectInspector.getStructFieldRef("metricCount"));
            combinationIdList.add(combinationId);
            MetricSummary metricSummary = new MetricStatsEssence(metricId, sum, sumOfSquare, metricCount, sampleCount, timeKey);
            PITTreatmentMetricSummary pitTreatmentMetricSummary = new PITTreatmentMetricSummary(
                    TrtmtCombinationCodec.getTreatmentId(combinationId), trafficPct,
                    TrtmtCombinationCodec.getTreatType(combinationId), timeKey, metricId, metricSummary);
            metricSummaryGroup.attach(pitTreatmentMetricSummary);
        }
        List<MetricStatsSummary> metricStatsSummaryList = readoutConfig.calcStats(metricSummaryGroup)
                .stream()
                .filter(metricStatsSummary -> TrtmtCombinationCodec.getTreatType(getCombinationId(combinationIdList,
                        metricStatsSummary.getTreatmentId())) == TreatmentType.Treatment)
                .collect(Collectors.toList());
        Object[] summaryList = new Object[metricStatsSummaryList.size()];
        MutableInt index = new MutableInt(0);
        metricStatsSummaryList.forEach(metricStatsSummary -> {
            long combinationId = getCombinationId(combinationIdList, metricStatsSummary.getTreatmentId());
            Object[] summaryStruct = new Object[7];
            summaryStruct[0] = new LongWritable(combinationId);
            summaryStruct[1] = new DoubleWritable(metricStatsSummary.getNormalized());
            summaryStruct[2] = new DoubleWritable(metricStatsSummary.getLift());
            summaryStruct[3] = new DoubleWritable(metricStatsSummary.getCp());
            summaryStruct[4] = new DoubleWritable(metricStatsSummary.getPvalue());
            summaryStruct[5] = new DoubleWritable(metricStatsSummary.getLowerCI());
            summaryStruct[6] = new DoubleWritable(metricStatsSummary.getUpperCI());
            summaryList[index.intValue()] = summaryStruct;
            index.increment();
        });
        return summaryList;
    }

    @Override
    public String getDisplayString(String[] children) {
        return "GetTTestStatsValue";
    }

    private long getCombinationId(List<Long> combinationIdList, long treatmentId) {
        List<Long> filteredList = combinationIdList.stream()
                .filter(combinationId -> TrtmtCombinationCodec.getTreatmentId(combinationId) == treatmentId)
                .collect(Collectors.toList());
        assert filteredList.size() == 1;
        return filteredList.get(0);
    }

    @VisibleForTesting
    void structNameCheck() {
        if (!(TreatmentMetricSummary.class.getSimpleName().equalsIgnoreCase(structObjectInspector.getTypeName()))) {
            throw new RuntimeException("the 4th args type is " + structObjectInspector.getTypeName()
                    + " expect " + TreatmentMetricsSummary.class.getSimpleName());
        }
    }

    @VisibleForTesting
    StructObjectInspector getStructObjectInspector() {
        return structObjectInspector;
    }


}
