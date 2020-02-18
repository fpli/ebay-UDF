package com.ebay.carmel.udf.syslib;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hive.ql.exec.UDF;

import java.math.BigDecimal;

public class IsDecimal extends UDF {

    public int evaluate(String input_str, int limit) {
        if(StringUtils.isBlank(input_str)) return 0;
        input_str = input_str.trim();
        try {
            BigDecimal bigDecimal = new BigDecimal(input_str);
            if (bigDecimal.precision() > limit ) {
                return 0;
            }
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }
}
