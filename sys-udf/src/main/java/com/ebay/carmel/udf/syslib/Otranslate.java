package com.ebay.carmel.udf.syslib;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hive.ql.exec.UDF;

public class Otranslate extends UDF {

    public String evaluate(String input_str, String from, String to) {
        if(StringUtils.isBlank(input_str) || StringUtils.isBlank(from) || StringUtils.isBlank(to)) return null;
        char[] inputChars = input_str.toCharArray();
        char[] toChars = to.toCharArray();
        char[] resultChars = new char[inputChars.length];
        int rIdx = 0;
        for(int i = 0 ; i < inputChars.length ; i ++) {
            int idx = from.indexOf(inputChars[i]);
            if (idx == -1) {
                resultChars[rIdx++] = inputChars[i];
            } else if (idx < toChars.length) {
                resultChars[rIdx++] = toChars[idx];
            }
        }
        return new String(resultChars, 0, rIdx);
    }
}
