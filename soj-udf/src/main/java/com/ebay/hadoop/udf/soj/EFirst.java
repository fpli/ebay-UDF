package com.ebay.hadoop.udf.soj;

import org.apache.hadoop.hive.ql.exec.UDFArgumentTypeException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.parse.SemanticException;
import org.apache.hadoop.hive.ql.udf.generic.AbstractGenericUDAFResolver;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDAFEvaluator;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspectorUtils;
import org.apache.hadoop.hive.serde2.typeinfo.TypeInfo;

public class EFirst extends AbstractGenericUDAFResolver {


    @Override
    public GenericUDAFEvaluator getEvaluator(TypeInfo[] parameters) throws SemanticException {
        if (parameters.length != 2) {
            throw new UDFArgumentTypeException(parameters.length - 1, "two argument is expected.");
        } else {

            return new GenericUDAFAbstractRowNumberEvaluator();

        }

    }

    static class EFirstBuffer implements GenericUDAFEvaluator.AggregationBuffer {

        Object val;
        Object seqNum;
        boolean firstRow;

        void init() {
            val = null;
            seqNum = null;
            firstRow = true;
        }

        EFirstBuffer() {
            init();
        }
    }

    public static class GenericUDAFAbstractRowNumberEvaluator extends GenericUDAFEvaluator {
        ObjectInspector inputOI1;
        ObjectInspector inputOI2;
        ObjectInspector outputOI;


        @Override
        public ObjectInspector init(Mode m, ObjectInspector[] parameters) throws HiveException {
            super.init(m, parameters);
            if (m != Mode.COMPLETE) {
                throw new HiveException("Only COMPLETE mode supported for EFirst function");
            }
            inputOI1 = parameters[0];
            inputOI2 = parameters[1];

            outputOI = ObjectInspectorUtils.getStandardObjectInspector(inputOI1,
                    ObjectInspectorUtils.ObjectInspectorCopyOption.WRITABLE);
            return outputOI;
        }

        @Override
        public AggregationBuffer getNewAggregationBuffer() throws HiveException {
            return new EFirstBuffer();
        }

        @Override
        public void reset(AggregationBuffer agg) throws HiveException {
            ((EFirstBuffer) agg).init();
        }

        @Override
        public void iterate(AggregationBuffer agg, Object[] parameters) throws HiveException {
            EFirstBuffer lb = (EFirstBuffer) agg;

            if (lb.firstRow) {
                lb.val = ObjectInspectorUtils.copyToStandardObject(parameters[0], inputOI1,
                        ObjectInspectorUtils.ObjectInspectorCopyOption.WRITABLE);
                lb.seqNum = parameters[1];
                lb.firstRow = false;
            }

            if (parameters[0] != null) {
                if (isGreater(lb.seqNum, parameters[1])) {
                    lb.val = ObjectInspectorUtils.copyToStandardObject(parameters[0], inputOI1,
                            ObjectInspectorUtils.ObjectInspectorCopyOption.WRITABLE);
                    lb.seqNum = parameters[1];
                }
            }
        }

        private boolean isGreater(Object in, Object last) {
            if (in == null) {
                return false;
            } else if (last == null) {
                return true;
            } else {
                return ObjectInspectorUtils.compare(in, inputOI2, last, inputOI2) > 0;
            }
        }

        @Override
        public Object terminatePartial(AggregationBuffer agg) throws HiveException {
            throw new HiveException("terminatePartial not supported");
        }

        @Override
        public void merge(AggregationBuffer agg, Object partial) throws HiveException {
            throw new HiveException("merge not supported");
        }

        @Override
        public Object terminate(AggregationBuffer agg) throws HiveException {
            EFirstBuffer lb = (EFirstBuffer) agg;
            return lb.val;
        }

    }
}
