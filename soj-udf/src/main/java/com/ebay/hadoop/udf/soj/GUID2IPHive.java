package com.ebay.hadoop.udf.soj;


import com.ebay.hadoop.udf.tags.ETLUdf;
import com.ebay.sojourner.common.sojlib.GUID2IP;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;

@ETLUdf(name = "soj_guid_ip")
public class GUID2IPHive extends UDF {
	
	public Text evaluate(Text guid) { 
        if (guid == null) {
            return null;
        }

        String guids = guid.toString(); 
        String result = GUID2IP.getIP(guids);
        if(result != null) 
        	return new Text(result);
        else return null;
    }
}
