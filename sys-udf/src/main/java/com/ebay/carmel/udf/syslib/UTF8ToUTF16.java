package com.ebay.carmel.udf.syslib;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hive.ql.exec.UDF;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

public class UTF8ToUTF16 extends UDF {

    // TODO impl need refer to https://github.corp.ebay.com/APD/APD-Arch/tree/master/udf/syslib
    public String evaluate(String input) throws UnsupportedEncodingException {
        if(StringUtils.isBlank(input)) return null;
        return new String(input.getBytes(Charset.forName("UTF-16")),"UTF-16");
    }
}
