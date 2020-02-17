package com.ebay.carmel.udf.syslib;

import org.apache.hadoop.hive.ql.exec.UDF;

public class D8IdObscure extends UDF {

    public long evaluate(long value) {
        long LowComplement = 238675309;
        long HighComplement = 10;
        long lowValue = value & 0xffffffffl;
        int highValue = (int)((value >> 32) & 0xffffffffl);
        lowValue = (lowValue ^ LowComplement) & 0xffffffffl ;
        highValue = (int)(highValue ^ HighComplement);
        long h = (long)highValue;
        long result = h << 32 | lowValue;
        return result;
    }
}
