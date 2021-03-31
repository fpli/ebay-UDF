package com.ebay.hadoop.udf.core;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDTF;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.objectinspector.StructObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;

import java.util.*;

/**
 * Created by xiansheng on 2020/11/25.
 *
 */
public class JsonToKV extends GenericUDTF
{
    @Override
    public StructObjectInspector initialize(ObjectInspector[] argOIs) throws UDFArgumentException {
        List<String> fieldNames = new ArrayList<String>(2);
        List<ObjectInspector> fieldOIs = new ArrayList<ObjectInspector>(2);
        fieldNames.add("key");
        fieldNames.add("value");
        fieldOIs.add(PrimitiveObjectInspectorFactory.javaStringObjectInspector);
        fieldOIs.add(PrimitiveObjectInspectorFactory.javaStringObjectInspector);
        return ObjectInspectorFactory.getStandardStructObjectInspector(fieldNames, fieldOIs);
    }

    public ArrayList<Object[]> processInputRecord(String data)  {
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode rootNode = mapper.readTree(data);
            Iterator<String> keys = rootNode.getFieldNames();
            ArrayList<Object[]> result = new ArrayList<Object[]>();
            while(keys.hasNext()){
                String key = keys.next();
                JsonNode valueNode = rootNode.path(key);
                result.add(new Object[] {key, valueNode.getTextValue()});
            }
            ArrayList<Object[]> results =result;
            return results;
        } catch (Exception e) {
            ArrayList<Object[]> result = new ArrayList<Object[]>();
            result.add(new Object[] {"json error", "json error"});
            ArrayList<Object[]> results =result;
            return results;
        }
    }

    @Override
    public  void process(Object[] objects) throws HiveException {
        String data =null;
        if (objects != null & objects.length>0){
            data = String.valueOf(objects[0]);
        }
        ArrayList<Object[]> results=processInputRecord(data);
        Iterator<Object[]> it = results.iterator();
        while (it.hasNext()) {
            Object[] r = it.next();
            forward(r);
        }
    }

    @Override
    public void close() throws HiveException {

    }
}
