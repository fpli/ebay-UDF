package com.ebay.hadoop.udf.common;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.ebay.hadoop.udf.tags.ETLUdf;
import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.hive.ql.udf.UDFType;

@UDFType(stateful = true)
@Description(
		name = "EpochDateToString",
		value = "_FUNC_(milisecond, date format) - conver milisecond to readable date String",
		extended = "Example:\n  > SELECT EpochDateToString(1497033129000, 'yyyy-MM-dd';\n")
@ETLUdf(name = "ms2date")
public class EpochDateToString extends UDF {
    String dateFormatted;
    
    public String evaluate(Long timestamp, String format) {
    	if (timestamp == null)
    		return null;
    	
        Date date = new Date(timestamp);
        DateFormat formatter = new SimpleDateFormat(format);
        dateFormatted = formatter.format(date);
        return dateFormatted;
    }
    
    public String evaluate(Long timestamp) {
    	return evaluate(timestamp, "yyyy-MM-dd");
    }
}