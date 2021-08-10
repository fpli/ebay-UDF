package com.ebay.hadoop.udf.soj;

import com.ebay.sojourner.common.sojlib.SOJExtractNVP;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;

import java.util.ArrayList;
import java.util.List;

public class SojExtractListNVP extends UDF {

    public List<String> evaluate(String payloadValue, String PayloadKey, String keyDelimiter, String valueDelimiter) {
        if (payloadValue == null || PayloadKey == null) {
            return null;
        }
        String payloadV = payloadValue.toString();
        List<String> tagValues = getTagValue(payloadV, PayloadKey, keyDelimiter, valueDelimiter);
        if (CollectionUtils.isEmpty(tagValues)) {
            return null;
        } else return tagValues;
    }

    public static List<String> getTagValue(String value, String key, String keyDelimiter, String valueDelimiter) {
        List<String> values = new ArrayList<>();
        if (StringUtils.isNotBlank(value) && StringUtils.isNotBlank(key)) {
            int kLen = key.length();
            int kDelimiterLen = keyDelimiter.length();
            int vDelimiterLen = valueDelimiter.length();
            if (value.startsWith(key + valueDelimiter)) {
                String searchKey = key + valueDelimiter;
                int pos = value.indexOf(keyDelimiter, searchKey.length());
                if (pos >= 0) {
                    values.add(value.substring(searchKey.length(), pos));
                    values.addAll(getTagValue(value.substring(pos + 1), key, keyDelimiter, valueDelimiter));
                } else {
                    values.add(value.substring(searchKey.length()));
                }
            } else {
                String searchKey = keyDelimiter + key + valueDelimiter;
                int l = kLen + kDelimiterLen + vDelimiterLen;
                int startPos = value.indexOf(searchKey);
                if (startPos > 0) {
                    if (value.length() > l + startPos) {
                        int endPos = value.indexOf(keyDelimiter, l + startPos);
                        if (endPos >= 0) {
                            values.add(value.substring(l + startPos, endPos));
                            values.addAll(getTagValue(value.substring(endPos + 1), key, keyDelimiter, valueDelimiter));
                        } else {
                            values.add(value.substring(l + startPos));
                        }
                    }
                }
            }
        }
        return values;
    }

    public static boolean CheckEqual(List<String> f1, List<String> v1) {
        if ((v1 == null && f1 != null) || (v1 != null && f1 == null) || v1.size() != f1.size()) {
            return false;
        } else if ((v1 == null && f1 == null) || (CollectionUtils.isEmpty(f1) && CollectionUtils.isEmpty(v1))) {
            return true;
        } else {
            return CollectionUtils.isEqualCollection(f1, v1);
        }
    }

    public static void Verify(String value, String key, String keyDelimiter, String valueDelimiter) {
        List<String> f1 = getTagValue(value, key, keyDelimiter, valueDelimiter);
        List<String> v1 = SojExtractListNVP.getTagValue(value, key, keyDelimiter, valueDelimiter);
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
