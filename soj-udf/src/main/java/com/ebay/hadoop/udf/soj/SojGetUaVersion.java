package com.ebay.hadoop.udf.soj;

import org.apache.hadoop.hive.ql.exec.UDF;

public class SojGetUaVersion extends UDF {

    public String evaluate(String userAgent, int startPos) {
        if (startPos > userAgent.length()) {
            return " ";
        } else if (userAgent.charAt(startPos - 1) == ';' || userAgent.charAt(startPos - 1) == ')') {
            return " ";
        } else if (StrBetweenList.getStrBetweenList(userAgent.substring(startPos - 1), " /", ";/)([ -+,") == null) {
            return " ";
        } else {
            return StrBetweenList.getStrBetweenList(userAgent.substring(startPos - 1), " /", ";/)([ -+,");
        }
    }
}
