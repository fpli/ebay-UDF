package com.ebay.hadoop.udf.soj;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GetUrlParamsTest {
    @Test
    public void evaluate() throws Exception {
        assertEquals("param", new GetUrlParams().evaluate("http://test.com?param"));
        assertEquals("_nkw=ebay+uk&l1=2&sacat=456", new GetUrlParams().evaluate("http://shop.ebay.com/xyz/abc?_nkw=ebay+uk&l1=2&sacat=456"));
        assertEquals("LH_PrefLoc=0&LH_BIN=1&LH_IncludeSIF=1&LH_GIFAST=1&LH_Price=10..49.99%40c&Condition=New%2520In%2520Box&Type=Airplanes|Helicopters&_catref=1&_dmpt=Radio_Control_Vehicles&_fln=1&_fsct=&_mPrRngCbx=1&_trksid=p3286.c0.m282&_qi=RTM653346", new GetUrlParams().evaluate("http://toys.shop.ebay.com/Battery-Powered-/2563/i.html?LH_PrefLoc=0&LH_BIN=1&LH_IncludeSIF=1&LH_GIFAST=1&LH_Price=10..49.99%40c&Condition=New%2520In%2520Box&Type=Airplanes|Helicopters&_catref=1&_dmpt=Radio_Control_Vehicles&_fln=1&_fsct=&_mPrRngCbx=1&_trksid=p3286.c0.m282&_qi=RTM653346"));

        assertEquals(null, new GetUrlParams().evaluate("http:/"));
        assertEquals(null, new GetUrlParams().evaluate(""));
        assertEquals(null, new GetUrlParams().evaluate(null));
        assertEquals(null, new GetUrlParams().evaluate(" "));
    }
}