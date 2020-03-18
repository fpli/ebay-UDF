package com.ebay.carmel.udf.datelib;

import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Epochms2Timestamp extends UDF {
    private static final String RESULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
    private static SimpleDateFormat result_formatter = new SimpleDateFormat(RESULT_DATE_FORMAT);

    static {
        result_formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
    }
    public Text evaluate(long millis ) {
        try {
            Date d = new Date(millis);
            return new Text(result_formatter.format(d));
        } catch (Exception e) {

        }
        return null;
    }
}
