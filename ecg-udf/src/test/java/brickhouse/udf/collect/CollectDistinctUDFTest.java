package brickhouse.udf.collect;

/*
 *  official test class detail see link:
 *  https://github.com/klout/brickhouse/blob/master/src/test/java/brickhouse/udf/collect/CollectDistinctUDFTest.java
 */

import brickhouse.udf.collect.CollectDistinctUDAF.SetCollectUDAFEvaluator;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.parse.SemanticException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDAFEvaluator.Mode;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.objectinspector.StructObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.typeinfo.StructTypeInfo;
import org.apache.hadoop.hive.serde2.typeinfo.TypeInfoUtils;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class CollectDistinctUDFTest {

    private CollectDistinctUDAF udf;

    @Before
    public void before() {
        udf = new CollectDistinctUDAF();
    }

    @Test
    public void testInitializeWithOneArguments() throws SemanticException {
        List<String> structFieldNames = new ArrayList<String>();
        structFieldNames.add("field1");
        structFieldNames.add("field2");

        List<ObjectInspector> objectInspectors = new ArrayList<ObjectInspector>();
        objectInspectors
                .add(PrimitiveObjectInspectorFactory.javaStringObjectInspector);
        objectInspectors
                .add(PrimitiveObjectInspectorFactory.javaStringObjectInspector);

        StructTypeInfo structTypeInfo = (StructTypeInfo) TypeInfoUtils
                .getTypeInfoFromObjectInspector(ObjectInspectorFactory
                        .getStandardStructObjectInspector(structFieldNames,
                                objectInspectors));
        udf.getEvaluator(new StructTypeInfo[] { structTypeInfo });
    }

    @Test
    public void testInit() throws HiveException {

        SetCollectUDAFEvaluator maxEval = new SetCollectUDAFEvaluator();

        List<String> fieldNames = new ArrayList<String>();
        fieldNames.add("field1");
        fieldNames.add("field2");

        List<ObjectInspector> objectInspectors = new ArrayList<ObjectInspector>();
        objectInspectors
                .add(PrimitiveObjectInspectorFactory.javaStringObjectInspector);
        objectInspectors
                .add(PrimitiveObjectInspectorFactory.javaStringObjectInspector);
        StructObjectInspector keyOI = ObjectInspectorFactory
                .getStandardStructObjectInspector(fieldNames, objectInspectors);
        // WritableIntObjectInspector valOI = (WritableIntObjectInspector)
        // PrimitiveObjectInspectorFactory.getPrimitiveWritableObjectInspector(PrimitiveCategory.INT);

        maxEval.init(Mode.PARTIAL1, new ObjectInspector[] { keyOI });
    }
}