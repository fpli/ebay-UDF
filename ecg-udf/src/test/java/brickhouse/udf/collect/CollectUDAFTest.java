package brickhouse.udf.collect;

/**
 * Created by jianyahuang on 2019.03.16
 */

import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDAFEvaluator;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.objectinspector.StandardStructObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.typeinfo.StructTypeInfo;
import org.apache.hadoop.hive.serde2.typeinfo.TypeInfoUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class CollectUDAFTest {

    @Test
    public void test() throws HiveException {

        CollectUDAF collectUDAF = new CollectUDAF();

        List<String> structFieldNames = new ArrayList<String>();
        structFieldNames.add("field1");
        structFieldNames.add("field2");

        List<ObjectInspector> objectInspectors = new ArrayList<ObjectInspector>();
        objectInspectors.add(PrimitiveObjectInspectorFactory.javaStringObjectInspector);
        objectInspectors.add(PrimitiveObjectInspectorFactory.javaStringObjectInspector);

        // 1. test init
        StructTypeInfo structTypeInfo = (StructTypeInfo) TypeInfoUtils
                .getTypeInfoFromObjectInspector(ObjectInspectorFactory
                        .getStandardStructObjectInspector(structFieldNames,
                                objectInspectors));
        collectUDAF.getEvaluator(new StructTypeInfo[] { structTypeInfo });

        // 2. test array in
        CollectUDAF.ArrayCollectUDAFEvaluator arrayCollectUDAFEvaluator = new CollectUDAF.ArrayCollectUDAFEvaluator();
        StandardStructObjectInspector obi_struct = ObjectInspectorFactory.getStandardStructObjectInspector(structFieldNames,objectInspectors);
        arrayCollectUDAFEvaluator.init(GenericUDAFEvaluator.Mode.PARTIAL1, new ObjectInspector[] { obi_struct });


    }
}
