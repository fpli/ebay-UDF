package com.ebay.hadoop.udf.ep;

import com.ebay.hadoop.udf.ep.avro.core.MetricIdValue;
import com.ebay.hadoop.udf.ep.avro.core.TreatmentMetricSummary;
import com.ebay.hadoop.udf.ep.codec.DimensionCodec;
import com.ebay.hadoop.udf.ep.codec.TreatmentInfoCodec;
import com.ebay.hadoop.udf.ep.codec.TrtmtCombinationCodec;
import com.ebay.hadoop.udf.ep.meta.QualificationType;
import com.ebay.hadoop.udf.ep.meta.TreatedType;
import com.ebay.hadoop.udf.ep.meta.TreatmentType;
import com.google.common.collect.Lists;
import org.apache.hadoop.hive.ql.io.orc.OrcStruct;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF;
import org.apache.hadoop.hive.serde2.objectinspector.ListObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.StructField;
import org.apache.hadoop.hive.serde2.objectinspector.StructObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.typeinfo.ListTypeInfo;
import org.apache.hadoop.hive.serde2.typeinfo.StructTypeInfo;
import org.apache.hadoop.hive.serde2.typeinfo.TypeInfoFactory;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author zilchen
 */
public class EPUdfTest {
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue() throws HiveException {
        long combinationId = TrtmtCombinationCodec.encode(21435, 55833, 20, TreatmentType.Control);
        long treatmentInfo = TreatmentInfoCodec.encode(TreatedType.TRC, QualificationType.MOD);
        long dimIdValue = DimensionCodec.encodeDimensionIdValue(-3, 1);
        long inherentDimIdValue = DimensionCodec.encodeInherentDimension(0, 0, 1);

        GetExptId exptId = new GetExptId();
        assertEquals(21435, exptId.evaluate(combinationId));

        GetTreatmentId trtmtId = new GetTreatmentId();
        assertEquals(55833, trtmtId.evaluate(combinationId));

        GetTreatmentVersion trtmtVer = new GetTreatmentVersion();
        assertEquals(20, trtmtVer.evaluate(combinationId));

        GetTreatmentType trtmtType = new GetTreatmentType();
        assertEquals(TreatmentType.Control.ordinal(), trtmtType.evaluate(combinationId));

        GetTreatedType treatedType = new GetTreatedType();
        assertEquals(TreatedType.TRC.ordinal(), treatedType.evaluate(treatmentInfo));

        GetDimensionId dimId = new GetDimensionId();
        assertEquals(-3, dimId.evaluate(dimIdValue));

        GetDimensionValue dimValue = new GetDimensionValue();
        assertEquals(1, dimValue.evaluate(dimIdValue));

        GetDimensionValueById dimValueById = new GetDimensionValueById();
        assertEquals(1, dimValueById.evaluate(Lists.newArrayList(dimIdValue), -3));

        GetInherentDimensionValue inherentDimValue = new GetInherentDimensionValue();
        assertEquals(0, inherentDimValue.evaluate(inherentDimIdValue, 0));

        IsTreated isTreated = new IsTreated();
        assertTrue(isTreated.evaluate(treatmentInfo));

        IsMod isMod = new IsMod();
        assertTrue(isMod.evaluate(treatmentInfo));

        IsNQT isNQT = new IsNQT();
        assertFalse(isNQT.evaluate(treatmentInfo));

        // mock this as it's hard to compose a list of structure in hive originally from avro schema
        StructObjectInspector structObjectInspector = mock(StructObjectInspector.class);
        when(structObjectInspector.getTypeName()).thenReturn("MetricIdValue");
        StructField idStruct = new StructField() {
            @Override
            public String getFieldName() {
                return "id";
            }

            @Override
            public ObjectInspector getFieldObjectInspector() {
                return null;
            }

            @Override
            public int getFieldID() {
                return 0;
            }

            @Override
            public String getFieldComment() {
                return null;
            }
        };
        when(structObjectInspector.getStructFieldRef("id")).thenReturn(idStruct);
        StructField valueStruct = new StructField() {
            @Override
            public String getFieldName() {
                return "value";
            }

            @Override
            public ObjectInspector getFieldObjectInspector() {
                return null;
            }

            @Override
            public int getFieldID() {
                return 1;
            }

            @Override
            public String getFieldComment() {
                return null;
            }
        };
        when(structObjectInspector.getStructFieldRef("value")).thenReturn(valueStruct);
        when(structObjectInspector.getStructFieldData(any(MetricIdValue.class), eq(idStruct))).thenReturn(-1);
        when(structObjectInspector.getStructFieldData(any(MetricIdValue.class), eq(valueStruct))).thenReturn(100d);

        GetMetricValueById metricValueById = new GetMetricValueById() {
            @Override
            protected StructObjectInspector getStructObjectInspector() {
                return structObjectInspector;
            }
        };

        ObjectInspector[] inspectors = new ObjectInspector[2];
        ListTypeInfo listTypeInfo = new ListTypeInfo();
        StructTypeInfo structTypeInfo = new StructTypeInfo();
        structTypeInfo.setAllStructFieldNames(Lists.newArrayList("id", "value", "context"));
        structTypeInfo.setAllStructFieldTypeInfos(Lists.newArrayList(TypeInfoFactory.intTypeInfo,
                TypeInfoFactory.doubleTypeInfo, TypeInfoFactory.binaryTypeInfo));
        listTypeInfo.setListElementTypeInfo(structTypeInfo);
        inspectors[0] = OrcStruct.createObjectInspector(listTypeInfo);
        inspectors[1] = PrimitiveObjectInspectorFactory.writableIntObjectInspector;
        metricValueById.initialize(inspectors);

        GenericUDF.DeferredObject[] args = new GenericUDF.DeferredObject[2];
        args[0] = new GenericUDF.DeferredJavaObject(Lists.newArrayList(new MetricIdValue(-1, 100d, null)));
        args[1] = new GenericUDF.DeferredJavaObject(new IntWritable(-1));
        assertEquals(new DoubleWritable(100), metricValueById.evaluate(args));
    }


