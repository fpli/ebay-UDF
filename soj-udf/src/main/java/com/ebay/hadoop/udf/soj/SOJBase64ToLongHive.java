package com.ebay.hadoop.udf.soj;

import com.ebay.sojourner.common.sojlib.SOJBase64ToLong;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;

public class SOJBase64ToLongHive extends UDF {

  public LongWritable evaluate(Text b64) {
    if (b64 == null) {
      return null;
    }

    String b64s = b64.toString();
    Long result = SOJBase64ToLong.getLong(b64s);
    if (result == null) {
      return null;
    } else {
      return new LongWritable(result);
    }
  }
}