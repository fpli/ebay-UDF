package com.ebay.carmel.udf.syslib;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hive.ql.exec.UDF;

import java.math.BigDecimal;

public class IsNumber extends UDF {

    public int evaluate(String input_str) {
        if(StringUtils.isBlank(input_str)) return 0;
        input_str = input_str.trim();
        try {
            if(!StringUtils.startsWith(input_str, "-") && !StringUtils.startsWith(input_str, "+")) {
                BigDecimal bigDecimal = new BigDecimal(input_str);
                return bigDecimal.scale() == 0 ? 1: 0;
            }
        } catch (Exception e) {
        }
        return 0;
    }
}
