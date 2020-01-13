package com.ebay.hadoop.udf.soj;

import net.sf.json.JSONObject;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;

/**
 * xiaoding on 2017-12-28
 */
public class SojJsonParse extends UDF{

	public Text evaluate(String jsonStr, String jsonKey){
		if(jsonStr==null || jsonKey==null){
			return null;
		}
		JSONObject obj = JSONObject.fromObject(jsonStr);
		if(obj!=null) {
			if (obj.has(jsonKey)) {
				return new Text(obj.getString(jsonKey));
			}
			else
			{
				return null;
			}
		}
		else
		{
			return null;
		}
		
	}

}
