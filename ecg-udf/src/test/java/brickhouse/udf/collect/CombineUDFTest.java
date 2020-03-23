package brickhouse.udf.collect;

import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.objectinspector.StandardMapObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;
import org.junit.Test;

import java.util.*;

/**
 * Created by jianyahuang on 2019.03.16
 */
public class CombineUDFTest {

    @Test
    public void test() throws HiveException {
        CombineUDF combineUDF = new CombineUDF();

        ObjectInspector[] args = new ObjectInspector[2];
        ObjectInspector mapKeyObjectInspector = PrimitiveObjectInspectorFactory.javaStringObjectInspector;  //.writableStringObjectInspector;
        ObjectInspector mapValueObjectInspector = PrimitiveObjectInspectorFactory.javaStringObjectInspector;
        StandardMapObjectInspector standardMapObjectInspector = ObjectInspectorFactory.getStandardMapObjectInspector(mapKeyObjectInspector,mapValueObjectInspector);
        args[0]=standardMapObjectInspector;
        args[1]=standardMapObjectInspector;
        combineUDF.initialize(args);

        GenericUDF.DeferredObject[] arguements = new GenericUDF.DeferredObject[2];
        Map<Object,Object> map1 = new HashMap<>();
        map1.put("1111","aaaa");
        //map1.put("2222","bbbb");
        Map<Object,Object> map2 = new HashMap<>();
        map2.put("3333","cccc");
        arguements[0]=new GenericUDF.DeferredJavaObject(map1);
        arguements[1]=new GenericUDF.DeferredJavaObject(map2);
        //combineUDF.evaluate(arguements);

    }
}
