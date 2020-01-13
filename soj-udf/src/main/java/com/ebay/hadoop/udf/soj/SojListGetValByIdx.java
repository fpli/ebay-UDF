package com.ebay.hadoop.udf.soj;

import com.ebay.sojourner.common.sojlib.SOJListGetValueByIndex;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;

public class SojListGetValByIdx extends UDF{
	
	public Text evaluate(final Text str_vec, String delim, int index){
		if(str_vec == null || delim == null || index < 1){
			return null;
		}
		
		String input_string = str_vec.toString();
		String return_str = SOJListGetValueByIndex.getValueByIndex(input_string, delim, index);
		
		if(return_str == null)
			return null;
		else
			return new Text(return_str);
		
	}

	public static void main(String[] args){

		String test="USD:0.0^-1^-1^2019-11-06T03:00:00-07:00^2019-11-06T03:00:00-07:00^STANDARD^ShippingMethodStandard^0^-1^1^-1^US^02135-2239^254398941029^-1^3^3";
		Text text = new Text();
		text.set(test);

		System.out.println(SOJListGetValueByIndex.getValueByIndex(test,"^" , 12));
	}
}