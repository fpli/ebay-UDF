package com.ebay.hadoop.udf.common;

import com.ebay.hadoop.udf.tags.ETLUdf;
import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;

@ETLUdf(name = "soj_bytes2num")
public class ByteArrayToNumHive extends UDF {
  @Description(name = "ByteArrayToNum",
    value = "_FUNC_(ByteWritable) - Returns numeric value of the Byte array",
    extended = "Example:\n"
      + " > SELECT _FUNC_() FROM src LIMIT 1;\n")


  public Object evaluate(BytesWritable b64) {
    if (b64 == null) {
      return new LongWritable(ByteArrayToNum.getLong(null));
    }
    Long retValue =  ByteArrayToNum.getLong(b64.getBytes());
    if(retValue > (long)(Integer.MAX_VALUE))
      return new LongWritable(retValue);
    else return new IntWritable(ByteArrayToNum.getInt(b64.getBytes()));
  }
}
