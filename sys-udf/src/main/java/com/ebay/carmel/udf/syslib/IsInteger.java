package com.ebay.carmel.udf.syslib;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hive.ql.exec.UDF;

public class IsInteger extends UDF {

    public int evaluate(String input_str) {
        try {
            if(StringUtils.isNotBlank(input_str)) {
                input_str = input_str.trim();
            }
            if(StringUtils.endsWith(input_str, ".")) {
                input_str = input_str.substring(0, input_str.length() - 1);
            }
            Integer.parseInt(input_str);
            return 1;
        } catch (Exception e) {
        }
        return 0;
    }
}
