package com.ebay.hadoop.udf.soj;

import com.ebay.sojourner.common.sojlib.SOJNVL;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;

public class SojTagFetcher extends UDF {

    public Text evaluate(final Text payloadText, String key) {
        if (payloadText == null || key == null) {
            return null;
        }
        
        String payload = payloadText.toString();
        String tagValue =null;
        try {
            tagValue=SOJNVL.getTagValue(payload, key);
        }
        catch(Exception e)
        {
            tagValue="-99999";
        }
        if (tagValue == null) {
            return null;
        } else {
            return new Text(tagValue);
        }
    }
}
