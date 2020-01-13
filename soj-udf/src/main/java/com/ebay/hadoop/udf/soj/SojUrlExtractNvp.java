package com.ebay.hadoop.udf.soj;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hive.ql.exec.UDF;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SojUrlExtractNvp extends UDF {
    private SojExtractNVP nvpExtract;

    public SojUrlExtractNvp() {
        super();
        nvpExtract = new SojExtractNVP();
    }

    public String evaluate(String urlStr, String namePair, int format) {
        if (StringUtils.isBlank(urlStr) || StringUtils.isBlank(namePair))
            return null;


        if (format == 0)
            return nvpExtract.evaluate(urlStr, namePair, "&", "=").toString();
        else if (format == 1) {
            String patternStr = String.format("((QQ%sZ)|(&%s=))((.*?)(QQ|&|$))", namePair, namePair);
            Pattern pattern = Pattern.compile(patternStr);
            Matcher m = pattern.matcher(urlStr);
            if (m.find())
                return m.group(5);
            else
                return null;
        } else if (format == 2) {
            String key = namePair.startsWith("_") ? namePair.substring(1): namePair;

            String patternStr = String.format("(QQ?%sZ)((.*?)(QQ|$))", key, key);
            Pattern pattern = Pattern.compile(patternStr);
            Matcher m = pattern.matcher(urlStr);
            if (m.find())
                return m.group(3);
            else
                return null;
        } else
            return null;
    }
}
