package brickhouse.udf.collect;

import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.exec.UDFArgumentTypeException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.parse.SemanticException;
import org.apache.hadoop.hive.ql.udf.generic.AbstractGenericUDAFResolver;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDAFEvaluator;
import org.apache.hadoop.hive.serde2.objectinspector.*;
import org.apache.hadoop.hive.serde2.typeinfo.TypeInfo;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

@Description(name="collect", value="_FUNC_(x) - Returns an array of all the elements in the aggregation group ")
public class CollectUDAF extends AbstractGenericUDAFResolver
{
    private static final Logger logger = Logger.getLogger(CollectUDAF.class);

    public GenericUDAFEvaluator getEvaluator(TypeInfo[] parameters) throws SemanticException
    {
        logger.setLevel(Level.INFO);
        //logger.addAppender(org.apache.log4j.ConsoleAppender);
        BasicConfigurator.configure();
        if ((parameters.length != 1) && (parameters.length != 2)) {
            logger.error("One argument is expected to return an Array, Two arguments are expected for a Map.");
            throw new UDFArgumentTypeException(parameters.length - 1,"One argument is expected to return an Array, Two arguments are expected for a Map.");
        }
        if (parameters.length == 1) {
            return new ArrayCollectUDAFEvaluator();
        }
        return new MapCollectUDAFEvaluator();
    }

    public static class ArrayCollectUDAFEvaluator extends GenericUDAFEvaluator
    {
        private ObjectInspector inputOI;
        private StandardListObjectInspector loi;
        private StandardListObjectInspector internalMergeOI;

        public ObjectInspector init(GenericUDAFEvaluator.Mode m, ObjectInspector[] parameters) throws HiveException
        {
            super.init(m, parameters);

            if (m == GenericUDAFEvaluator.Mode.PARTIAL1) {
                this.inputOI = parameters[0];
                return ObjectInspectorFactory.getStandardListObjectInspector(ObjectInspectorUtils.getStandardObjectInspector(this.inputOI));
            }
            if (!(parameters[0] instanceof StandardListObjectInspector))
            {
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
            arrayBuff.collectArray = new ArrayList();
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
            Object pCopy = ObjectInspectorUtils.copyToStandardObject(p, this.inputOI);
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
            ArrayList collectArray = new ArrayList();
        }
    }

    public static class MapCollectUDAFEvaluator extends GenericUDAFEvaluator
    {

        private PrimitiveObjectInspector inputKeyOI;
        private ObjectInspector inputValOI;
        private StandardMapObjectInspector moi;
        private StandardMapObjectInspector internalMergeOI;

        public ObjectInspector init(GenericUDAFEvaluator.Mode m, ObjectInspector[] parameters) throws HiveException
        {
            super.init(m, parameters);

            if (m == GenericUDAFEvaluator.Mode.PARTIAL1) {
                this.inputKeyOI = ((PrimitiveObjectInspector)parameters[0]);
                this.inputValOI = parameters[1];

                return ObjectInspectorFactory.getStandardMapObjectInspector(
                        ObjectInspectorUtils.getStandardObjectInspector(this.inputKeyOI),
                        ObjectInspectorUtils.getStandardObjectInspector(this.inputValOI));
            }
            if (!(parameters[0] instanceof StandardMapObjectInspector)) {
                this.inputKeyOI = ((PrimitiveObjectInspector) ObjectInspectorUtils.getStandardObjectInspector(parameters[0]));
                this.inputValOI = ObjectInspectorUtils.getStandardObjectInspector(parameters[1]);
                return ObjectInspectorFactory.getStandardMapObjectInspector(this.inputKeyOI, this.inputValOI);
            }
            this.internalMergeOI = ((StandardMapObjectInspector)parameters[0]);
            this.inputKeyOI = ((PrimitiveObjectInspector)this.internalMergeOI.getMapKeyObjectInspector());
            this.inputValOI = this.internalMergeOI.getMapValueObjectInspector();
            this.moi = ((StandardMapObjectInspector)ObjectInspectorUtils.getStandardObjectInspector(this.internalMergeOI));
            return this.moi;
        }

        public GenericUDAFEvaluator.AggregationBuffer getNewAggregationBuffer() throws HiveException
        {
            GenericUDAFEvaluator.AggregationBuffer buff = new MapAggBuffer();
            reset(buff);
            return buff;
        }

        public void iterate(GenericUDAFEvaluator.AggregationBuffer agg, Object[] parameters) throws HiveException
        {
            Object k = parameters[0];
            Object v = parameters[1];

            if (k != null) {
                MapAggBuffer myagg = (MapAggBuffer)agg;
                putIntoSet(k, v, myagg);
            }
        }

        public void merge(GenericUDAFEvaluator.AggregationBuffer agg, Object partial) throws HiveException
        {
            MapAggBuffer myagg = (MapAggBuffer)agg;
            HashMap partialResult = (HashMap)this.internalMergeOI.getMap(partial);
            for (Iterator localIterator = partialResult.keySet().iterator(); localIterator.hasNext(); ) {
                Object i = localIterator.next();
                putIntoSet(i, partialResult.get(i), myagg);
            }
        }

        public void reset(GenericUDAFEvaluator.AggregationBuffer buff) throws HiveException
        {
            MapAggBuffer arrayBuff = (MapAggBuffer)buff;
            arrayBuff.collectMap = new HashMap();
        }

        public Object terminate(GenericUDAFEvaluator.AggregationBuffer agg) throws HiveException
        {
            MapAggBuffer myagg = (MapAggBuffer)agg;
            HashMap ret = new HashMap(myagg.collectMap);
            return ret;
        }

        private void putIntoSet(Object key, Object val, MapAggBuffer myagg)
        {
            Object keyCopy = ObjectInspectorUtils.copyToStandardObject(key, this.inputKeyOI);
            Object valCopy = ObjectInspectorUtils.copyToStandardObject(val, this.inputValOI);

            myagg.collectMap.put(keyCopy, valCopy);
        }

        public Object terminatePartial(GenericUDAFEvaluator.AggregationBuffer agg) throws HiveException
        {
            MapAggBuffer myagg = (MapAggBuffer)agg;
            HashMap ret = new HashMap(myagg.collectMap);
            return ret;
        }

        static class MapAggBuffer implements GenericUDAFEvaluator.AggregationBuffer
        {
            HashMap<Object, Object> collectMap = new HashMap();
        }
    }
}