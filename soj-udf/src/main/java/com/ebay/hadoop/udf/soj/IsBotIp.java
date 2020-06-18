package com.ebay.hadoop.udf.soj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.BooleanWritable;
import org.apache.hadoop.io.Text;

public class IsBotIp extends UDF {

  private static final String LINUX_NS_LOOKUP = "nslookup";
  private static final String DNS_KEY = "name =";
  private static final String AMAZON_AWS = "amazonaws.";
  private static final String THE_PLANET = ".theplanet.";
  private static final String GI_XEN = ".gixen.com";
  private static final String POST_NEWS = "postnews";
  private static final String GOOGLE_APP = ".google.";
  private static final int RETRY_THRESHOLD_EACH_IP = 3;
  private Runtime runtime = Runtime.getRuntime();

  public BooleanWritable evaluate(final Text ip) {
    String ipStr = ip.toString();
    if (StringUtils.isBlank(ipStr)) {
      return new BooleanWritable(false);
    }
    boolean result = isBotHost(getHostByIp(ipStr, 0));
    return new BooleanWritable(result);
  }

  private String getHostByIp(String ip, int retryNumber) {
    String host = null;
    try {
      String[] ips = new String[]{LINUX_NS_LOOKUP, ip};
      Process proc = runtime.exec(ips);
      String result = getHostFromStdout(proc);
      if (StringUtils.isNotBlank(result)) {
        host = result.substring(result.indexOf(DNS_KEY) + DNS_KEY.length());
        if (host != null) {
          host = host.trim();
        }
      }
    } catch (Exception e) {
    }
    boolean isIp = isValidIP(host);
    if (retryNumber < RETRY_THRESHOLD_EACH_IP && isIp) {
      retryNumber++;
      host = getHostByIp(ip, retryNumber);
    }
    return host;
  }

  private String getHostFromStdout(Process proc) throws IOException {
    String hostInfo = null;
    String stdMsg = null;
    BufferedReader stdReader = new BufferedReader(new InputStreamReader(proc.getInputStream()));
    while ((stdMsg = stdReader.readLine()) != null) {
      if (stdMsg.contains(DNS_KEY)) {
        hostInfo = stdMsg;
      }
    }
    return hostInfo;
  }

  private boolean isBotHost(String host) {
    if (StringUtils.isNotBlank(host)) {
      return host.contains(AMAZON_AWS)
          || host.contains(THE_PLANET)
          || host.contains(GI_XEN)
          || (host.startsWith(POST_NEWS) && host.contains(GOOGLE_APP));
    }
    return false;
  }

  public static boolean isValidIP(String instr) {
    if (StringUtils.isBlank(instr)) {
      return false;
    }
    int iplen = 0;
    int ip1, ip2, ip3, ip4;
    String[] ipseg = null;
    iplen = instr.length();
    // Error if length is less than 7 or greater than 15
    if (iplen < 7 || iplen > 15) {
      return false;
    }
    ipseg = instr.split("\\.");
    // if all 4 values not returned then it is error
    if (ipseg.length != 4) {
      return false;
    }
    // if anyone of them is less than 0 or greater than 255 it is error
    try {
      // only trim left blank
      // align with TD udf
      ip1 = Integer.parseInt(ltrim(ipseg[0]));
      ip2 = Integer.parseInt(ltrim(ipseg[1]));
      ip3 = Integer.parseInt(ltrim(ipseg[2]));
      ip4 = Integer.parseInt(ltrim(ipseg[3]));

      if ((ip1 < 0 || ip1 > 255)
          || (ip2 < 0 || ip2 > 255)
          || (ip3 < 0 || ip3 > 255)
          || (ip4 < 0 || ip4 > 255)) {
        return false;
      }
    } catch (Exception e) {
      return false;
    }
    return true;
  }

  private static String ltrim(String value) {
    String result = value;
    if (result == null) {
      return result;
    }
    char[] ch = result.toCharArray();
    int index = -1;
    for (int i = 0; i < ch.length; i++) {
      if (Character.isWhitespace(ch[i])) {
        index = i;
      } else {
        break;
      }
    }
    if (index != -1) {
      result = result.substring(index + 1);
    }
    return result;
  }
}
