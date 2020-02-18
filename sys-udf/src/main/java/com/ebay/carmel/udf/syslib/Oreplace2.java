package com.ebay.carmel.udf.syslib;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hive.ql.exec.UDF;

public class Oreplace2 extends UDF {

    public String evaluate(String input_str, String search_str, String replace_str) {
        if(null == replace_str) replace_str = "";
        return StringUtils.replace(input_str, search_str, replace_str);
    }
}
