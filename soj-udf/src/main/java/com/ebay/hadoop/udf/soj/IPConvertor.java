package com.ebay.hadoop.udf.soj;

import com.ebay.hadoop.udf.utils.TransformUtil;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;

public final class IPConvertor extends UDF {

  public static final String FILES_DELIM = "\007";

  public Text evaluate(final Text ip) {
    if (ip == null || "".equals(ip.toString())) {
      return new Text("0");
    }

    String ipstr = TransformUtil.ipToInt(ip.toString()).toString();
    return new Text(ipstr);
  }
}
