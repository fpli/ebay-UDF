package com.ebay.hadoop.udf.soj;

import com.ebay.hadoop.udf.utils.FunctionUtil;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;

/**
 * Created by xiaoding on 2017/1/20.
 */
public class SojGetBrowserType extends UDF {
    public Text evaluate( Text userAgent) {
        if(userAgent == null)
        {
            return new Text("NULL UserAgent");
        }
        String userAgentStr = userAgent.toString();
        if ("".equals(userAgentStr)) {
            return new Text("NULL UserAgent");
        } else if (FunctionUtil.isContain(userAgentStr, "Firefox")) {
            return new Text("Firefox");
        }else if (FunctionUtil.isContain(userAgentStr, "Opera")) {
            return new Text("Opera");
        }
        else if (FunctionUtil.isContain(userAgentStr, "Safari")&&!FunctionUtil.isContain(userAgentStr, "Chrome/")) {
            return new Text("Safari");
        }
        else if (FunctionUtil.isContain(userAgentStr, "WebTV")) {
            return new Text("WebTV/MSNTV");
        }
        else if (FunctionUtil.isContain(userAgentStr, "Netscape")) {
            return new Text("Netscape");
        }
        else if (FunctionUtil.isContain(userAgentStr, "MSIE")) {
            if (FunctionUtil.isContain(userAgentStr, "MSIE 5.2")||FunctionUtil.isContain(userAgentStr, "MSIE 5.1")) {
                return new Text("MacIE");
            } else {
                return new Text("IE");
            }
        }
        else if (FunctionUtil.isContain(userAgentStr, "Mozilla/4")) {
            return new Text("Netscape");
        }
        else if (FunctionUtil.isContain(userAgentStr, "Chrome")) {
            return new Text("Chrome");
        }
        else
        {
            return new Text("Unknown Browser Type");
        }
    }
}
