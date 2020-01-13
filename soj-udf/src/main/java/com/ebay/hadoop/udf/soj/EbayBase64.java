package com.ebay.hadoop.udf.soj;

import com.ebay.sojourner.common.sojlib.Base64Ebay;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;

public class EbayBase64 extends UDF {

    public Text evaluate(Text encodedText) {
        return evaluate(encodedText, true);
    }
    
    public Text evaluate(Text encodedText, boolean isStandard) {
        if (encodedText == null) {
            return null;
        }
        
        byte[] bytes = Base64Ebay.decode(encodedText.toString(), isStandard);
        if (bytes == null) {
            return null;
        } else {
            return new Text(bytes);
        }
    }
}
