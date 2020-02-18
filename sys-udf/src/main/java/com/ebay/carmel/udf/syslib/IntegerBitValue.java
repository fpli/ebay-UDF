package com.ebay.carmel.udf.syslib;

import org.apache.hadoop.hive.ql.exec.UDF;

public class IntegerBitValue extends UDF {

    public int evaluate(int value, int bitIdx) {
        if(bitIdx > 32 || bitIdx <=0 ) return  0;
        else if(bitIdx == 32) {
            return (value & 1) > 0 ? 1:0;
        } else {
            return (value >> (32 - bitIdx)) > 0 ? 1:0;
        }
    }
}
