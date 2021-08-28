package com.ebay.hadoop.udf.common;

import com.ebay.hadoop.udf.common.utils.StrUtils;
import com.ebay.hadoop.udf.tags.ETLUdf;
import org.apache.hadoop.hive.ql.exec.UDF;

/**
 * Created by szang on 7/24/17.
 *
 * Please refer to com.ebay.dss.zeta.hive.LastnameOf for documentation details about this Class.
 */
@ETLUdf(name = "firstnameOf")
public class FirstnameOf extends UDF {

    public String evaluate(String name) {
        return evaluate(name, "\\s+");
    }

    public String evaluate(String name, String del) {

        if (name == null || del == null) return null;

        String temp = StrUtils.trim(name);
        if (temp.equals("")) return "";
        
        char backspace = '\u0008';
        char space = ' ';

        //Only trim space instead of all whitespace chars
        String[] split = temp.split(del);

        return StrUtils.trim(split[0].replace(backspace, space));
    }
}