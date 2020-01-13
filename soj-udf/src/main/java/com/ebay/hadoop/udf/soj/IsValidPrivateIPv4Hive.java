package com.ebay.hadoop.udf.soj;

import com.ebay.sojourner.common.sojlib.IsValidPrivateIPv4;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.BooleanWritable;
import org.apache.hadoop.io.Text;


public class IsValidPrivateIPv4Hive extends UDF {
	public BooleanWritable evaluate(Text guid) { 
        if (guid == null) {
            return new BooleanWritable(false);
        }

        String guids = guid.toString(); 
        return new BooleanWritable(IsValidPrivateIPv4.isValidIP(guids));
    }
}
