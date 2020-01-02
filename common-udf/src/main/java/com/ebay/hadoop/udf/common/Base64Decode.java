package com.ebay.hadoop.udf.common;

import com.ebay.hadoop.udf.common.encrypt.Base64Ebay;
import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;

@Description(
		name = "Base64Decode",
		value = "_FUNC_(str) - Decode base-64 encoded string",
		extended = "Example:\n  > SELECT Base64Decode(<base-64 encoded column>) FROM table LIMIT 1;\n")
public class Base64Decode extends UDF {

	Text result = new Text();

	public Base64Decode() {
	}

	public Text evaluate(Text s) {
		if (s == null) {
			return null;
		}
		result.set(Base64Ebay.decode(s.toString()));
		return result;
	}
}
