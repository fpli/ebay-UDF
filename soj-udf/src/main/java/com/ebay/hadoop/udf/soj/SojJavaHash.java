package com.ebay.hadoop.udf.soj;

import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.IntWritable;

/**
 * Created by qingxu1 on 2017/7/10.
 */
public class SojJavaHash extends UDF {

    public IntWritable evaluate(String guid, String constant, String experiment_id, int mod_value){

        if(guid == null){
            return new IntWritable(0);
        }

        if(String.valueOf(mod_value) == "null"){
            return new IntWritable(0);
        }

        return new IntWritable(SojMd5Hash.sojJavaHash(guid,constant,experiment_id,mod_value));

    }
}
