package com.ebay.hadoop.udf.soj;


import com.ebay.hadoop.udf.utils.FunctionUtil;
import com.ebay.sojourner.common.sojlib.SojGetUaVersion;
import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;

/**
 * Created by xiaoding on 2021/11/24.
 */
//
public class SojGetOsNG extends UDF {
    public Text evaluate(String clientData) {

        String agent = FunctionUtil.extWoCopy(clientData, "Agent");
        boolean isNewVersion = false;
        String chUaPlatformVersion = FunctionUtil.extWoCopy(clientData, "chUaPlatformVersion");
        if (agent == null) {
            return new Text("unknown");
        }
        if (StringUtils.isNotEmpty(chUaPlatformVersion)) {
            isNewVersion = true;
        }
        String strUpper = agent.toUpperCase();
        String strOrigin = agent;
        if (FunctionUtil.isContain(strUpper, "Windows-NT".toUpperCase())) {
            return new Text("Windows NT");
        } else if (isNewVersion?((FunctionUtil.isContain(strUpper, "Windows ".toUpperCase())
                && chUaPlatformVersion.contains("7"))
                || (FunctionUtil.isContain(strUpper, "Windows NT ".toUpperCase())
                && chUaPlatformVersion.contains("6.1"))):
                (FunctionUtil.isContain(strUpper,"Windows 7".toUpperCase())
                        ||FunctionUtil.isContain(strUpper,"Windows NT 6.1".toUpperCase()))) {
            return new Text("Windows 7");
        } else if (isNewVersion?(FunctionUtil.isContain(strUpper, "Windows NT ".toUpperCase())
                && chUaPlatformVersion.contains("6.0")):
                (FunctionUtil.isContain(strUpper,"Windows NT 6.0".toUpperCase()))) {
            return new Text("Windows Vista");
        } else if (isNewVersion?(FunctionUtil.isContain(strUpper, "Windows NT ".toUpperCase())
                && chUaPlatformVersion.contains("6")):
                (FunctionUtil.isContain(strUpper,"Windows NT 6".toUpperCase()))) {
            return new Text("Windows 7");
        } else if (isNewVersion?(FunctionUtil.isContain(strUpper, "Windows NT ".toUpperCase())
                && chUaPlatformVersion.contains("5.2")):
                (FunctionUtil.isContain(strUpper,"Windows NT 5.2".toUpperCase()))) {
            return new Text("Windows 2003");
        } else if (isNewVersion?(FunctionUtil.isContain(strUpper, "Windows NT ".toUpperCase())
                && chUaPlatformVersion.contains("5.1")):
                (FunctionUtil.isContain(strUpper,"Windows NT 5.1".toUpperCase()))) {
            return new Text("Windows XP");
        } else if (isNewVersion?(FunctionUtil.isContain(strUpper, "Windows NT ".toUpperCase())
                && (chUaPlatformVersion.contains("5.0") ||chUaPlatformVersion.contains("5"))):
                (FunctionUtil.isContain(strUpper,"Windows NT 5.0".toUpperCase())||
                        FunctionUtil.isContain(strUpper,"Windows NT 5".toUpperCase()))) {
            return new Text("Windows 2000");
        } else if ((isNewVersion?(FunctionUtil.isContain(strUpper, "Windows NT ".toUpperCase())
                && chUaPlatformVersion.contains("4")):
                (FunctionUtil.isContain(strUpper,"Windows NT 4".toUpperCase())))
                || FunctionUtil.isContain(strUpper, "Windows NT".toUpperCase())
                || FunctionUtil.isContain(strUpper, "WinNT".toUpperCase())) {
            return new Text("Windows NT");
        } else if (FunctionUtil.isContain(strUpper, "Win98".toUpperCase())) {
            return new Text("Windows 98");
        } else if (FunctionUtil.isContain(strUpper, "Win95".toUpperCase())) {
            return new Text("Windows 95");
        } else if (FunctionUtil.isContain(strUpper, "Win9x".toUpperCase())) {
            return new Text("Windows 9x");
        } else if (FunctionUtil.isContain(strUpper, "Win31".toUpperCase())) {
            return new Text("Windows 3.1");
        } else if (FunctionUtil.isContain(strUpper, "Windows CE".toUpperCase())) {
            return new Text("Windows CE ");
        } else if (FunctionUtil.isContain(strUpper, "Windows ME".toUpperCase())) {
            return new Text("Windows ME");
        } else if (FunctionUtil.isContain(strUpper, "Windows mo".toUpperCase())) {
            return new Text("Windows mobile");
        } else if (FunctionUtil.isContain(strUpper, "Windows phone".toUpperCase())) {
            return new Text("Windows phone OS");
        } else if (FunctionUtil.isContain(strUpper, "Windows".toUpperCase()) ||
                FunctionUtil.isContain(strOrigin, "Win")) {
            return new Text("Windows ");
        } else if (FunctionUtil.isContain(strUpper, "Mac OS X".toUpperCase())) {
            if (FunctionUtil.isContain(strUpper, "iPhone".toUpperCase())) {
                if (FunctionUtil.isContain(strUpper, "iPod".toUpperCase())) {
                    if (isNewVersion) {
                        return new Text("iOS iPod " + chUaPlatformVersion.replace(".", "_"));
                    } else {
                        return new Text("iOS iPod " + SojGetUaVersion.getUaVersion(strOrigin, strUpper.indexOf("iPhone OS".toUpperCase()) + 9));
                    }
                } else {
                    if (isNewVersion) {
                        return new Text("iOS iPhone " + chUaPlatformVersion.replace(".", "_"));
                    } else {
                        return new Text("iOS iPhone " + SojGetUaVersion.getUaVersion(strOrigin, strUpper.indexOf("iPhone OS".toUpperCase()) + 9));
                    }
                }
            } else if (FunctionUtil.isContain(strUpper, "iPad".toUpperCase())) {
                if (isNewVersion) {
                    return new Text("iOS iPad " + chUaPlatformVersion.replace(".", "_"));
                } else {
                    return new Text("iOS iPad " + SojGetUaVersion.getUaVersion(strOrigin, strUpper.indexOf("CPU OS".toUpperCase()) + 6));
                }
            } else {
                if (isNewVersion) {
                    return new Text("MacOSX " + chUaPlatformVersion.replace(".", "_"));
                } else {
                    return new Text("MacOSX " + SojGetUaVersion.getUaVersion(strOrigin, strUpper.indexOf("Mac OS X".toUpperCase()) + 8));
                }
            }
        } else if (FunctionUtil.isContain(strUpper, "Android".toUpperCase())) {
            if(isNewVersion) {
                return new Text("Android " + chUaPlatformVersion);
            }else{
                return new Text("Android "+SojGetUaVersion.getUaVersion(strOrigin,strUpper.indexOf("Android".toUpperCase())+7));
            }
        } else if (FunctionUtil.isContain(strUpper, "Mac PowerPC".toUpperCase()) || FunctionUtil.isContain(strUpper, "Macintosh".toUpperCase())) {
            return new Text("Mac OS");
        } else if (FunctionUtil.isContain(strUpper, "FreeBSD".toUpperCase())) {
            return new Text("FreeBSD");
        } else if (FunctionUtil.isContain(strUpper, "OpenBSD".toUpperCase())) {
            return new Text("OpenBSD");
        } else if (FunctionUtil.isContain(strUpper, "NetBSD".toUpperCase())) {
            return new Text("NetBSD");
        } else if (FunctionUtil.isContain(strUpper, "BSD".toUpperCase())) {
            return new Text("BSD");
        } else if (FunctionUtil.isContain(strUpper, "Linux".toUpperCase())) {
            if (FunctionUtil.isContain(strUpper, "Ubuntu".toUpperCase())) {
                if (isNewVersion) {
                    return new Text("Ubuntu " + chUaPlatformVersion.replace(".", "_"));
                } else {
                    return new Text("Ubuntu " + SojGetUaVersion.getUaVersion(strOrigin, strUpper.indexOf("Ubuntu".toUpperCase()) + 6));
                }
            } else {
                if (isNewVersion) {
                    return new Text("Linux " + chUaPlatformVersion.replace(".", "_"));
                } else {
                    return new Text("Linux " + SojGetUaVersion.getUaVersion(strOrigin, strUpper.indexOf("Linux".toUpperCase()) + 5));
                }
            }
        } else if (FunctionUtil.isContain(strUpper, "CentOS".toUpperCase())) {
            return new Text("CentOS");
        } else if (FunctionUtil.isContain(strUpper, "Unix".toUpperCase())) {
            return new Text("Linux");
        } else if (FunctionUtil.isContain(strUpper, "SunOS".toUpperCase())) {
            return new Text("SunOS");
        } else if (FunctionUtil.isContain(strUpper, "IRIX".toUpperCase())) {
            return new Text("IRIX");
        } else if (FunctionUtil.isContain(strUpper, "Symbian".toUpperCase())) {
            return new Text("Symbian OS");
        } else if (FunctionUtil.isContain(strUpper, "Nokia".toUpperCase())) {
            return new Text("Nokia");
        } else if (FunctionUtil.isContain(strUpper, "Sony".toUpperCase())) {
            return new Text("SonyEricsson");
        } else if (FunctionUtil.isContain(strUpper, "BeOS".toUpperCase())) {
            return new Text("BeOS");
        } else if (FunctionUtil.isContain(strUpper, "BlackBerry".toUpperCase())) {
            return new Text("BlackBerry");
        } else if (FunctionUtil.isContain(strUpper, "Wii".toUpperCase())) {
            return new Text("Nintendo Wii");
        } else if (FunctionUtil.isContain(strUpper, "Nintendo DS".toUpperCase())) {
            return new Text("Nintendo DS");
        } else if (FunctionUtil.isContain(strUpper, "Nintendo".toUpperCase())) {
            return new Text("Nintendo");
        } else if (FunctionUtil.isContain(strUpper, "playstation".toUpperCase())) {
            return new Text("playstation");
        } else if (FunctionUtil.isContain(strUpper, "webOS".toUpperCase())) {
            return new Text("webOS");
        } else if (FunctionUtil.isContain(strUpper, "Brew".toUpperCase())) {
            return new Text("Brew");
        } else if (FunctionUtil.isContain(strUpper, "palmOS".toUpperCase())) {
            return new Text("palmOS");
        } else if (FunctionUtil.isContain(strUpper, "kindle".toUpperCase())) {
            return new Text("kindle");
        } else if (FunctionUtil.isContain(strUpper, "nook".toUpperCase())) {
            return new Text("nook");
        } else if (FunctionUtil.isContain(strUpper, "samsung".toUpperCase())) {
            return new Text("samsung");
        } else if (FunctionUtil.isContain(strUpper, "eBayi".toUpperCase())) {
            return new Text("iOS eBay App");
        } else if (FunctionUtil.isContain(strUpper, "eBayS".toUpperCase())) {
            return new Text("iOS eBay Selling App");
        } else if (FunctionUtil.isContain(strUpper, "eBayF".toUpperCase())) {
            return new Text("iOS eBay Fashion App");
        } else if (FunctionUtil.isContain(strUpper, "PayPal".toUpperCase())) {
            return new Text("iOS PayPal App");
        } else {
            return new Text("unknown");
        }
    }
}
