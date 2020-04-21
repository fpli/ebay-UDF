package com.ebay.hadoop.udf.soj;

import com.ebay.sojourner.common.sojlib.GUID2Date;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class GUID2DateHive extends UDF {

  private static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
//  private static SimpleDateFormat formatter = new SimpleDateFormat(DEFAULT_DATE_FORMAT);

//  private static ThreadLocal<SimpleDateFormat> simpleDateFormatThreadLocal = new ThreadLocal<SimpleDateFormat>() {
//    @Override
//    protected SimpleDateFormat initialValue() {
//      SimpleDateFormat dateFormat = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
//      // Make consistent with getCalender()
//      dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
//      return dateFormat;
//    }
//  };
  private DateTimeFormatter formatter = DateTimeFormat.forPattern(DEFAULT_DATE_FORMAT).withZone(
      DateTimeZone.forTimeZone(TimeZone.getTimeZone("GMT")));

  public Text evaluate(Text guid) {

    if (guid == null) {
      //  return new Text(formatter.format(GUID2Date.getDate(null)));
      return null;
    }

    String guids = guid.toString();
    Date newdate = null;
    try {
      newdate = GUID2Date.getDate(guids);
    } catch (Exception e) {

    }
    if (newdate != null) {
      //      return new Text(simpleDateFormatThreadLocal.get().format(newdate));
      return new Text(formatter.print(newdate.getTime()));
    } else {
      return null;
    }
  }
}
