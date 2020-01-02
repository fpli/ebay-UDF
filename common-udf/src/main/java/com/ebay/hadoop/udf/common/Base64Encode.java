package com.ebay.hadoop.udf.common;

import com.ebay.hadoop.udf.common.encrypt.Base64Ebay;
import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;

@Description(
        name = "Base64Encode",
        value = "_FUNC_(str) - Encode string with base-64",
        extended = "Example:\n  > SELECT Base64Encode(<Column to be encoded>) FROM table LIMIT 1;\n")
public class Base64Encode extends UDF {

    Text result = new Text();

    public Base64Encode() {
    }

    public Text evaluate(Text s) {
        if (s == null) {
            return null;
        }
        result.set(Base64Ebay.encode(s.getBytes()));
        return result;
    }
}
