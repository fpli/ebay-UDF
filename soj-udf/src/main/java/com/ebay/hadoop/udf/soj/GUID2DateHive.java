package com.ebay.hadoop.udf.soj;

import com.ebay.sojourner.common.sojlib.GUID2Date;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class GUID2DateHive extends UDF {

    private static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
    private static SimpleDateFormat formatter = new SimpleDateFormat(DEFAULT_DATE_FORMAT);

    static {
        formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
    }

    public Text evaluate( Text guid ) {

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
            return new Text(formatter.format(newdate));
        } else {
            return null;
        }
    }
}
