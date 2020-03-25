package com.ebay.hadoop.udf.soj;

import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDFStringToMap;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;
import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.Assert.assertTrue;

public class SojStringToMapTest {

    @Test
    public void testStringToMapWithDefaultDelimiters() throws HiveException {
        SojStringToMap udf = new SojStringToMap();
        initGenericUDFWithNoDelimiters(udf);
        Map<Integer, String> expResult = new LinkedHashMap<>();
        expResult.put(1, "value1");
        expResult.put(2, "value2");
        expResult.put(3, "value3");
        runAndVerify("value1,value2,value3,", expResult, udf);
    }

    @Test
    public void testStringToMapWithCustomDelimiters() throws HiveException {
        SojStringToMap udf = new SojStringToMap();
        initGenericUDF(udf);
        Map<Integer, String> expResult = new LinkedHashMap<>();
        expResult.put(1, "value1");
        expResult.put(2, "value2");
        expResult.put(3, "value3");
        runAndVerify("value1;value2;value3", ";", expResult, udf);
    }


    @Test
    public void testStringToMapWithCustomDelimiters2() throws HiveException {
        SojStringToMap udf = new SojStringToMap();
        initGenericUDF(udf);
        Map<Integer, String> expResult = new LinkedHashMap<>();
        expResult.put(1, "value1");
        expResult.put(2, "value2");
        expResult.put(3, "value3");
        runAndVerify("value1,value2,value3,", ",", expResult, udf);
    }


    private void initGenericUDFWithNoDelimiters(SojStringToMap udf)
            throws UDFArgumentException {

        ObjectInspector valueOI0 = PrimitiveObjectInspectorFactory.javaStringObjectInspector;
        ObjectInspector[] arguments = {valueOI0};
        udf.initialize(arguments);
    }


    private void initGenericUDF(SojStringToMap udf)
            throws UDFArgumentException {

        ObjectInspector valueOI0 = PrimitiveObjectInspectorFactory.javaStringObjectInspector;
        ObjectInspector valueOI1 = PrimitiveObjectInspectorFactory.javaStringObjectInspector;
        ObjectInspector[] arguments = { valueOI0, valueOI1 };
        udf.initialize(arguments);
    }

    private void runAndVerify(String text, Map<Integer, String> expResult,
                              GenericUDF udf) throws HiveException {

        GenericUDF.DeferredObject valueObj0 = new GenericUDF.DeferredJavaObject(text);
        GenericUDF.DeferredObject[] args = {valueObj0};
        @SuppressWarnings("unchecked")
        LinkedHashMap<Object, Object> output = (LinkedHashMap<Object, Object>) udf.evaluate(args);
        assertTrue("soj_str_to_map() test", expResult.equals(output));
    }

    private void runAndVerify(String text, String delimiter, Map<Integer, String> expResult,
                              GenericUDF udf) throws HiveException {

        GenericUDF.DeferredObject valueObj0 = new GenericUDF.DeferredJavaObject(text);
        GenericUDF.DeferredObject valueObj1 = new GenericUDF.DeferredJavaObject(delimiter);
        GenericUDF.DeferredObject[] args = {valueObj0, valueObj1};
        @SuppressWarnings("unchecked")
        LinkedHashMap<Object, Object> output = (LinkedHashMap<Object, Object>) udf.evaluate(args);
        assertTrue("soj_str_to_map() test", expResult.equals(output));
    }
}
