package com.ebay.hadoop.udf.soj;

import com.ebay.hadoop.udf.stringsearch.StringSearcherExt;
import com.ebay.hadoop.udf.tags.ETLUdf;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;
import org.neosearch.stringsearcher.Emit;

@ETLUdf(name = "soj_parse_clientinfo")
public final class ClientInfoParser extends UDF {

  StringSearcherExt<?> stringSearcher = StringSearcherExt.builder()
      .addSearchString("&ForwardedFor=")
      .addSearchString("&RemoteIP=")
      .addSearchString("&Referer=")
      .addSearchString("&ContentLength=")
      .addSearchString("&Script=")
      .addSearchString("&Server=")
      .addSearchString("&Agent=")
      .addSearchString("&Encoding=")
      .addSearchString("&TPool=")
      .addSearchString("&TStamp=")
      .addSearchString("&TType=")
      .addSearchString("&TName=")
      .addSearchString("&TStatus=")
      .addSearchString("&TDuration=")
      .addSearchString("&TPayload=")
      .addSearchString("&TMachine=")
      .addSearchString("&corrId=")
      .addSearchString("&nodeId=")
      .build();

  private static int isValidCIname(String key) {
    String candidates = "ForwardedFor|RemoteIP|Referer|ContentLength|Script|Server|Agent|Encoding"
        + "|TPool|TStamp|TType|TName|TStatus|TDuration|TPayload|TMachine|corrId|nodeId";
    return candidates.indexOf(key) >= 0 ? 1 : 0;
  }

  public static void test1() {
    String clientInfo = "TPayload=corr_id_%3D10b9ca021740aca46a4f12d2ffafded3%26node_id"
        + "%3D1a40fbd5899c044b%26REQUEST_GUID%3D17410b9c-a790-a77d-8e02-63b0ffc8bd81%26logid"
        + "%3Dt6wwm53vpd77%253C%253Dqkisqn47pse31%2528%2560coq1%2Aw%2560ut3542-17410b9ca7d-0x1af"
        + "%26cal_mod%3Dfalse&TPool=r1lqgsvc&TDuration=3&TStatus=0&TType=URL&ContentLength=2080"
        + "&ForwardedFor=92.169.117.31;2.17.114"
        + ".43&Script=/trk20svc/TrackingResource/v1&Server=www.ebay.fr&TMachine=10.119.216"
        + ".224&TStamp=04:14:05.56&TName=Ginger.CollectionSvc.track&Agent=Mozilla/5.0 (Macintosh;"
        + " Intel Mac OS X 10_15_6) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.1.2 "
        + "Safari/605.1.15&RemoteIP=92.169.117.31&Encoding=gzip&Referer=https://www.ebay"
        + ".fr/sl/list/v2?draft_id=2234338318023&mode=AddItem&productEnforcement=false";
    ClientInfoParser optParser = new ClientInfoParser();
    String keys[] = {"TPayload", "Referer", "Agent", "ForwardedFor", "RemoteIP"};
    for (String k : keys) {
      String v = optParser.extWoCopy(clientInfo, k);
      System.out.println(k + " : " + v);
    }
  }

  public static void singleTest() {
    String clientInfo = "TPayload=corr_id_%3D10b9ca021740aca46a4f12d2ffafded3%26node_id"
        + "%3D1a40fbd5899c044b%26REQUEST_GUID%3D17410b9c-a790-a77d-8e02-63b0ffc8bd81%26logid"
        + "%3Dt6wwm53vpd77%253C%253Dqkisqn47pse31%2528%2560coq1%2Aw%2560ut3542-17410b9ca7d-0x1af"
        + "%26cal_mod%3Dfalse&TPool=r1lqgsvc&TDuration=3&TStatus=0&TType=URL&ContentLength=2080"
        + "&ForwardedFor=92.169.117.31;2.17.114"
        + ".43&Script=/trk20svc/TrackingResource/v1&Server=www.ebay.fr&TMachine=10.119.216"
        + ".224&TStamp=04:14:05.56&TName=Ginger.CollectionSvc.track&Agent=Mozilla/5.0 (Macintosh;"
        + " Intel Mac OS X 10_15_6) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.1.2 "
        + "Safari/605.1.15&RemoteIP=92.169.117.31&Encoding=gzip&Referer=https://www.ebay"
        + ".fr/sl/list/v2?draft_id=2234338318023&mode=AddItem&productEnforcement=false";
    ClientInfoParser optParser = new ClientInfoParser();
    String k = "Script";
    String v = optParser.extWoCopy(clientInfo, k);
    System.out.println(k + " : " + v);
  }

  public static void main(String args[]) {
    test1();
  }

  public Text evaluate(final Text clientInfoTexT, String clientField) {
    if (clientInfoTexT == null || clientField == null) {
      return null;
    }

    String clientInfo = clientInfoTexT.toString();
    String value = extWoCopy(clientInfo, clientField);
    if (value == null) {
      return null;
    } else {
      return new Text(value);
    }
  }

  public String extWoCopy(String clientinfo, String key) {
    if (StringUtils.isBlank(clientinfo)) {
      return null;
    } else if (!StringUtils.isBlank(key) && isValidCIname(key) != 0) {
      int valueStartPos;
      int tmpPos = -1;
      if (clientinfo.startsWith(key + '=')) {
        valueStartPos = (key + '=').length();
        Emit e = stringSearcher.firstMatch(clientinfo, valueStartPos);
        if (e == null) {
          return clientinfo.substring(valueStartPos);
        } else {
          tmpPos = e.getStart();
          return clientinfo.substring(valueStartPos, tmpPos);
        }
      } else if (clientinfo.indexOf("&" + key + '=') < 0) {
        return null;
      } else {
        int p = clientinfo.indexOf("&" + key + '=');
        valueStartPos = p + ("&" + key + '=').length();
        Emit e = stringSearcher.firstMatch(clientinfo, valueStartPos);
        if (e == null) {
          return clientinfo.substring(valueStartPos);
        } else {
          tmpPos = e.getStart();
          return clientinfo.substring(valueStartPos, tmpPos);
        }
      }
    } else {
      return null;
    }
  }


}