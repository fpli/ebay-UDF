package com.ebay.hadoop.udf;

import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.Map;

public class CommandGenerator {


    public static void main(String[] args) throws Exception{

        Yaml yaml = new Yaml();

        List<Map<String, Object>> list = yaml.load(new FileInputStream(new File("xxx/ebay-UDF/soj-udf/soj.yaml")));

        createTestFunctions(list, "soj", "soj-udf-1.15-SNAPSHOT-jar-with-dependencies.jar", "apollorno");


//        createProductionFunctions(list, "soj", "soj-udf-1.15-SNAPSHOT-jar-with-dependencies.jar");

    }

    public static void createTestFunctions( List<Map<String, Object>> list, String component, String jarFile, String clusterName) throws Exception{
        for(Map<String, Object> item : list){
            String funcName = (String)item.get("name");
            String[] temps = funcName.split("\\.");
            funcName = temps[0] + "_test." + temps[1];
            String className = (String)item.get("className");
            Class.forName(className);
//            System.out.println(String.format("DROP FUNCTION IF EXISTS %s;", funcName));
            System.out.println(String.format("create function %s as '%s' using jar '%s/%s/%s';", funcName, className, getClusterPrefix(true, clusterName), component, jarFile));
        }

        System.out.println();
        System.out.println(list.size());
    }

    public static void createProductionFunctions( List<Map<String, Object>> list , String component, String jarFile,  String clusterName) throws Exception{
        for(Map<String, Object> item : list){

            String funcName = (String)item.get("name");
            String className = (String)item.get("className");
            Class.forName(className);
            System.out.println(String.format("DROP FUNCTION IF EXISTS %s;", funcName));
            System.out.println(String.format("create function %s as '%s' using jar '%s/%s/%s';", funcName, className, getClusterPrefix(false, clusterName), component, jarFile));
        }
    }


    private static String getClusterPrefix(boolean isTest, String clusterName){
        if(clusterName.equalsIgnoreCase("herculeslvs"))
            return isTest ? "hdfs://hercules/apps/udf-test" : "hdfs://hercules/apps/udf";
        else
            return isTest ? "viewfs://apollo-rno/apps/udf-test" : "viewfs://apollo-rno/apps/udf";
    }


}
