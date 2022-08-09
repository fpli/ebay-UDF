package com.ebay.hadoop.udf.soj;

import com.ebay.hadoop.udf.tags.ETLUdf;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hive.ql.exec.UDF;

@ETLUdf(name = "soj_nvl")
public class SojTagFetcher extends UDF {

    public String evaluate(final String payload, String key) {
        if (payload == null || key == null) {
            return null;
        }

        String tagValue = null;
        try {
            tagValue = getTagValue(payload, key);
        }
        catch(Exception e)
        {
            tagValue = "-99999";
        }
        if (tagValue == null) {
            return null;
        } else {
            return tagValue;
        }
    }

    public static String getTagValue(String querystring, String key) {
        if (querystring == null || key == null || key.length() == 0 ||
                StringUtils.isBlank(querystring) || StringUtils.isBlank(key)) {
            return null;
        }

        String v = null;
        int l = key.length();
        if (querystring.startsWith(key) && querystring.length() > key.length() && querystring.charAt(l) == '=') {
            int pos = querystring.indexOf('&');
            if (pos > 0) {
                v = querystring.substring(l + 1, pos);
            } else {
                v = querystring.substring(l + 1);
            }
        }

        if (v == null) {
            if((querystring.startsWith("_" + key) || querystring.startsWith("!" + key)) && (querystring.length() > key.length() + 1) && querystring.charAt(l + 1) == '=') {
                int pos = querystring.indexOf('&');
                if (pos > 0) {
                    v = querystring.substring(l + 2, pos);
                } else {
                    v = querystring.substring(l + 2);
                }
            }
        }

        if (v == null) {
            int begin = querystring.indexOf("&" + key + "=");
            if (begin >= 0) {
                int begin2 = begin + l + 2;
                int pos = querystring.indexOf('&', begin2);
                if (pos > 0) {
                    v = querystring.substring(begin2, pos);
                } else {
                    v = querystring.substring(begin2);
                }
            }
        }

        if (v == null) {
            int begin_1 = querystring.indexOf("&_" + key + "=");
            int begin_2 = querystring.indexOf("&!" + key + "=");

            int begin = -1;
            if(begin_1 >= 0){
                begin = begin_1;
            } else if(begin_2 >= 0){
                begin = begin_2;
            }

            if (begin >= 0) {
                int begin2 = begin + l + 3;
                int pos = querystring.indexOf('&', begin2);
                if (pos > 0) {
                    v = querystring.substring(begin2, pos);
                } else {
                    v = querystring.substring(begin2);
                }
            }
        }

        if (v != null && v.length() == 0) {
            return null;
        }
        return v;
    }
}