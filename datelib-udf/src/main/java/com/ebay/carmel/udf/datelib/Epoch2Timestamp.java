package com.ebay.carmel.udf.datelib;

import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Epoch2Timestamp extends UDF {

    private static final String RESULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
    private static SimpleDateFormat result_formatter = new SimpleDateFormat(RESULT_DATE_FORMAT);

    static {
        result_formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
    }

    public Text evaluate(Long time) {
        Date d = new Date(time * 1000);
        return new Text(result_formatter.format(d));
    }
}