    @Test
    public void testTTestStats() throws Exception {
        ObjectInspector[] inspectors = new ObjectInspector[4];
        inspectors[0] = PrimitiveObjectInspectorFactory.writableIntObjectInspector;
        inspectors[1] = PrimitiveObjectInspectorFactory.writableLongObjectInspector;
        inspectors[2] = PrimitiveObjectInspectorFactory.writableIntObjectInspector;
        ListTypeInfo listTypeInfo = new ListTypeInfo();
        StructTypeInfo structTypeInfo = new StructTypeInfo();
        structTypeInfo.setAllStructFieldNames(Lists.newArrayList("combinationId", "trafficPct", "sum", "sumOfSquare", "sampleCount", "metricCount"));
        structTypeInfo.setAllStructFieldTypeInfos(Lists.newArrayList(TypeInfoFactory.longTypeInfo, TypeInfoFactory.doubleTypeInfo,
                TypeInfoFactory.doubleTypeInfo, TypeInfoFactory.doubleTypeInfo, TypeInfoFactory.longTypeInfo, TypeInfoFactory.longTypeInfo));
        listTypeInfo.setListElementTypeInfo(structTypeInfo);
        ListObjectInspector listInspector = (ListObjectInspector) OrcStruct.createObjectInspector(listTypeInfo);
        inspectors[3] = listInspector;
        StructObjectInspector structObjectInspector = mock(StructObjectInspector.class);
        GetTTestStatsValue getTTestStatsValue = new GetTTestStatsValue() {
            @Override
            void structNameCheck() {
                // do nothing
            }
            @Override
            StructObjectInspector getStructObjectInspector() {
                return structObjectInspector;
            }
        };
        getTTestStatsValue.initialize(inspectors);
        GenericUDF.DeferredObject[] args = new GenericUDF.DeferredObject[4];
        args[0] = new GenericUDF.DeferredJavaObject(new IntWritable(8600));
        args[1] = new GenericUDF.DeferredJavaObject(new LongWritable(1583218800000L));
        args[2] = new GenericUDF.DeferredJavaObject(new IntWritable(-4));
        TreatmentMetricSummary a = new TreatmentMetricSummary(1919060151248902L, 0.5d, 5.596848634340253E8,
                3.9954327147976044E11, 42316275L, 18867644L);
        TreatmentMetricSummary b = new TreatmentMetricSummary(-9221452942343788538L, 0.5d, 5.608571462902747E8,
                4.0402905210581995E11, 42313924L, 18858982L);
        StructField comb = mock(StructField.class);
        when(structObjectInspector.getStructFieldRef("combinationId")).thenReturn(comb);
        when(structObjectInspector.getStructFieldData(eq(a),eq(comb))).thenReturn(1919060151248902L);
        when(structObjectInspector.getStructFieldData(eq(b),eq(comb))).thenReturn(-9221452942343788538L);
        StructField traf = mock(StructField.class);
        when(structObjectInspector.getStructFieldRef("trafficPct")).thenReturn(traf);
        when(structObjectInspector.getStructFieldData(any(),eq(traf))).thenReturn(0.5d);
        StructField sum = mock(StructField.class);
        when(structObjectInspector.getStructFieldRef("sum")).thenReturn(sum);
        when(structObjectInspector.getStructFieldData(any(),eq(sum))).thenReturn(5.596848634340253E8);
        StructField sumSquare = mock(StructField.class);
        when(structObjectInspector.getStructFieldRef("sumofsquare")).thenReturn(sumSquare);
        when(structObjectInspector.getStructFieldData(any(),eq(sumSquare))).thenReturn(3.9954327147976044E11);
        StructField sampleCount = mock(StructField.class);
        when(structObjectInspector.getStructFieldRef("sampleCount")).thenReturn(sampleCount);
        when(structObjectInspector.getStructFieldData(any(),eq(sampleCount))).thenReturn(42316275L);
        StructField metricCount = mock(StructField.class);
        when(structObjectInspector.getStructFieldRef("metricCount")).thenReturn(metricCount);
        when(structObjectInspector.getStructFieldData(any(),eq(metricCount))).thenReturn(18867644L);
        args[3] = new GenericUDF.DeferredJavaObject(Lists.newArrayList(a, b));
        getTTestStatsValue.evaluate(args);

    }
}
