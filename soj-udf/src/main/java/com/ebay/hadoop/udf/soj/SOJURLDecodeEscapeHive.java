package com.ebay.hadoop.udf.soj;

import com.ebay.sojourner.common.sojlib.SOJURLDecodeEscape;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;

public class SOJURLDecodeEscapeHive extends UDF{
	
	public Text evaluate(Text url, String enc){ 
		if(url == null)
			return null;
		String result = SOJURLDecodeEscape.javaNetUrlDecode(url.toString(),  enc);
		if(result == null)
			return null;
		else return new Text(result);
	} 
}
