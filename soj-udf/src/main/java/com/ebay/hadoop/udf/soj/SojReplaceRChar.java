package com.ebay.hadoop.udf.soj;

import org.apache.hadoop.hive.ql.exec.UDF;

/**
 * 
 * @author Daniel Zhang
 * 
 *         This UDF takes input as string,charList,replaceChar and returns the
 *         string.
 *
 */

public class SojReplaceRChar extends UDF {

	public String evaluate(String str, String charList, String replaceChar) {
		// Checking the Given parameters are NULL or not
		if (str == null || charList == null || replaceChar == null) {
			return null;
		} else if (str.equals("")) {
			return "";
		} else {
			String firstChar = replaceChar.length() > 1 ?
					replaceChar.substring(0,1) :
					replaceChar;
			String chars = charList.equals("") ? "." : charList;
			return str.replaceAll("[^" + chars + "]", firstChar);
		}

	}
}
