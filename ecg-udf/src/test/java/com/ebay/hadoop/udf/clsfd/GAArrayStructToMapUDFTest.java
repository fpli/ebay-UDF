package com.ebay.hadoop.udf.clsfd;

import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF;
import org.apache.hadoop.hive.serde2.objectinspector.*;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.IntObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.JavaStringObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/*
 * Created by jianyahuang on 2019.02.26
 *
 * select customdimensions
 * ,dw_clsfd.array_struct_to_map(raw.customdimensions) as sess_cd
 * ,raw.ga_prfl_id
 * ,raw.dtfrom dw_clsfd.stg_clsfd_ga_raw_ar raw
 * limit 1;
 *
 * [{"index":20,"value":""}]	{20:""}	150399907	20200316
 *
 * * customdimensions    	array<struct<index:int,value:string>>	from deserializer
 * * out                    map<int,string>
 *
 */

public class GAArrayStructToMapUDFTest {
    @Test
    public void test() throws HiveException {

        GAArrayStructToMapUDF array_struct_to_map = new GAArrayStructToMapUDF();
        // 1. test func initialize
        List<String> structFieldNames = new ArrayList<String>();
        structFieldNames.add("index");
        structFieldNames.add("value");

        List<Object> structFieldValues = new ArrayList<Object>();
        structFieldValues.add(20);
        structFieldValues.add("abc");

        List<ObjectInspector> objectInspectors = new ArrayList<ObjectInspector>();
        objectInspectors.add(PrimitiveObjectInspectorFactory.javaIntObjectInspector);
        objectInspectors.add(PrimitiveObjectInspectorFactory.javaStringObjectInspector);
        StandardStructObjectInspector obi_struct = ObjectInspectorFactory.getStandardStructObjectInspector(structFieldNames,objectInspectors);
        StructField structField1 = obi_struct.getStructFieldRef(structFieldNames.get(0));
        StructField structField2 = obi_struct.getStructFieldRef(structFieldNames.get(1));
        System.out.println("index:"+obi_struct.getAllStructFieldRefs());
        obi_struct.setStructFieldData(structFieldValues,structField1,structFieldValues.get(0));
        obi_struct.setStructFieldData(structFieldValues,structField2,structFieldValues.get(1));
        System.out.println("value:"+obi_struct.getStructFieldData(structFieldValues,structField1)+"\t"+obi_struct.getStructFieldData(structFieldValues,structField2));
        System.out.println("value-list:"+obi_struct.getStructFieldsDataAsList(structFieldValues));
        StandardListObjectInspector obi_array = ObjectInspectorFactory.getStandardListObjectInspector(obi_struct);

        ObjectInspector[] arguments = new ObjectInspector[1];
        arguments[0] = obi_array;
        ObjectInspector obi = array_struct_to_map.initialize(arguments);

        // test type after inition
        ListObjectInspector loi = ((ListObjectInspector)arguments[0]);    //  = obi_array
        StructObjectInspector soi = ((StructObjectInspector) loi.getListElementObjectInspector());    // (StructObjectInspector) ObjectInspector  = obi_struct
        StructField structFieldName = soi.getStructFieldRef("index");
        StructField structFieldValue = soi.getStructFieldRef("value");
        //Object idxObj = soi.getStructFieldData(o, structField);
        //Object idxObj = soi.getStructFieldData(structFieldNames, structField);
        Object idxObj = soi.getStructFieldData(structFieldValues, structFieldName);
        ObjectInspector idxoi = structFieldName.getFieldObjectInspector();
        ObjectInspector valoi = structFieldValue.getFieldObjectInspector();
        //System.out.println("idxoi:"+idxoi+"\n valoi:"+valoi);
        int index = ((IntObjectInspector)idxoi).get(idxObj);
        Object valObj = soi.getStructFieldData(structFieldValues, structFieldValue);
        String value = ((JavaStringObjectInspector)valoi).getPrimitiveJavaObject(valObj);
        //this.ret.put(Integer.valueOf(index), value.replace("\n", ""));
        System.out.println("output: "+Integer.valueOf(index)+"\t "+value.replace("\n", ""));

        // 2. test func evaluate
        List<Object> args = new ArrayList<Object>(); args.add(structFieldValues);
        GenericUDF.DeferredObject[] gdo = new GenericUDF.DeferredObject[]{new GenericUDF.DeferredJavaObject(args)};
        LinkedHashMap<Object, Object> ret = (LinkedHashMap<Object, Object>) array_struct_to_map.evaluate(gdo);
        for(Map.Entry<Object, Object> entry : ret.entrySet()){
            System.out.println("key ："+entry.getKey()+" value ："+entry.getValue());
            assertEquals(entry.getKey(),obi_struct.getStructFieldData(structFieldValues,structField1));
            assertEquals(entry.getValue(),obi_struct.getStructFieldData(structFieldValues,structField2));
        }

        // 3. if err,display
        String[] children = new String[1];
        children[0]="lalala";
        String out = array_struct_to_map.getDisplayString(children);
        System.out.println(out);


    }
}
