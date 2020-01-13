package com.ebay.hadoop.udf.soj;

import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

@Description(
		name = "SojTimestampToDate",
		value = "_FUNC_(soj_timestamp) - Convert Sojourner timestamp to yyyy/MM/dd HH:mm:ss")
public final class SojTimestampToDateWithMilliSecond extends UDF {

	private static final long OFFSET = ((long) 25567)*24*3600*1000;
    private static final String DEFAULT_DATE_FORMAT = "yyyy/MM/dd HH:mm:ss.SSS";
	private static final String UTC = "UTC";

	public Text evaluate(final LongWritable sojTimestamp) throws Exception {
		if (sojTimestamp == null || sojTimestamp.get() == 0L) {
			return null;
		}
	
		long timestamp = (sojTimestamp.get()/1000);
        Date date = new Date(timestamp - OFFSET);

		// Use UTC timezone
		SimpleDateFormat formatter = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
		formatter.setTimeZone(TimeZone.getTimeZone(UTC));

        return new Text(formatter.format(date));
	}
}
