package com.ebay.carmel.udf.datelib;

import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Date2Epoch extends UDF {

    public Long evaluate(Text date) throws ParseException {
        if (null == date) return null;
        String strDate = date.toString();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date d = simpleDateFormat.parse(strDate);
        return d.getTime();
    }
}
