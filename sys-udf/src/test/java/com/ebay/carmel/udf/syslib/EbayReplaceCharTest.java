package com.ebay.carmel.udf.syslib;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class EbayReplaceCharTest implements TestBase {

    @Test
    public void testUdf() throws Exception {
        EbayReplaceChar ebayReplaceChar = new EbayReplaceChar();
        assertEquals("abcdefjunk", ebayReplaceChar.evaluate("ab?cdef?(junk_)","?()_",""));
        assertEquals("ab=cdef==junk==", ebayReplaceChar.evaluate("ab?cdef?(junk_)","?()_","="));
    }
}
