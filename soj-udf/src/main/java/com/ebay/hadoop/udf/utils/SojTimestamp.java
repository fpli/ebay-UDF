package com.ebay.hadoop.udf.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;


public class SojTimestamp {
    public static final long OFFSET = 2208963600000000L;
    public static final int MILLI2MICRO = 1000;

    public static String getSojTimestamp(String s) throws ParseException {
        String res;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT-7"));
        Date date = sdf.parse(s.substring(0,23));
        long ts = date.getTime();
        long sojTimestamp = (ts * MILLI2MICRO) + OFFSET;
        res = String.valueOf(sojTimestamp);
        return res;
    }

    public static String getDateToSojTimestamp(String s) throws ParseException {
        String res;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT-7"));
        Date date = sdf.parse(s.substring(0,10));
        long ts = date.getTime();
        long sojTimestamp = (ts * MILLI2MICRO) + OFFSET;
        res = String.valueOf(sojTimestamp);
        return res;
    }
}