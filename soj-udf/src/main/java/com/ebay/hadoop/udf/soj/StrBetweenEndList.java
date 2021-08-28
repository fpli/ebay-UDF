package com.ebay.hadoop.udf.soj;

import com.ebay.hadoop.udf.tags.ETLUdf;
import jodd.util.StringUtil;
import org.apache.hadoop.hive.ql.exec.UDF;

@ETLUdf(name = "soj_str_between_endlist")
public class StrBetweenEndList extends UDF {
    public String evaluate( String url, String start, String end ) {
        if (StringUtil.isBlank(url))
            return null;
        int startPos;
        int endPos;

        if (!StringUtil.isBlank(start)) {
            startPos = url.indexOf(start);
            if (startPos < 0) {
                return null;
            } else {
                startPos += start.length();
                if (startPos == url.length())
                    return null;
            }
        } else {
            startPos = 0;
        }

        if (StringUtil.isBlank(end))
            return url.substring(startPos);

        endPos = url.length();
        int len = end.length();
        for (int i = 0; i < len; ++i) {
            char c = end.charAt(i);
            int l = url.indexOf(c, startPos);
            if (l != -1 && l < endPos) {
                endPos = l;
            }
        }

        return (startPos != 0 || endPos != 0) && endPos > startPos ? url.substring(startPos, endPos) : null;

    }
}
