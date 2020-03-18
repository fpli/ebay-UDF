package com.ebay.carmel.udf.datelib;

import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;
import org.apache.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class WeekEndDate extends UDF {

    private static final Logger logger = Logger.getLogger(WeekEndDate.class);
    private static final String RESULT_DATE_FORMAT = "yyyy-MM-dd";
    private static SimpleDateFormat[] formatters = Arrays.asList(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"),
            new SimpleDateFormat("yyyy-MM-dd"),
            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")).
            toArray(new SimpleDateFormat[0]);
    private static SimpleDateFormat result_formatter = new SimpleDateFormat(RESULT_DATE_FORMAT);

//    static {
//        for(SimpleDateFormat f : formatters) {
//            f.setTimeZone(TimeZone.getTimeZone("GMT"));
//        }
//        result_formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
//    }
    public Text evaluate(Text date) {
        if (null == date) return null;
        String strDate = date.toString();
        Date result = null;
        Calendar calendar = Calendar.getInstance();
        // secs input
        try {
            Long time = Long.parseLong(strDate);
            Date d = new Date(time * 1000 * 1000);
            calendar.setTime(d);
            calendar.set(Calendar.DAY_OF_WEEK, 7);
            result = calendar.getTime();
            return new Text(result_formatter.format(result));
        } catch (Exception ignore){}
        // time str input
        for (SimpleDateFormat formatter : formatters) {
            try {
                Date d = formatter.parse(strDate);
                calendar.setTime(d);
                logger.info("str time "+ strDate + " and parsed time " + calendar.getTime().getTime());
                calendar.set(Calendar.DAY_OF_WEEK, 7);
                logger.info("formatted time " + calendar.getTime().getTime());
                result = calendar.getTime();
                return new Text(result_formatter.format(result));
            } catch (Exception e){

            }

        }
        return null;
    }
}
