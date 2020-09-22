package com.ebay.hadoop.udf.shpmt;

import org.apache.hadoop.hive.ql.exec.UDF;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class GetUrl extends UDF {
    public String evaluate(String msg) {

        if (msg == null) {
            return null;
        }

        Pattern pattern = Pattern.compile("[a-zA-z0-9@\\'?\\.$:&%_/\\/\\-\\?]+\\.com\\s|http //[a-zA-z0-9,@\\'?\\.$&=%_/\\/-]+|https //[a-zA-z0-9,@\\'?\\.$&=%_/\\/-]+|www[a-zA-z0-9,@\\'?\\.$%_/\\/]+|http://[a-zA-z0-9,@\\'?\\.$&=%_/\\/-]+|https://[a-zA-z0-9,@\\'?\\.$&=%_/\\/-]+", Pattern.CASE_INSENSITIVE);
        Set<String> stringSet = new HashSet<String>();
        Matcher patternMatcher = pattern.matcher(msg);
        while (patternMatcher.find()) {
            stringSet.add(patternMatcher.group().trim());
        }

        return String.join(",", stringSet);

    }
}


