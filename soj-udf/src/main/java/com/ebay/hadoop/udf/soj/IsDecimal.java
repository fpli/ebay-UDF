package com.ebay.hadoop.udf.soj;

import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.commons.lang3.math.NumberUtils;

import java.math.BigDecimal;


/**
 * Created by szang on 7/31/17.
 *
 * This UDF exams if the input String can be interpreted as a valid decimal number.

 * The source code reference: https://github.corp.ebay.com/APD/DINT-UDF/blob/master/udf/syslib/udf_IsDecimal2.c
 *
 * The initial request on JIRA ticket:
 *
 *      Hi Team,
 *         The below UDF function is not supported in Spark. Please let me know the equivalent.
 *         syslib.udf_isdecimal(encrypted_user_id)=1
 *
 * On a side note, the source code doesn't deal with the following corner case:
 *      input: "0000.1"
 */


public class IsDecimal extends UDF {

    public IntWritable evaluate( Text instr, int p, int s) {
        if (instr == null || p < 0 || s < 0) {
            return new IntWritable(0);
        }
        String num = instr.toString();
        if (!NumberUtils.isNumber(num) || num.contains("e") || num.contains("E")) {
            return new IntWritable(0);
        }
        try {
            BigDecimal decimal = new BigDecimal(num);
            int scale = decimal.scale();
            int precision = decimal.precision();
            if ((p - s) >= (precision - scale)){
                return new IntWritable(1);
            }else{
                return new IntWritable(0);
            }

        } catch (NumberFormatException e) {
            return new IntWritable(0);
        }
    }
}
