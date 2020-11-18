package com.ebay.hadoop.udf.hadoop;

import org.junit.Assert;
import org.junit.Test;

public class UgiExtractTest {

    @Test
    public void testUgiExtract(){

        UgiExtract extract  = new UgiExtract();
        Assert.assertEquals(extract.evaluate("b_engage/rnodevour.vip.hadoop.ebay.com@PROD.EBAY.COM (auth:TOKEN)"),"b_engage");
        Assert.assertEquals(extract.evaluate("b_touchstone@PROD.EBAY.COM (auth:KERBEROS)"),"b_touchstone");
        Assert.assertEquals(extract.evaluate("b_tns (auth:TOKEN) via hdfs/hms-asset00347307.stratus.lvs.ebay.com@PROD.EBAY.COM (auth:TOKEN)"), "b_tns");
    }
}
