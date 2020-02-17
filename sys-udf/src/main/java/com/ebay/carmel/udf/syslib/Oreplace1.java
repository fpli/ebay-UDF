package com.ebay.carmel.udf.syslib;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hive.ql.exec.UDF;

public class Oreplace1 extends UDF {
    public String evaluate(String input_str, String search_str) {
        return StringUtils.replace(input_str, search_str, "");
    }
}
