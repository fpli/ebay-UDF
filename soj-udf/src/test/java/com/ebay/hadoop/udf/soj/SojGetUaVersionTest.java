package com.ebay.hadoop.udf.soj;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SojGetUaVersionTest {

    @Test
    public void test(){
        assertEquals("4.0",new SojGetUaVersion().evaluate("Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 4.0; KHTE B459418859A38001T821751 )",1));
        assertEquals("5.0",new SojGetUaVersion().evaluate("Mozilla/5.0 (Windows; U; Windows NT 6.1; nl; rv:1.9.2.18) Gecko/20110614 Firefox/3.6.18",1));
        assertEquals("2.8.4rel.1",new SojGetUaVersion().evaluate("Lynx/2.8.4rel.1 libwww-FM/2.14 SSL-MM/1.4.1 OpenSSL/0.9.7a",1));
        assertEquals("5.0",new SojGetUaVersion().evaluate("Mozilla/5.0 (Macintosh; U; PPC Mac OS X; en-US) AppleWebKit/125.4 (KHTML, like Gecko, Safari)",1));
        assertEquals("2.0.2",new SojGetUaVersion().evaluate("eBay/2.0.2 CFNetwork/485.13.9 Darwin/11.0.0",1));
        assertEquals("5.0.0.586",new SojGetUaVersion().evaluate("BlackBerry9700/5.0.0.586 Profile/MIDP-2.1 Configuration/CLDC-1.1 VendorID/142",1));
        assertEquals("9.52",new SojGetUaVersion().evaluate("Opera/9.52 (Windows NT 5.1; U; en)",1));
    }
}
