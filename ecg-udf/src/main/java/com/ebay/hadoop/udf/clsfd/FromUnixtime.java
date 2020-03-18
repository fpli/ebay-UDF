package com.ebay.hadoop.udf.clsfd;

import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.log4j.Logger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class FromUnixtime extends UDF
{
    private static final Logger logger = Logger.getLogger(FromUnixtime.class);
    private static final DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public String evaluate(String timestamp, String timezone) {
        if ((timestamp == null) || (timestamp.equals(""))) {
            return null;
        }
        if ((timezone == null) || (timezone.equals("")))
        {
            timezone = "GMT";
        }
        Date dt = new Date(Long.parseLong(timestamp));
        sdf.setTimeZone(TimeZone.getTimeZone(timezone));
        String tm = sdf.format(dt);
        return tm;
    }

}