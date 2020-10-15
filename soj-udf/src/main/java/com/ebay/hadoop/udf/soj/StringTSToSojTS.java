package com.ebay.hadoop.udf.soj;

import org.apache.hadoop.hive.ql.exec.UDF;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Date;
import java.util.TimeZone;

public class StringTSToSojTS extends UDF {


    private static DateTimeFormatter timestampFormatter =
            DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss.SSS")
                    .withZone(
                            DateTimeZone.forTimeZone(TimeZone.getTimeZone("GMT-7")));
    
    public Long evaluate(String strTS) {

        Date date = timestampFormatter.parseDateTime(strTS.substring(0, 23)).toDate();
        long ts = date.getTime();
        long sojTimestamp = (ts * 1000) + 2208963600000000L;

        return sojTimestamp;
    }
}
