package com.ebay.hadoop.udf.soj;

import com.ebay.hadoop.udf.utils.TransformUtil;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;

public final class AgentIPConvertor extends UDF {

  public static final String FIELD_DELIM = "\007";
  String agent = 0 + FIELD_DELIM + 0;
  String ipstr = "0";

  public Text evaluate(final Text userAgent, final Text ip) {
    if (userAgent != null && !"".equals(userAgent.toString())) {
      long[] long4AgentHash = TransformUtil.md522Long(TransformUtil.getMD5(userAgent.toString()));
      agent = long4AgentHash[0] + FIELD_DELIM + long4AgentHash[1];
    }
    if (ip != null && !"".equals(ip.toString())) {

      ipstr = TransformUtil.ipToInt(ip.toString()).toString();

    }
    return new Text(agent+FIELD_DELIM+ipstr);
  }
}
