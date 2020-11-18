package com.ebay.hadoop.udf.hadoop;

import org.apache.hadoop.hive.ql.exec.UDF;

public class UgiExtract extends UDF {

    public String evaluate(String value) {
        if(value == null || value.trim().isEmpty()){
            return "";
        }else{
            String userName = value;
            if(value.indexOf(" via ") != -1){
                userName  =  value.substring(0, value.indexOf(" via ")).trim();
            }
            if(userName.indexOf("@") != -1)
                userName = userName.substring(0, userName.indexOf("@"));
            if(userName.indexOf("/") != -1)
                userName = userName.substring(0, userName.indexOf("/"));
            if(userName.indexOf("(") != -1)
                userName = userName.substring(0, userName.indexOf("("));

            return userName.trim();

        }
    }
}
