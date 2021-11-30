package com.ebay.hadoop.udf.utils;

import com.ebay.hadoop.udf.stringsearch.StringSearcherExt;
import org.apache.commons.lang.StringUtils;
import org.neosearch.stringsearcher.Emit;

/**
 * Created by xiaoding on 2017/1/20.
 */
public class FunctionUtil {
    private static StringSearcherExt<?> stringSearcher = StringSearcherExt.builder()
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
            .addSearchString("&chUaModel=")
            .addSearchStrings("&chUaPlatformVersion=")
            .addSearchString("&chUaFullVersion=")
            .addSearchStrings("&chUaMobile=")
            .build();

    private static int isValidCIname(String key) {
        String candidates = "ForwardedFor|RemoteIP|Referer|ContentLength|Script|Server|Agent|Encoding"
                + "|TPool|TStamp|TType|TName|TStatus|TDuration|TPayload|TMachine|corrId|nodeId|chUaModel"
                + "|chUaPlatformVersion|chUaFullVersion|chUaMobile";
        return candidates.indexOf(key) >= 0 ? 1 : 0;
    }

    public static boolean isContain( String str, String subStr ) {

        if (str.contains(subStr)) {
            return true;
        } else {
            return false;
        }
    }

    public static String extWoCopy(String clientinfo, String key) {
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
