package com.ebay.hadoop.udf.soj;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author xiaoding
 * @since 2021/11/24 3:32 PM
 */
public class SojGetOsNGTest {

    @Test
    public void testBackward(){

        //test old version
        //test windows releated OS
        assertEquals("Windows NT",new SojGetOsNG().evaluate("Agent=Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 4.0; KHTE B459418859A38001T821751 )").toString());
        assertEquals("Windows NT",new SojGetOsNG().evaluate("Agent=Mozilla/4.0 (compatible; MSIE 6.0; Windows-NT 4.0; KHTE B459418859A38001T821751 )").toString());
        assertEquals("Windows Vista",new SojGetOsNG().evaluate("Agent=Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 6.0; KHTE B459418859A38001T821751 )").toString());
        assertEquals("Windows 7",new SojGetOsNG().evaluate("Agent=Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 6; KHTE B459418859A38001T821751 )").toString());
        assertEquals("Windows 2003",new SojGetOsNG().evaluate("Agent=Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.2; KHTE B459418859A38001T821751 )").toString());
        assertEquals("Windows XP",new SojGetOsNG().evaluate("Agent=Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; KHTE B459418859A38001T821751 )").toString());
        assertEquals("Windows 2000",new SojGetOsNG().evaluate("Agent=Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0; KHTE B459418859A38001T821751 )").toString());
        assertEquals("Windows 2000",new SojGetOsNG().evaluate("Agent=Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5; KHTE B459418859A38001T821751 )").toString());
        assertEquals("Windows NT",new SojGetOsNG().evaluate("Agent=Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 4; KHTE B459418859A38001T821751 )").toString());
        assertEquals("Windows NT",new SojGetOsNG().evaluate("Agent=Mozilla/4.0 (compatible; MSIE 6.0; Windows NT; KHTE B459418859A38001T821751 )").toString());
        assertEquals("Windows 98",new SojGetOsNG().evaluate("Agent=Mozilla/4.0 (compatible; MSIE 6.0; Win98; KHTE B459418859A38001T821751 )").toString());
        assertEquals("Windows 95",new SojGetOsNG().evaluate("Agent=Mozilla/4.0 (compatible; MSIE 6.0; Win95; KHTE B459418859A38001T821751 )").toString());
        assertEquals("Windows 9x",new SojGetOsNG().evaluate("Agent=Mozilla/4.0 (compatible; MSIE 6.0; Win9x; KHTE B459418859A38001T821751 )").toString());
        assertEquals("Windows 3.1",new SojGetOsNG().evaluate("Agent=Mozilla/4.0 (compatible; MSIE 6.0; Win31; KHTE B459418859A38001T821751 )").toString());
        assertEquals("Windows CE ",new SojGetOsNG().evaluate("Agent=Mozilla/4.0 (compatible; MSIE 6.0; Windows CE; KHTE B459418859A38001T821751 )").toString());
        assertEquals("Windows ME",new SojGetOsNG().evaluate("Agent=Mozilla/4.0 (compatible; MSIE 6.0; Windows ME; KHTE B459418859A38001T821751 )").toString());
        assertEquals("Windows mobile",new SojGetOsNG().evaluate("Agent=Mozilla/4.0 (compatible; MSIE 6.0; Windows mobile; KHTE B459418859A38001T821751 )").toString());
        assertEquals("Windows phone OS",new SojGetOsNG().evaluate("Agent=Mozilla/4.0 (compatible; MSIE 6.0; Windows phone; KHTE B459418859A38001T821751 )").toString());
        assertEquals("Windows 7",new SojGetOsNG().evaluate("Agent=Mozilla/5.0 (Windows; U; Windows NT 6.1; nl; rv:1.9.2.18) Gecko/20110614 Firefox/3.6.18").toString());
        assertEquals("Windows XP",new SojGetOsNG().evaluate("Agent=Opera/9.52 (Windows NT 5.1; U; en)").toString());
        //test unknown
        assertEquals("unknown",new SojGetOsNG().evaluate("Agent=Lynx/2.8.4rel.1 libwww-FM/2.14 SSL-MM/1.4.1 OpenSSL/0.9.7a").toString());
        assertEquals("unknown",new SojGetOsNG().evaluate("Agent=eBay/2.0.2 CFNetwork/485.13.9 Darwin/11.0.0").toString());
        //test IOS related
        assertEquals("MacOSX  ",new SojGetOsNG().evaluate("Agent=Mozilla/5.0 (Macintosh; U; PPC Mac OS X; en-US) AppleWebKit/125.4 (KHTML, like Gecko, Safari)").toString());
        assertEquals("iOS iPad 6_0",new SojGetOsNG().evaluate("Agent=Mozilla/5.0 (iPad; CPU OS 6_0 like Mac OS X) AppleWebKit/536.26 (KHTML, like Gecko) Version/6.0 Mobile/10A403 Safari/8536.25").toString());
        assertEquals("iOS iPod 5_0_1",new SojGetOsNG().evaluate("Agent=Mozilla/5.0 (iPod; CPU iPhone OS 5_0_1 like Mac OS X) AppleWebKit/534.46 (KHTML, like Gecko) Version/5.1 Mobile/9A405 Safari/7534.48.3").toString());
        assertEquals("iOS iPhone 6_0",new SojGetOsNG().evaluate("Agent=Mozilla/5.0 (iPhone; CPU iPhone OS 6_0 like Mac OS X) AppleWebKit/536.26 (KHTML, like Gecko) Version/6.0 Mobile/10A403 Safari/8536.25").toString());
        assertEquals("MacOSX 10_14_6",new SojGetOsNG().evaluate("Agent=Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.55 Safari/537.36").toString());
        //test Andriond
        assertEquals("Android 4.1.1",new SojGetOsNG().evaluate("Agent=Mozilla/5.0 (Linux; Android 4.1.1; Nexus 7 Build/JRO03S) AppleWebKit/535.19 (KHTML, like Gecko) Chrome/18.0.1025.166 Safari/535.19").toString());
        // test other cases
        assertEquals("BlackBerry",new SojGetOsNG().evaluate("Agent=BlackBerry9700/5.0.0.586 Profile/MIDP-2.1 Configuration/CLDC-1.1 VendorID/142").toString());


    }

