package com.ebay.carmel.udf.syslib;

import org.apache.hadoop.hive.ql.exec.UDF;

public class DecimalBitValue extends UDF {

    public int evaluate(long value, int bitIdx) {
        if (bitIdx > 64 || bitIdx <= 0) return 0;
        else if (bitIdx == 64) {
            return (value & 1) > 0 ? 1:0;
        } else {
            return ((value >> (64 - bitIdx)) & 1) > 0 ? 1:0;
        }
    }
}
