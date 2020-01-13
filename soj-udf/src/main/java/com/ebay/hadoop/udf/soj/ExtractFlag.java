package com.ebay.hadoop.udf.soj;

import com.ebay.sojourner.common.sojlib.SOJExtractFlag;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;

public class ExtractFlag extends UDF {
    
    public IntWritable evaluate(Text sojFlag, int bitPos) {
        if (sojFlag == null || bitPos < 0) {
            return new IntWritable(0);
        }
        
        int pos = SOJExtractFlag.extractFlag(sojFlag.toString(), bitPos);
        return pos == 0 ? new IntWritable(0) : new IntWritable(pos);
    }
}
