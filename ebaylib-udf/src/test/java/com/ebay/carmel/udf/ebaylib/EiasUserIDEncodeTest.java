package com.ebay.carmel.udf.ebaylib;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class EiasUserIDEncodeTest {

    @Test
    public void test() {
        EiasUserIDEncode eiasUserIDEncode = new EiasUserIDEncode();
        assertEquals("nY+sHZ2PrBmdj6wVnY+sEZ2PrA2dj6wJnY+sBZ2Dow+dj6x9nY+seQ==",eiasUserIDEncode.evaluate(123));
        assertEquals("nY+sHZ2PrBmdj6wVnY+sEZ2PrA2dj6wJnYCiCpSBpQ+dj6x9nY+seQ==",eiasUserIDEncode.evaluate(2324343));
    }
}
