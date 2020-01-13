package com.ebay.hadoop.udf.soj;

import org.apache.hadoop.hive.ql.exec.UDF;

public class GetUrlParams extends UDF {
    public String evaluate(String url) {
        if (url == null) {
            return null;
        }

        //check :
        int pos = url.indexOf(":");
        if (pos < 0)
            return null;

        //verify // after :
        if (url.length() < pos + 3
                || !"//".equals(url.substring(pos + 1, pos + 3)))
            return null;
        else
            pos += 2;

        //verify ?
        pos = url.indexOf("?", pos + 1);
        if (pos < 0 || pos == url.length())
            return null;
        else
            return url.substring(pos + 1);
    }
}