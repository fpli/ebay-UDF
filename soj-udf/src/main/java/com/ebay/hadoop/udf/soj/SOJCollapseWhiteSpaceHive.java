package com.ebay.hadoop.udf.soj;

import com.ebay.sojourner.common.sojlib.SOJCollapseWhiteSpace;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;

public class SOJCollapseWhiteSpaceHive extends UDF {
	public Text evaluate(Text str) { 
        if (str == null) {
            return null;
        }

        String strs = str.toString(); 
        String result = SOJCollapseWhiteSpace.getString(strs);
        if(result != null)
        	return new Text(result);
        else return null;
    }
}
