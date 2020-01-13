package com.ebay.hadoop.udf.soj;

import com.ebay.hadoop.udf.utils.SojTimestamp;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;

public class SojStr2Date extends UDF {

    public Text evaluate( final Text sojTimestamp ) throws Exception {
        return new Text(SojTimestamp.getDateToSojTimestamp(sojTimestamp.toString()));
    }
}