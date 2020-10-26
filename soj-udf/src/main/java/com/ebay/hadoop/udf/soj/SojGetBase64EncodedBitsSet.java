package com.ebay.hadoop.udf.soj;

import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;

/**
 * Created by qingxu1 on 2017/7/5.
 */
public class SojGetBase64EncodedBitsSet extends UDF {

    public Text evaluate(String sojFlag) {
        if (sojFlag == null) {
            return null;
        }

        String sojValue = SojGetBase64EncodedBitsSets.getBase64EncodedBitsSetValue(sojFlag.toString());
        if(sojValue == null){
            return null;
        }
        else{
            return(new Text(sojValue));
        }

    }
}