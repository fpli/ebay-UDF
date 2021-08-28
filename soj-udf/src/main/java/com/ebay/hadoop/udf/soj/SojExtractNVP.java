package com.ebay.hadoop.udf.soj;

import com.ebay.hadoop.udf.tags.ETLUdf;
import com.ebay.sojourner.common.sojlib.SOJExtractNVP;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;

@ETLUdf(name = "soj_extract_nvp")
public class SojExtractNVP extends UDF{

	public Text evaluate(String payloadValue, String PayloadKey, String keyDelimiter, String valueDelimiter){
		if(payloadValue==null || PayloadKey==null){
			return null;
		}

		String payloadV = payloadValue.toString();
		String tagValue = getTagValue(payloadV, PayloadKey, keyDelimiter, valueDelimiter);
		if(tagValue==null){
			return null;
		}
		else return(new Text(tagValue));

	}

	public static String getTagValue(String value, String key, String keyDelimiter, String valueDelimiter) {
		if (StringUtils.isNotBlank(value) && StringUtils.isNotBlank(key)) {
			int kLen = key.length();
			int kDelimiterLen = keyDelimiter.length();
			int vDelimiterLen = valueDelimiter.length();
			if (value.startsWith(key + valueDelimiter)) {
				String searchKey = key + valueDelimiter;
				int pos = value.indexOf(keyDelimiter, searchKey.length());
				if (pos >= 0) {
					return value.substring(searchKey.length(), pos);
				} else {
					return value.substring(searchKey.length());
				}
			} else {
				String searchKey = keyDelimiter + key + valueDelimiter;
				int l = kLen + kDelimiterLen + vDelimiterLen;
				int startPos = value.indexOf(searchKey);
				if (startPos > 0) {
					if (value.length() > l + startPos) {
						int endPos = value.indexOf(keyDelimiter, l + startPos);
						if (endPos >= 0) {
							return value.substring(l + startPos, endPos);
						} else {
							return value.substring(l + startPos);
						}
					} else {
						return null;
					}
				} else {
					return null;
				}
			}
		} else {
			return null;
		}
	}

	public static boolean CheckEqual(String f1, String v1) {
		if ((v1 == null && f1 != null) || (v1 != null && f1 == null)) {
			return false;
		} else if (v1 == null && f1 == null) {
			return true;
		} else {
			return v1.equals(f1);
		}
	}
	public static void Verify(String value, String key, String keyDelimiter, String valueDelimiter) {
		String f1 = getTagValue(value, key, keyDelimiter, valueDelimiter);
		String v1 = SOJExtractNVP.getTagValue(value, key, keyDelimiter, valueDelimiter);
		if (!CheckEqual(f1, v1)) {
			System.out.println("Failed");
		} else {
			System.out.println("Pass");
		}
	}

	public static void main(String[] args) throws Exception {
		Verify("h8=d3", "h8", "&", "=");
		Verify("h8=d3&h4=d9&", "h8", "&", "=");
		Verify("h8=d3&h4=d9&", "h4", "&", "=");
		Verify("h8=d3&h4=d9", "h4", "&", "=");
		Verify("h8=d3&h5=d9", "h4", "&", "=");
		Verify("h8=d3&h4=", "h4", "&", "=");
		System.out.println("params = " + getTagValue("h8=d3", "h8", "&", "="));
		System.out.println("params = " + getTagValue("h8=d3&h4=d9&", "h8", "&", "="));
		System.out.println("params = " + getTagValue("h8=d3&h4=d9&", "h4", "&", "="));
		System.out.println("params = " + getTagValue("h8=d3&h4=d9", "h4", "&", "="));
		System.out.println("params = " + getTagValue("h8=d3&h5=d9", "h4", "&", "="));
		System.out.println("params = " + getTagValue("h8=d3&h4=", "h4", "&", "="));
	}
}
