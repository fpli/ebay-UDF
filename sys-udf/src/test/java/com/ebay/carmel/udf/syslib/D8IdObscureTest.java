package com.ebay.carmel.udf.syslib;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class D8IdObscureTest implements TestBase {
    @Test
    public void testUdf() throws Exception {
        D8IdObscure d8IdObscure = new D8IdObscure();
        assertEquals(43188348251l, d8IdObscure.evaluate( 54));
        assertEquals(43188225212l, d8IdObscure.evaluate( 123345));
        assertEquals(43188225215l, d8IdObscure.evaluate( 123346));
        assertEquals(18698243441026395l, d8IdObscure.evaluate( 18698286152024118l));
    }
}
