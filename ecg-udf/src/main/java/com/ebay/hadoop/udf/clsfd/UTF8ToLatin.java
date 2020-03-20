package com.ebay.hadoop.udf.clsfd;

import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.log4j.Logger;

import java.io.UnsupportedEncodingException;

public class UTF8ToLatin extends UDF
{
    private static final Logger logger = Logger.getLogger(UTF8ToLatin.class);

    public String evaluate(String input) {
        if ((input == null) || (input.equals(""))) {
            return null;
        }
        String latinString = null;
        try {
            latinString = new String(input.getBytes("UTF-8"), "ISO-8859-1");
        }
        catch (UnsupportedEncodingException e) {
            //e.printStackTrace();
            logger.warn("Unsupported Encoding Err",new Exception(e) );
        }
        return latinString;
    }
}
