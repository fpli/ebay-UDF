package com.ebay.carmel.udf.datelib;

import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Timestamp2Epochms extends UDF {

    private static final String RESULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";


    public Long evaluate(Text date) throws ParseException {
        if(null == date) return null;
        SimpleDateFormat result_formatter = new SimpleDateFormat(RESULT_DATE_FORMAT);
        String strDate = date.toString();
        Date d = result_formatter.parse(strDate);
        return  d.getTime();
    }
}
