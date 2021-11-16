package com.ebay.hadoop.udf.soj;

import static org.junit.Assert.assertEquals;

import org.apache.hadoop.io.Text;
import org.junit.Test;

public class ClientInfoParserTest {

  @Test
  public void test() throws Exception {

    assertEquals(new Text("10.15.15.141&&&"), new ClientInfoParser().evaluate(new Text(
                    "Script=/roverimp/0/0/9&a=b&Agent=Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:81.0) "
                            + "Gecko/20100101 Firefox/81.0&&&Server=rover.ebay.de&RemoteIP=84.181.53"
                            + ".104&Referer=https://www.ebay.de/itm/Lebensgroße-Figur/124360322508&TType=URL"
                            + "&Encoding=gzip&TPayload=corr_id_%3Df959a522f00b35b1%26node_id"
                            + "%3D4ba84c525d2ae5e0%26REQUEST_GUID%3D17504b66-3de0-a0f0-f8d6-4c55f7c420f3"
                            + "%26logid%3Dt6qjpbq%253F%253Cumjthu%2560t%2Aqk%257Euh%2528rbpv6713-17504b663e3"
                            + "-0x11a%26cal_mod%3Dfalse&TStamp=13:17:43"
                            + ".39&TPool=r1rover&a=b&&TStatus=0&TDuration=6&ContentLength=-1&TName"
                            + "=roverimp_INTL"
                            + "&chUaModel=ios&chUaPlatformVersion=1.0.1"
                            +"&chUaFullVersion=1.0.9.111.1&chUaMobile=ios"
                            + "&ForwardedFor=&&&&TMachine=10.15.15.141&&&"),
            "TMachine"));
    assertEquals(new Text("84.181.53.104"), new ClientInfoParser().evaluate(new Text(
                    "Script=/roverimp/0/0/9&Agent=Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:81.0) "
                            + "Gecko/20100101 Firefox/81.0&Server=rover.ebay.de&RemoteIP=84.181.53"
                            + ".104&Referer=https://www.ebay.de/itm/Lebensgroße-Figur/124360322508&TType=URL"
                            + "&Encoding=gzip&TPayload=corr_id_%3Df959a522f00b35b1%26node_id"
                            + "%3D4ba84c525d2ae5e0%26REQUEST_GUID%3D17504b66-3de0-a0f0-f8d6-4c55f7c420f3"
                            + "%26logid%3Dt6qjpbq%253F%253Cumjthu%2560t%2Aqk%257Euh%2528rbpv6713-17504b663e3"
                            + "-0x11a%26cal_mod%3Dfalse&TStamp=13:17:43"
                            + ".39&TPool=r1rover&&TStatus=0&TDuration=6&ContentLength=-1&TName=roverimp_INTL"
                            + "&chUaModel=ios&chUaPlatformVersion=1.0.1"
                            +"&chUaFullVersion=1.0.9.111.1&chUaMobile=ios"
                            + "&ForwardedFor=84.181.53.104&TMachine=10.15.15.141"),
            "ForwardedFor"));
    assertEquals(new Text("10.15.15.141"), new ClientInfoParser().evaluate(new Text(
                    "Script=/roverimp/0/0/9&Agent=Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:81.0) "
                            + "Gecko/20100101 Firefox/81.0&Server=rover.ebay.de&RemoteIP=84.181.53"
                            + ".104&Referer=https://www.ebay.de/itm/Lebensgroße-Figur/124360322508&TType=URL"
                            + "&Encoding=gzip&TPayload=corr_id_%3Df959a522f00b35b1%26node_id"
                            + "%3D4ba84c525d2ae5e0%26REQUEST_GUID%3D17504b66-3de0-a0f0-f8d6-4c55f7c420f3"
                            + "%26logid%3Dt6qjpbq%253F%253Cumjthu%2560t%2Aqk%257Euh%2528rbpv6713-17504b663e3"
                            + "-0x11a%26cal_mod%3Dfalse&TStamp=13:17:43"
                            + ".39&TPool=r1rover&&TStatus=0&TDuration=6&ContentLength=-1&TName=roverimp_INTL"
                            + "&ForwardedFor=84.181.53.104&TMachine=10.15.15.141"
                            + "&chUaModel=ios&chUaPlatformVersion=1.0.1"
                            +"&chUaFullVersion=1.0.9.111.1&chUaMobile=ios"
            ),
            "TMachine"));
    assertEquals(new Text("7e6e65361780a9f54e93eba0fff08bc6"), new ClientInfoParser().evaluate(new Text(
                    "Script=/roverimp/0/0/9&Agent=Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:81.0) "
                            + "Gecko/20100101 Firefox/81.0&Server=rover.ebay.de&RemoteIP=84.181.53"
                            + ".104&Referer=https://www.ebay.de/itm/Lebensgroße-Figur/124360322508&TType=URL"
                            + "&Encoding=gzip&TPayload=corr_id_%3Df959a522f00b35b1%26node_id"
                            + "%3D4ba84c525d2ae5e0%26REQUEST_GUID%3D17504b66-3de0-a0f0-f8d6-4c55f7c420f3"
                            + "%26logid%3Dt6qjpbq%253F%253Cumjthu%2560t%2Aqk%257Euh%2528rbpv6713-17504b663e3"
                            + "-0x11a%26cal_mod%3Dfalse&TStamp=13:17:43"
                            + ".39&TPool=r1rover&&TStatus=0&TDuration=6&ContentLength=-1&TName=roverimp_INTL"
                            + "&ForwardedFor=84.181.53.104&TMachine=10.15.15.141" +
                            "&corrId=7e6e65361780a9f54e93eba0fff08bc6"),
            "corrId"));
    assertEquals(new Text("10.15.15.141"), new ClientInfoParser().evaluate(new Text(
                    "chUaModel=ios&chUaPlatformVersion=1.0.1"
                            +"&chUaFullVersion=1.0.9.111.1&chUaMobile=ios"
                            +"&Script=/roverimp/0/0/9&Agent=Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:81.0) "
                            + "Gecko/20100101 Firefox/81.0&Server=rover.ebay.de&RemoteIP=84.181.53"
                            + ".104&Referer=https://www.ebay.de/itm/Lebensgroße-Figur/124360322508&TType=URL"
                            + "&Encoding=gzip&TPayload=corr_id_%3Df959a522f00b35b1%26node_id"
                            + "%3D4ba84c525d2ae5e0%26REQUEST_GUID%3D17504b66-3de0-a0f0-f8d6-4c55f7c420f3"
                            + "%26logid%3Dt6qjpbq%253F%253Cumjthu%2560t%2Aqk%257Euh%2528rbpv6713-17504b663e3"
                            + "-0x11a%26cal_mod%3Dfalse&TStamp=13:17:43"
                            + ".39&TPool=r1rover&&TStatus=0&TDuration=6&ContentLength=-1&TName=roverimp_INTL"
                            + "&ForwardedFor=84.181.53.104&TMachine=10.15.15.141" +
                            "&corrId=7e6e65361780a9f54e93eba0fff08bc6"),
            "TMachine"));
    assertEquals(new Text("7e6e65361780a9f54e93eba0fff08bc6"), new ClientInfoParser().evaluate(new Text(
                    "Script=/roverimp/0/0/9&Agent=Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:81.0) "
                            + "Gecko/20100101 Firefox/81.0&Server=rover.ebay.de&RemoteIP=84.181.53"
                            + ".104&Referer=https://www.ebay.de/itm/Lebensgroße-Figur/124360322508&TType=URL"
                            + "&Encoding=gzip&TPayload=corr_id_%3Df959a522f00b35b1%26node_id"
                            + "%3D4ba84c525d2ae5e0%26REQUEST_GUID%3D17504b66-3de0-a0f0-f8d6-4c55f7c420f3"
                            + "%26logid%3Dt6qjpbq%253F%253Cumjthu%2560t%2Aqk%257Euh%2528rbpv6713-17504b663e3"
                            + "-0x11a%26cal_mod%3Dfalse&TStamp=13:17:43"
                            + ".39&TPool=r1rover&&TStatus=0&TDuration=6&ContentLength=-1&TName=roverimp_INTL"
                            + "&ForwardedFor=84.181.53.104&TMachine=10.15.15.141&nodeId=7e6e65361780a9f54e93eba0fff08bc6"),
            "nodeId"));
    assertEquals(new Text("10.15.15.141"), new ClientInfoParser().evaluate(new Text(
                    "Script=/roverimp/0/0/9&Agent=Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:81.0) "
                            + "Gecko/20100101 Firefox/81.0&Server=rover.ebay.de&RemoteIP=84.181.53"
                            + ".104&Referer=https://www.ebay.de/itm/Lebensgroße-Figur/124360322508&TType=URL"
                            + "&Encoding=gzip&TPayload=corr_id_%3Df959a522f00b35b1%26node_id"
                            + "%3D4ba84c525d2ae5e0%26REQUEST_GUID%3D17504b66-3de0-a0f0-f8d6-4c55f7c420f3"
                            + "%26logid%3Dt6qjpbq%253F%253Cumjthu%2560t%2Aqk%257Euh%2528rbpv6713-17504b663e3"
                            + "-0x11a%26cal_mod%3Dfalse&TStamp=13:17:43.39"
                            + "&chUaModel=ios&chUaPlatformVersion=1.0.1"
                            + "&chUaFullVersion=1.0.9.111.1&chUaMobile=ios"
                            + "&TPool=r1rover&&TStatus=0&TDuration=6&ContentLength=-1&TName=roverimp_INTL"
                            + "&ForwardedFor=84.181.53.104&TMachine=10.15.15.141&nodeId=7e6e65361780a9f54e93eba0fff08bc6"),
            "TMachine"));
    assertEquals(new Text("ios"), new ClientInfoParser().evaluate(new Text(
                    "Script=/roverimp/0/0/9&Agent=Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:81.0) "
                            + "Gecko/20100101 Firefox/81.0&Server=rover.ebay.de&RemoteIP=84.181.53"
                            + ".104&Referer=https://www.ebay.de/itm/Lebensgroße-Figur/124360322508&TType=URL"
                            + "&Encoding=gzip&TPayload=corr_id_%3Df959a522f00b35b1%26node_id"
                            + "%3D4ba84c525d2ae5e0%26REQUEST_GUID%3D17504b66-3de0-a0f0-f8d6-4c55f7c420f3"
                            + "%26logid%3Dt6qjpbq%253F%253Cumjthu%2560t%2Aqk%257Euh%2528rbpv6713-17504b663e3"
                            + "-0x11a%26cal_mod%3Dfalse&TStamp=13:17:43.39"
                            + "&chUaModel=ios&chUaPlatformVersion=1.0.1"
                            + "&chUaFullVersion=1.0.9.111.1&chUaMobile=ios"
                            + "&TPool=r1rover&&TStatus=0&TDuration=6&ContentLength=-1&TName=roverimp_INTL"
                            + "&ForwardedFor=84.181.53.104&TMachine=10.15.15.141&nodeId=7e6e65361780a9f54e93eba0fff08bc6"),
            "chUaModel"));
    assertEquals(new Text("1.0.1"), new ClientInfoParser().evaluate(new Text(
                    "Script=/roverimp/0/0/9&Agent=Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:81.0) "
                            + "Gecko/20100101 Firefox/81.0&Server=rover.ebay.de&RemoteIP=84.181.53"
                            + ".104&Referer=https://www.ebay.de/itm/Lebensgroße-Figur/124360322508&TType=URL"
                            + "&Encoding=gzip&TPayload=corr_id_%3Df959a522f00b35b1%26node_id"
                            + "%3D4ba84c525d2ae5e0%26REQUEST_GUID%3D17504b66-3de0-a0f0-f8d6-4c55f7c420f3"
                            + "%26logid%3Dt6qjpbq%253F%253Cumjthu%2560t%2Aqk%257Euh%2528rbpv6713-17504b663e3"
                            + "-0x11a%26cal_mod%3Dfalse&TStamp=13:17:43.39"
                            + "&chUaModel=ios&chUaPlatformVersion=1.0.1"
                            + "&chUaFullVersion=1.0.9.111.1&chUaMobile=ios"
                            + "&TPool=r1rover&&TStatus=0&TDuration=6&ContentLength=-1&TName=roverimp_INTL"
                            + "&ForwardedFor=84.181.53.104&TMachine=10.15.15.141&nodeId=7e6e65361780a9f54e93eba0fff08bc6"),
            "chUaPlatformVersion"));
    assertEquals(new Text("1.0.9.111.1"), new ClientInfoParser().evaluate(new Text(
                    "Script=/roverimp/0/0/9&Agent=Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:81.0) "
                            + "Gecko/20100101 Firefox/81.0&Server=rover.ebay.de&RemoteIP=84.181.53"
                            + ".104&Referer=https://www.ebay.de/itm/Lebensgroße-Figur/124360322508&TType=URL"
                            + "&Encoding=gzip&TPayload=corr_id_%3Df959a522f00b35b1%26node_id"
                            + "%3D4ba84c525d2ae5e0%26REQUEST_GUID%3D17504b66-3de0-a0f0-f8d6-4c55f7c420f3"
                            + "%26logid%3Dt6qjpbq%253F%253Cumjthu%2560t%2Aqk%257Euh%2528rbpv6713-17504b663e3"
                            + "-0x11a%26cal_mod%3Dfalse&TStamp=13:17:43.39"
                            + "&chUaModel=ios&chUaPlatformVersion=1.0.1"
                            + "&chUaFullVersion=1.0.9.111.1&chUaMobile=ios"
                            + "&TPool=r1rover&&TStatus=0&TDuration=6&ContentLength=-1&TName=roverimp_INTL"
                            + "&ForwardedFor=84.181.53.104&TMachine=10.15.15.141&nodeId=7e6e65361780a9f54e93eba0fff08bc6"),
            "chUaFullVersion"));
    assertEquals(new Text("ios"), new ClientInfoParser().evaluate(new Text(
                    "Script=/roverimp/0/0/9&Agent=Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:81.0) "
                            + "Gecko/20100101 Firefox/81.0&Server=rover.ebay.de&RemoteIP=84.181.53"
                            + ".104&Referer=https://www.ebay.de/itm/Lebensgroße-Figur/124360322508&TType=URL"
                            + "&Encoding=gzip&TPayload=corr_id_%3Df959a522f00b35b1%26node_id"
                            + "%3D4ba84c525d2ae5e0%26REQUEST_GUID%3D17504b66-3de0-a0f0-f8d6-4c55f7c420f3"
                            + "%26logid%3Dt6qjpbq%253F%253Cumjthu%2560t%2Aqk%257Euh%2528rbpv6713-17504b663e3"
                            + "-0x11a%26cal_mod%3Dfalse&TStamp=13:17:43.39"
                            + "&chUaModel=ios&chUaPlatformVersion=1.0.1"
                            + "&chUaFullVersion=1.0.9.111.1&chUaMobile=ios"
                            + "&TPool=r1rover&&TStatus=0&TDuration=6&ContentLength=-1&TName=roverimp_INTL"
                            + "&ForwardedFor=84.181.53.104&TMachine=10.15.15.141&nodeId=7e6e65361780a9f54e93eba0fff08bc6"),
            "chUaMobile"));
  }
}