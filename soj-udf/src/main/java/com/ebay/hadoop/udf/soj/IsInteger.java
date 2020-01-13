package com.ebay.hadoop.udf.soj;

import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;

public class IsInteger extends UDF {

    @SuppressWarnings("unused")
    public IntWritable evaluate( Text inst) {
        if (inst == null) {
            return new IntWritable(0);
        }
        
        try {
            int value = Integer.valueOf(inst.toString());
            return new IntWritable(1);
        } catch (Exception e) {

            if (inst.getLength() == 0) {
                return new IntWritable(1);
            } else {
                return new IntWritable(0);
            }
            //return new IntWritable(0);
        }
    }
}
