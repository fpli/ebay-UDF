package com.ebay.hadoop.udf.soj;

import java.util.Map;
import org.apache.commons.collections.MapUtils;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;

public class SojMaptoStr extends UDF {

  private static final String FILED_DELIM = "=";
  private static final String COLUMN_DELIM = "&";

  public Text evaluate(Map<String, String> sojMap) {
    if (MapUtils.isEmpty(sojMap)) {
      return null;
    }
    StringBuilder sojStr = new StringBuilder();
    for (Map.Entry<String, String> kvPair : sojMap.entrySet()) {
      sojStr.append(kvPair.getKey()).append(FILED_DELIM).append(kvPair.getValue())
          .append(COLUMN_DELIM);
    }
    if (sojStr.length() > 0) {
      return new Text(sojStr.substring(0, sojStr.length() - 1));
    }
    return null;
  }

}
