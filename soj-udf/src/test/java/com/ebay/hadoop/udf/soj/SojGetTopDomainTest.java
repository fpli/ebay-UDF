package com.ebay.hadoop.udf.soj;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class SojGetTopDomainTest {
    @Test
    public void test() {
        assertEquals("ebay",new SojGetTopDomain().evaluate("www.ebay.co.uk"));
        assertEquals("ebay",new SojGetTopDomain().evaluate("www.ebay.com"));
        assertEquals("ebay",new SojGetTopDomain().evaluate("www.ebay.jp"));
        assertEquals("192.168.15.10",new SojGetTopDomain().evaluate("192.168.15.10"));
        assertEquals("192.168.15.125",new SojGetTopDomain().evaluate("192.168.15.125"));


        assertEquals("ebay.n",new SojGetTopDomain().evaluate("ebay.n"));
        assertEquals("ebay",new SojGetTopDomain().evaluate("ebay.nl"));
        assertEquals("ebay.22",new SojGetTopDomain().evaluate("ebay.22"));
        assertEquals("ebay",new SojGetTopDomain().evaluate("ebay.n2"));
        assertEquals("ebay",new SojGetTopDomain().evaluate("ebay.com"));
        assertEquals("ebay.cot",new SojGetTopDomain().evaluate("ebay.cot"));

        assertEquals("www.ebay.n",new SojGetTopDomain().evaluate("www.ebay.n"));
        assertEquals("www.ebay.22",new SojGetTopDomain().evaluate("www.ebay.22"));
        assertEquals("ebay",new SojGetTopDomain().evaluate("www.ebay.com"));
        assertEquals("www.ebay.abc",new SojGetTopDomain().evaluate("www.ebay.abc"));
        assertEquals("ebay",new SojGetTopDomain().evaluate("www.ebay.com.ab"));

        assertNull(new SojGetTopDomain().evaluate(null));
    }



    @Test
    public void ebayVerifyGTLDTest(){
        assertEquals(0,new SojGetTopDomain().ebayVerifyGTLD("abcdefg"));
        assertEquals(0,new SojGetTopDomain().ebayVerifyGTLD("abc"));
        assertEquals(1,new SojGetTopDomain().ebayVerifyGTLD("com"));
        assertEquals(1,new SojGetTopDomain().ebayVerifyGTLD("COM"));
        assertEquals(1,new SojGetTopDomain().ebayVerifyGTLD("cat"));
        assertEquals(1,new SojGetTopDomain().ebayVerifyGTLD("coop"));
        assertEquals(1,new SojGetTopDomain().ebayVerifyGTLD("co"));
        assertEquals(1,new SojGetTopDomain().ebayVerifyGTLD("net"));
        assertEquals(1,new SojGetTopDomain().ebayVerifyGTLD("name"));
        assertEquals(1,new SojGetTopDomain().ebayVerifyGTLD("ne"));
        assertEquals(1,new SojGetTopDomain().ebayVerifyGTLD("edu"));
        assertEquals(1,new SojGetTopDomain().ebayVerifyGTLD("gov"));
        assertEquals(1,new SojGetTopDomain().ebayVerifyGTLD("gr"));
        assertEquals(1,new SojGetTopDomain().ebayVerifyGTLD("aero"));
        assertEquals(1,new SojGetTopDomain().ebayVerifyGTLD("arpa"));
        assertEquals(1,new SojGetTopDomain().ebayVerifyGTLD("asia"));
        assertEquals(1,new SojGetTopDomain().ebayVerifyGTLD("biz"));
        assertEquals(1,new SojGetTopDomain().ebayVerifyGTLD("bc"));
        assertEquals(1,new SojGetTopDomain().ebayVerifyGTLD("bd"));
        assertEquals(1,new SojGetTopDomain().ebayVerifyGTLD("info"));
        assertEquals(1,new SojGetTopDomain().ebayVerifyGTLD("int"));
        assertEquals(1,new SojGetTopDomain().ebayVerifyGTLD("mil"));
        assertEquals(1,new SojGetTopDomain().ebayVerifyGTLD("mobi"));
        assertEquals(1,new SojGetTopDomain().ebayVerifyGTLD("museu"));
        assertEquals(1,new SojGetTopDomain().ebayVerifyGTLD("org"));
        assertEquals(1,new SojGetTopDomain().ebayVerifyGTLD("or"));
        assertEquals(1,new SojGetTopDomain().ebayVerifyGTLD("pro"));
        assertEquals(1,new SojGetTopDomain().ebayVerifyGTLD("ptd"));
        assertEquals(1,new SojGetTopDomain().ebayVerifyGTLD("plc"));
        assertEquals(1,new SojGetTopDomain().ebayVerifyGTLD("tel"));
        assertEquals(1,new SojGetTopDomain().ebayVerifyGTLD("travel"));

    }
}