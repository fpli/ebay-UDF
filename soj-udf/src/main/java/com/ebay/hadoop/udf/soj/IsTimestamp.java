package com.ebay.hadoop.udf.soj;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.IntWritable;

public class IsTimestamp extends UDF {

  private static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

  private static ThreadLocal<SimpleDateFormat> simpleDateFormatThreadLocal = new ThreadLocal<SimpleDateFormat>() {
    @Override
    protected SimpleDateFormat initialValue() {
      SimpleDateFormat dateFormat = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
      // Make consistent with getCalender()
      dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
      return dateFormat;
    }
  };

  public IntWritable evaluate(String instr, int p) {

    if (StringUtils.isEmpty(instr) || p < 0 || p > 6) {
      return new IntWritable(0);
    }

    int len = instr.length();

    if (p == 0 && len != 19) {
      return new IntWritable(0);
    } else if (p > 0 && (len < 19 || len > 20 + p)) {
      return new IntWritable(0);
    }

    String part = null;
    long msec = 0l;
    if (len > 19) {
      part = instr.substring(0, 20);
      msec = Long.parseLong(instr.substring(21));
    } else {
      part = instr;
    }

    try {
      simpleDateFormatThreadLocal.get().parse(part);
    } catch (ParseException e) {
      return new IntWritable(0);
    }

    if (len > 19 && p > 0) {
      if (msec < 0 || msec > Math.pow(10, p) - 1) {
        new IntWritable(0);
      }
    }

    return new IntWritable(1);
  }
}
