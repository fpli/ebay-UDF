package com.ebay.hadoop.udf.soj;

import com.ebay.sojourner.common.sojlib.SOJExtractNVP;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;

public class SojExtractNVP extends UDF{

	public Text evaluate(String payloadValue, String PayloadKey, String keyDelimiter, String valueDelimiter){
		if(payloadValue==null || PayloadKey==null){
			return null;
		}
		
		String payloadV = payloadValue.toString();
		String tagValue = SOJExtractNVP.getTagValue(payloadV, PayloadKey, keyDelimiter, valueDelimiter);
		if(tagValue==null){
			return null;
		}
		else return(new Text(tagValue));
		
	}

}
