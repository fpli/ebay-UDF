package com.ebay.hadoop.udf.common;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.hive.ql.udf.UDFType;

@UDFType(stateful = true)
@Description(
		name = "StringToEpochTimestamp",
		value = "_FUNC_(datetime Str, datetime format) - conver readable datetime String to milisecond",
		extended = "Example:\n  > SELECT StringToEpochDate('2017-01-01 00:00:00.000', 'yyyy-MM-dd HH:mm:ss.SSS';\n")
public class StringToEpochTimestamp extends UDF {
	long timestamp;
    public Long evaluate(String dateFormatted) throws ParseException {
        return evaluate(dateFormatted, "yyyy-MM-dd HH:mm:ss.SSS");
    }
    
    public Long evaluate(String dateFormatted, String format) throws ParseException {
    	if (StringUtils.isBlank(dateFormatted))
    		return null;
        DateFormat formatter = new SimpleDateFormat(format);
        timestamp = formatter.parse(dateFormatted).getTime();
        return timestamp;
    }
}