package com.ebay.hadoop.udf.common;

import com.ebay.hadoop.udf.tags.ETLUdf;
import org.apache.hadoop.hive.ql.exec.UDF;

/**
 * Created by yunjzhang on 7/28/17.
 *
 * Please refer to com.ebay.dss.zeta.hive.LastnameOf for documentation details about this Class.
 */
@ETLUdf(name = "udf_d8_id_obscure")
public class D8IdObscure extends UDF {
    static long LowComplement = 238675309;
    static long HighComplement = 10 * ((long) Math.pow(2, 32));
    static Long xor = HighComplement + LowComplement;

    public Long evaluate(Long inputId) {
    	return inputId == null ? null : inputId ^ xor;
    }
}