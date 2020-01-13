package com.ebay.hadoop.udf.soj;

import org.apache.hadoop.hive.ql.exec.UDF;

public class SojListElementCount extends UDF {

	public Integer evaluate(String str, String delimit) {

		String result;

		// Checking Given parameters are NULL or not

		if (str == null || delimit == null) {
			return null;
		} else if (str.equals("")) {
			return 0;
		} else if (delimit.equals("")) {
			return 1;
		} else {
			Integer cnt = 1;
			String del = delimit.substring(0,1);
			for (int i = 0; i < str.length(); i++)
				if (str.substring(i,i + 1).equals(del))
					cnt++;
			return cnt;
		}

	}

}
