package com.ebay.hadoop.udf.soj;

import org.apache.hadoop.hive.ql.exec.UDF;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Date;
import java.util.TimeZone;

public class StringDateToSojTS extends UDF {

    private static DateTimeFormatter dateTimeFormatter =
            DateTimeFormat.forPattern("yyyy-MM-dd")
                    .withZone(
                            DateTimeZone.forTimeZone(TimeZone.getTimeZone("GMT-7")));
    
    public Long evaluate(String strDate) {

        Date date = dateTimeFormatter.parseDateTime(strDate.substring(0, 10)).toDate();
        long ts = date.getTime();
        long sojTimestamp = (ts * 1000) + 2208963600000000L;

        return sojTimestamp;
    }
}
