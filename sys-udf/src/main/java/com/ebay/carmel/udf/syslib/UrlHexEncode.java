package com.ebay.carmel.udf.syslib;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hive.ql.exec.UDF;

public class UrlHexEncode extends UDF {

    public String evaluate(String urlStr) {
        if (StringUtils.isNotBlank(urlStr)) {
            char[] urlChars = urlStr.toCharArray();
            char[] result = new char[Integer.MAX_VALUE/100];
            int idx = 0;
            for(int i = 0 ; i < urlChars.length ; i++) {
                char url = urlChars[i];
                if ((url <= 57 && url >= 48) ||(url <= 90 && url>= 65) || (url <=122 && url>=97)
                        || url =='-' || url=='_' || url=='.' || url=='~'  || url=='!' || url=='*' || url==39 || url=='(' || url==')'
                        /* reserved */
                        || url ==';' || url== '/' || url== '?' || url== ':' || url== '@' || url== '&' || url== '=' || url== '+' || url== '$' || url== ','
                ) {
                    result[idx ++] = url;
                } else if (url == ' '){
                    result[idx ++] = '+';
                } else {
                    result[idx ++] = '%';
                    result[idx ++] = to_hex_code((int)url >> 4);
                    result[idx ++] = to_hex_code((int)url & 15);
                }
            }
           char[] transResult = new char[idx];
           System.arraycopy(result,0, transResult,0, idx);
           return new String(transResult);
        }
        return null;
    }

    private char to_hex_code(int code) {
        char hex[] = "0123456789abcdef".toCharArray();
        return hex[code & 15];
    }
}
