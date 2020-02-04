package com.ebay.hadoop.udf;

import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.Map;

public class CommandGenerator {


    public static void main(String[] args) throws Exception{

        Yaml yaml = new Yaml();

        List<Map<String, Object>> list = yaml.load(new FileInputStream(new File("/Users/jnwang/Documents/projects/ebay-UDF/common-udf/common.yaml")));

        for(Map<String, Object> item : list){
            String funcName = (String)item.get("name");
            String className = (String)item.get("className");
            Class.forName(className);
            System.out.println(String.format("DROP FUNCTION IF EXISTS %s;", funcName));
            System.out.println(String.format("create function %s as '%s' using jar 'viewfs://apollo-rno/apps/udf/common/common-udf-1.0-SNAPSHOT-jar-with-dependencies.jar';", funcName, className));
        }
        
    }
}
