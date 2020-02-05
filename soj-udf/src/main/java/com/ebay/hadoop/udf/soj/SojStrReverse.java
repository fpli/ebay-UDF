package com.ebay.hadoop.udf.soj;

import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.hive.ql.exec.UDF;

public class SojStrReverse extends UDF {

    public String evaluate( String value ) {
        if (value == null || value.length() == 0) {
            return null;
        }
        return StringUtils.reverse(value);
    }
}
