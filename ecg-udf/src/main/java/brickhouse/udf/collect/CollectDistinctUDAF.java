package brickhouse.udf.collect;

import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.exec.UDFArgumentTypeException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.parse.SemanticException;
import org.apache.hadoop.hive.ql.udf.generic.AbstractGenericUDAFResolver;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDAFEvaluator;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspectorUtils;
import org.apache.hadoop.hive.serde2.objectinspector.StandardListObjectInspector;
import org.apache.hadoop.hive.serde2.typeinfo.TypeInfo;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/*
 *  official github code detail see :
 *  https://github.com/klout/brickhouse/blob/master/src/main/java/brickhouse/udf/collect/CollectDistinctUDAF.java
 */

@Description(name="collect_distinct", value="_FUNC_(x) - Returns an array of distinct the elements in the aggregation group ")
public class CollectDistinctUDAF extends AbstractGenericUDAFResolver
{
    private static final Logger logger = Logger.getLogger(CollectDistinctUDAF.class);
    public GenericUDAFEvaluator getEvaluator(TypeInfo[] parameters)
            throws SemanticException
    {
        logger.setLevel(Level.INFO);
        //logger.addAppender(org.apache.log4j.ConsoleAppender);
        BasicConfigurator.configure();

        if (parameters.length != 1) {
            logger.error("One argument is expected to return an Array.");
            throw new UDFArgumentTypeException(parameters.length - 1,"One argument is expected to return an Array.");
        }
        return new SetCollectUDAFEvaluator();
    }

    public static class SetCollectUDAFEvaluator extends GenericUDAFEvaluator
    {
        private ObjectInspector inputOI;
        private StandardListObjectInspector loi;
        private StandardListObjectInspector internalMergeOI;

        public ObjectInspector init(GenericUDAFEvaluator.Mode m, ObjectInspector[] parameters) throws HiveException
        {
            super.init(m, parameters);

            if (m == GenericUDAFEvaluator.Mode.PARTIAL1) {
                this.inputOI = parameters[0];
                return ObjectInspectorFactory.getStandardListObjectInspector( ObjectInspectorUtils.getStandardObjectInspector(this.inputOI));
            }
            if (!(parameters[0] instanceof StandardListObjectInspector)) {
                this.inputOI = ObjectInspectorUtils.getStandardObjectInspector(parameters[0]);
                return ObjectInspectorFactory.getStandardListObjectInspector(this.inputOI);
            }
            this.internalMergeOI = ((StandardListObjectInspector)parameters[0]);
            this.inputOI = this.internalMergeOI.getListElementObjectInspector();
            this.loi = ((StandardListObjectInspector)ObjectInspectorUtils.getStandardObjectInspector(this.internalMergeOI));
            return this.loi;
        }

        public GenericUDAFEvaluator.AggregationBuffer getNewAggregationBuffer() throws HiveException
        {
            GenericUDAFEvaluator.AggregationBuffer buff = new ArrayAggBuffer();
            reset(buff);
            return buff;
        }

        public void iterate(GenericUDAFEvaluator.AggregationBuffer agg, Object[] parameters) throws HiveException
        {
            Object p = parameters[0];
            if (p != null) {
                ArrayAggBuffer myagg = (ArrayAggBuffer)agg;
                putIntoSet(p, myagg);
            }
        }

        public void merge(GenericUDAFEvaluator.AggregationBuffer agg, Object partial) throws HiveException
        {
            ArrayAggBuffer myagg = (ArrayAggBuffer)agg;
            ArrayList partialResult = (ArrayList)this.internalMergeOI.getList(partial);
            for (Iterator localIterator = partialResult.iterator(); localIterator.hasNext(); ) {
                Object i = localIterator.next();
                putIntoSet(i, myagg);
            }
        }

        public void reset(GenericUDAFEvaluator.AggregationBuffer buff) throws HiveException
        {
            ArrayAggBuffer arrayBuff = (ArrayAggBuffer)buff;
            arrayBuff.collectArray = new HashSet();
        }

        public Object terminate(GenericUDAFEvaluator.AggregationBuffer agg) throws HiveException
        {
            ArrayAggBuffer myagg = (ArrayAggBuffer)agg;
            ArrayList ret = new ArrayList(myagg.collectArray.size());
            ret.addAll(myagg.collectArray);
            return ret;
        }

        private void putIntoSet(Object p, ArrayAggBuffer myagg)
        {
            Object pCopy = ObjectInspectorUtils.copyToStandardObject(p,this.inputOI);
            myagg.collectArray.add(pCopy);
        }

        public Object terminatePartial(GenericUDAFEvaluator.AggregationBuffer agg) throws HiveException
        {
            ArrayAggBuffer myagg = (ArrayAggBuffer)agg;
            ArrayList ret = new ArrayList(myagg.collectArray.size());
            ret.addAll(myagg.collectArray);
            return ret;
        }

        static class ArrayAggBuffer implements GenericUDAFEvaluator.AggregationBuffer
        {
            Set collectArray = new HashSet();
        }
    }
}