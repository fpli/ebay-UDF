package com.ebay.hadoop.udf.soj;

import com.ebay.hadoop.udf.tags.ETLUdf;
import org.apache.hadoop.hive.ql.exec.UDF;

/**
 * 
 * @author naravipati
 * 
 *         This UDF takes input as string,delimiter,element and returns the
 *         position of the first occurrence of the string if the given 
 *         string is available in list.
 *
 */
@ETLUdf(name = "soj_value_in_list")
public class SojValueInList extends UDF {

	public String evaluate(String str, String delimit, String element) {

		int index = 0;
		String result = null;

		// Checking Given parameters are NULL or not

		if (str == null || delimit == null || element == null) {

			return null;

		} else {

			for (String retval : str.split(delimit)) {
				index++;
				if (retval.equals(element)) {
					result = Integer.toString(index);
					break;
				}
			}

			return result;
		}
	}

}
