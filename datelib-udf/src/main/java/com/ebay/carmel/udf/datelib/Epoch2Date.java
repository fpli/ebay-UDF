package com.ebay.carmel.udf.datelib;

import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Epoch2Date extends UDF {

    private static final String RESULT_DATE_FORMAT = "yyyy-MM-dd";

    public Text evaluate(long secs ){
        SimpleDateFormat result_formatter = new SimpleDateFormat(RESULT_DATE_FORMAT);
        Date d = new Date(secs * 1000);
        return new Text(result_formatter.format(d));
    }
}
