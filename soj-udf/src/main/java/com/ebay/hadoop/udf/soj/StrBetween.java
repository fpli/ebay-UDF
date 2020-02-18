package com.ebay.hadoop.udf.soj;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hive.ql.exec.UDF;

public class StrBetween extends UDF {

    public String evaluate(String str, char start, char end) {
        if (StringUtils.isEmpty(str))
            return null;

        int strLen = str.length();
        int found_start = 0;


        int startIndex = -1;
        int endIndex = -1;
        for (int i = 0; i < strLen; i++) {
            if (str.charAt(i) == start && found_start == 0) {
                found_start = 1;
                startIndex = i + 1;
            } else if (str.charAt(i) == end && found_start == 1) {
                endIndex = i;
                break;
            }
        }

        if (startIndex == -1 || startIndex == strLen) {
            return null;
        }

        if (endIndex == -1) {
            return str.substring(startIndex);
        }

        if (startIndex == endIndex) {
            return null;
        }

        return str.substring(startIndex, endIndex);
    }
}
