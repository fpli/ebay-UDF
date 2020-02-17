package com.ebay.carmel.udf.syslib;

import org.apache.hadoop.hive.ql.exec.UDF;

public class EbayReplaceChar extends UDF {

    public String evaluate(String str, String charList, String replaceChar) {
        // Checking the Given parameters are NULL or not
        if (str == null || charList == null || replaceChar == null) {
            return null;
        } else if (str.equals("") || charList.equals("")) {
            return "";
        } else {
            String firstChar = replaceChar.length() > 1 ?
                    replaceChar.substring(0,1) :
                    replaceChar;
            return str.replaceAll("[" + charList + "]", firstChar);
        }

    }
}
