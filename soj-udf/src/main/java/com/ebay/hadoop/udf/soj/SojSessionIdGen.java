package com.ebay.hadoop.udf.soj;

import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;

/**
 * xiaoding on 2022-12-28
 */
public class SojSessionIdGen extends UDF{

	public Text evaluate(String guid, long timestamp){
		return new Text(concatTimestamp(guid,timestamp));
	}

	private String concatTimestamp(String prefix, long unixTimestamp) {
		int prefixLen = 0;
		if (!StringUtils.isBlank(prefix)) {
			prefixLen = prefix.length();
		} else {
			prefix = "";
		}
		StringBuilder builder = new StringBuilder(prefixLen + 16);
		builder.append(prefix);
		String x = Long.toHexString(unixTimestamp);
		for (int i = 16 - x.length(); i > 0; i--) {
			builder.append('0');
		}
		builder.append(x);
		return builder.toString();
	}

}