    @Test
    public void testForward(){
        //Test new version with client hints
        //test windows releated OS
        assertEquals("Windows NT",new SojGetOsNG().evaluate("Agent=Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 10.0; KHTE B459418859A38001T821751 )&chUaPlatformVersion=4.0").toString());
        assertEquals("Windows NT",new SojGetOsNG().evaluate("Agent=Mozilla/4.0 (compatible; MSIE 6.0; Windows-NT 10.0; KHTE B459418859A38001T821751 )&chUaPlatformVersion=4.0").toString());
        assertEquals("Windows Vista",new SojGetOsNG().evaluate("Agent=Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 10.0; KHTE B459418859A38001T821751 )&chUaPlatformVersion=6.0").toString());
        assertEquals("Windows 7",new SojGetOsNG().evaluate("Agent=Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 10.0; KHTE B459418859A38001T821751 )&chUaPlatformVersion=6").toString());
        assertEquals("Windows 2003",new SojGetOsNG().evaluate("Agent=Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 10.0; KHTE B459418859A38001T821751 )&chUaPlatformVersion=5.2").toString());
        assertEquals("Windows XP",new SojGetOsNG().evaluate("Agent=Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 10.0; KHTE B459418859A38001T821751 )&chUaPlatformVersion=5.1").toString());
        assertEquals("Windows 2000",new SojGetOsNG().evaluate("Agent=Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 10.0; KHTE B459418859A38001T821751 )&chUaPlatformVersion=5.0").toString());
        assertEquals("Windows 2000",new SojGetOsNG().evaluate("Agent=Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 10.0; KHTE B459418859A38001T821751 )&chUaPlatformVersion=5").toString());
        assertEquals("Windows NT",new SojGetOsNG().evaluate("Agent=Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 10.0; KHTE B459418859A38001T821751 )&chUaPlatformVersion=4").toString());
        assertEquals("Windows NT",new SojGetOsNG().evaluate("Agent=Mozilla/4.0 (compatible; MSIE 6.0; Windows NT; KHTE B459418859A38001T821751 )&chUaPlatformVersion=").toString());
        assertEquals("Windows 98",new SojGetOsNG().evaluate("Agent=Mozilla/4.0 (compatible; MSIE 6.0; Win98; KHTE B459418859A38001T821751 )").toString());
        assertEquals("Windows 95",new SojGetOsNG().evaluate("Agent=Mozilla/4.0 (compatible; MSIE 6.0; Win95; KHTE B459418859A38001T821751 )").toString());
        assertEquals("Windows 9x",new SojGetOsNG().evaluate("Agent=Mozilla/4.0 (compatible; MSIE 6.0; Win9x; KHTE B459418859A38001T821751 )").toString());
        assertEquals("Windows 3.1",new SojGetOsNG().evaluate("Agent=Mozilla/4.0 (compatible; MSIE 6.0; Win31; KHTE B459418859A38001T821751 )").toString());
        assertEquals("Windows CE ",new SojGetOsNG().evaluate("Agent=Mozilla/4.0 (compatible; MSIE 6.0; Windows CE; KHTE B459418859A38001T821751 )").toString());
        assertEquals("Windows ME",new SojGetOsNG().evaluate("Agent=Mozilla/4.0 (compatible; MSIE 6.0; Windows ME; KHTE B459418859A38001T821751 )").toString());
        assertEquals("Windows mobile",new SojGetOsNG().evaluate("Agent=Mozilla/4.0 (compatible; MSIE 6.0; Windows mobile; KHTE B459418859A38001T821751 )").toString());
        assertEquals("Windows phone OS",new SojGetOsNG().evaluate("Agent=Mozilla/4.0 (compatible; MSIE 6.0; Windows phone; KHTE B459418859A38001T821751 )").toString());
        assertEquals("Windows 7",new SojGetOsNG().evaluate("Agent=Mozilla/5.0 (Windows; U; Windows NT 10.0; nl; rv:1.9.2.18) Gecko/20110614 Firefox/3.6.18&chUaPlatformVersion=6.1").toString());
        assertEquals("Windows XP",new SojGetOsNG().evaluate("Agent=Opera/9.52 (Windows NT 10.0; U; en)&chUaPlatformVersion=5.1").toString());
        //test unknown
        assertEquals("unknown",new SojGetOsNG().evaluate("Agent=Lynx/2.8.4rel.1 libwww-FM/2.14 SSL-MM/1.4.1 OpenSSL/0.9.7a").toString());
        assertEquals("unknown",new SojGetOsNG().evaluate("Agent=eBay/2.0.2 CFNetwork/485.13.9 Darwin/11.0.0").toString());
        //test IOS related
        assertEquals("MacOSX  ",new SojGetOsNG().evaluate("Agent=Mozilla/5.0 (Macintosh; U; PPC Mac OS X; en-US) AppleWebKit/125.4 (KHTML, like Gecko, Safari)&chUaPlatformVersion=").toString());
        assertEquals("iOS iPad 6_0",new SojGetOsNG().evaluate("Agent=Mozilla/5.0 (iPad; CPU OS 12_0 like Mac OS X) AppleWebKit/536.26 (KHTML, like Gecko) Version/6.0 Mobile/10A403 Safari/8536.25&chUaPlatformVersion=6.0&chUaPlatformVersion=6.0").toString());
        assertEquals("iOS iPod 5_0_1",new SojGetOsNG().evaluate("Agent=Mozilla/5.0 (iPod; CPU iPhone OS 12_0 like Mac OS X) AppleWebKit/534.46 (KHTML, like Gecko) Version/5.1 Mobile/9A405 Safari/7534.48.3&chUaPlatformVersion=5.0.1").toString());
        assertEquals("iOS iPhone 6_0",new SojGetOsNG().evaluate("Agent=Mozilla/5.0 (iPhone; CPU iPhone OS 12_0 like Mac OS X) AppleWebKit/536.26 (KHTML, like Gecko) Version/6.0 Mobile/10A403 Safari/8536.25&chUaPlatformVersion=6.0").toString());
        assertEquals("MacOSX 10_14_6",new SojGetOsNG().evaluate("Agent=Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.55 Safari/537.36&chUaPlatformVersion=10.14.6").toString());
        //test Andriond
        assertEquals("Android 4.1.1",new SojGetOsNG().evaluate("Agent=Mozilla/5.0 (Linux; Android 10; K) AppleWebKit/535.19 (KHTML, like Gecko) Chrome/18.0.1025.166 Safari/535.19&chUaPlatformVersion=4.1.1").toString());
        // test other cases
        assertEquals("BlackBerry",new SojGetOsNG().evaluate("Agent=BlackBerry9700/5.0.0.586 Profile/MIDP-2.1 Configuration/CLDC-1.1 VendorID/142").toString());

    }

}
