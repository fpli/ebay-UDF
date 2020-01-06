package com.ebay.hadoop.udf.common;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.hive.ql.udf.UDFType;

@UDFType(stateful = true)
@Description(
		name = "EpochTimestampToString",
		value = "_FUNC_(milisecond, datetime format) - conver milisecond to readable datetime String",
		extended = "Example:\n  > SELECT EpochTimestampToString(1497033129001, 'yyyy-MM-dd HH:mm:ss.SSS';\n")
public class EpochTimestampToString extends UDF {
    String dateFormatted;
    
    public String evaluate(Long timestamp) {
    	return evaluate(timestamp, "yyyy-MM-dd HH:mm:ss.SSS");
    }
    
    public String evaluate(Long timestamp, String format) {
    	if (timestamp == null)
    		return null;
    	
        Date date = new Date(timestamp);
        DateFormat formatter = new SimpleDateFormat(format);
        dateFormatted = formatter.format(date);
        return dateFormatted;
    }
}