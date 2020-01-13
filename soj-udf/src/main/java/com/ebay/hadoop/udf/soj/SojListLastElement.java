package com.ebay.hadoop.udf.soj;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hive.ql.exec.UDF;

/**
 * 
 * @author naravipati
 * 
 *         This UDF takes input as string and delimiter and returns the last
 *         element.
 *
 */

public class SojListLastElement extends UDF {

	public String evaluate(String str, String delimit) {

		String result;

		// Checking Given parameters are NULL or not

		if (str == null || delimit == null) {

			return null;
		}

		else {

			result = str.substring(str.lastIndexOf(delimit) + 1).trim();

			if (StringUtils.isBlank(result)) {

				return null;
			}

			return result;
		}

	}

}
