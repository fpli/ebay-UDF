package com.ebay.carmel.udf.datelib;

import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.apache.log4j.Logger;

public class WeekStartDate extends UDF {

    private static final String RESULT_DATE_FORMAT = "yyyy-MM-dd";


    public Text evaluate( Text date ) {
        if (null == date) return null;
        String strDate = date.toString();
        Calendar calendar = Calendar.getInstance();
        Date result = null;
        SimpleDateFormat[] formatters = Arrays.asList(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"),
                new SimpleDateFormat("yyyy-MM-dd"),
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
        ).toArray(new SimpleDateFormat[0]);
        SimpleDateFormat result_formatter = new SimpleDateFormat(RESULT_DATE_FORMAT);
        // if secs input
        try {
            Long time = Long.parseLong(strDate);
            Date d = new Date(time * 1000 * 1000);
            calendar.setTime(d);
            calendar.set(Calendar.DAY_OF_WEEK, 1);
            result = calendar.getTime();
            return new Text(result_formatter.format(result));
        } catch (Exception ignore){}
        // if time str input
        for (SimpleDateFormat formatter : formatters) {
            try {
                Date d = formatter.parse(strDate);
                calendar.setTime(d);
                calendar.set(Calendar.DAY_OF_WEEK, 1);
                result = calendar.getTime();
                return new Text(result_formatter.format(result));
            } catch (Exception e) {}
        }


        return null;
    }
}
