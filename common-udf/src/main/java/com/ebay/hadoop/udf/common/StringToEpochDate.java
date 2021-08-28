package com.ebay.hadoop.udf.common;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.ebay.hadoop.udf.tags.ETLUdf;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.hive.ql.udf.UDFType;

@UDFType(stateful = true)
@Description(
		name = "StringToEpochDate",
		value = "_FUNC_(datetime Str, datetime format) - conver readable datetime String to milisecond",
		extended = "Example:\n  > SELECT StringToEpochDate('2017-01-01', 'yyyy-MM-dd';\n")
@ETLUdf(name = "date2ms")
public class StringToEpochDate extends UDF {
	long timestamp;
	
    public Long evaluate(String dateFormatted, String format) throws ParseException {
    	if (StringUtils.isBlank(dateFormatted))
    		return null;
    	
        DateFormat formatter = new SimpleDateFormat(format);
        timestamp = formatter.parse(dateFormatted).getTime();
        return timestamp;
    }
    
    public Long evaluate(String dateFormatted) throws ParseException {
        return evaluate(dateFormatted, "yyyy-MM-dd");
    }
}