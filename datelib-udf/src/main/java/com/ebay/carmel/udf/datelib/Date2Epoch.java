package com.ebay.carmel.udf.datelib;

import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Date2Epoch extends UDF {

    private static final String RESULT_DATE_FORMAT = "yyyy-MM-dd";
    public Long evaluate(Text date) throws ParseException {
        if (null == date) return null;
        String strDate = date.toString();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(RESULT_DATE_FORMAT);
        Date d = simpleDateFormat.parse(strDate);
        return d.getTime();
    }
}
