package com.ebay.hadoop.udf.soj;

import com.ebay.hadoop.udf.tags.ETLUdf;
import com.ebay.sojourner.common.sojlib.SOJGetPageType;
import org.apache.hadoop.hive.ql.exec.UDF;

@ETLUdf(name = "soj_get_page_type")
public class GetPageType extends UDF{
	
	public Integer evaluate(String itemId, String flags, int rdt, int id, Integer lkup, Integer fbp){
		if(itemId==null || flags==null || rdt<0 || id<0 || lkup==null || fbp==null){
			return null; //check if this is okay
		}
		
		Integer pageTypeValue = SOJGetPageType.soj_get_page_type(itemId, flags, rdt, id, lkup, fbp);
		if(pageTypeValue==null){
			return null; // check if this is okay
		}
		else
			return pageTypeValue;
	}

}
