package com.ebay.hadoop.udf.soj;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hive.ql.exec.UDF;

public class CstrStrchr extends UDF {

    public String evaluate(String str, String chr) {

        if (str == null) {
            return null;
        }

        if (StringUtils.isEmpty(chr)) {
            return null;
        }

        char ch = chr.charAt(0);

        int index = str.indexOf(ch);
        if (index < 0) {
            return null;
        } else {
            return str.substring(index);
        }
    }
}
