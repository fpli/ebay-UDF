package com.ebay.hadoop.udf.soj;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Naresh on 10/18/17
 */

public class SojReplaceCharTest {
	
	@Test
    public void evaluate() throws Exception {
		
		assertEquals("abcdefjunk", new SojReplaceChar().evaluate("ab?cdef?(junk_)", "\\?()_", ""));
		assertEquals("ab=cdef==junk==", new SojReplaceChar().evaluate("ab?cdef?(junk_)", "?()_", "="));
		assertEquals("ab cdef  junk  ", new SojReplaceChar().evaluate("ab?cdef?(junk_)", "?()_", " "));
		assertEquals(null,new SojReplaceChar().evaluate(null, null, null));
		assertEquals(null,new SojReplaceChar().evaluate("ABC", null, "="));
		assertEquals(null,new SojReplaceChar().evaluate(null, "A", "="));
		assertEquals("",new SojReplaceChar().evaluate("", "A", "-"));
		assertEquals("",new SojReplaceChar().evaluate("A", "", "-"));

	}

}
