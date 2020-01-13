package com.ebay.hadoop.udf.soj;

import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;

public class IsValidIPv4 extends UDF {
    public IntWritable evaluate(Text instr) {
        if (instr == null) {
            return new IntWritable(0);
        }
        
        boolean isValid = com.ebay.sojourner.common.sojlib.IsValidIPv4.isValidIP(instr.toString());
        
        return isValid ? new IntWritable(1) : new IntWritable(0);
    }
}
