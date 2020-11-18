package com.ebay.hadoop.udf;

import org.junit.Assert;
import org.junit.Test;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class YamlVerificationTest {
    
    public void testYamls(){

        Yaml yaml = new Yaml();

        try{
            File rootPath = new File(this.getClass().getClassLoader().getResource("").getPath()).getParentFile().getParentFile().getParentFile();
            List<String> yamls = new ArrayList<>();

            for(File file: rootPath.listFiles()){
                if(file.isDirectory()){
                    for(File childModule: file.listFiles(new FileFilter() {
                        @Override
                        public boolean accept(File pathname) {
                            return pathname.getName().endsWith("yaml");
                        }
                    })){
                        yamls.add(childModule.getAbsolutePath());
                    }

                }
            }


            for(String yamlTemplate: yamls){
                Set<String> existingFuncs = new HashSet<>();
                System.out.println(yamlTemplate);
                List<Map<String, Object>> list = yaml.load(new FileInputStream(new File(yamlTemplate)));
                for(Map<String, Object> item : list){
                    String funcName = (String)item.get("name");
                    String className = (String)item.get("className");
                    Class.forName(className);
                    Assert.assertFalse(existingFuncs.contains(funcName));
                    existingFuncs.add(funcName);
                }
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            Assert.assertTrue(false);
        }

    }
}
