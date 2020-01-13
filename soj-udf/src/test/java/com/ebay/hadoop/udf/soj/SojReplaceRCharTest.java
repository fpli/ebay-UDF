package com.ebay.hadoop.udf.soj;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Daniel
 */

public class SojReplaceRCharTest {
	
	@Test
    public void evaluate() throws Exception {
		
		assertEquals("??(_)", new SojReplaceRChar().evaluate("ab?cdef?(junk_)", "\\?()_", ""));
		assertEquals("==?====?(====_)", new SojReplaceRChar().evaluate("ab?cdef?(junk_)", "?()_", "="));
		assertEquals("  ?    ?(    _)", new SojReplaceRChar().evaluate("ab?cdef?(junk_)", "?()_", " "));
		assertEquals(null,new SojReplaceRChar().evaluate(null, null, null));
		assertEquals(null,new SojReplaceRChar().evaluate("ABC", null, "="));
		assertEquals(null,new SojReplaceRChar().evaluate(null, "A", "="));
		assertEquals("",new SojReplaceRChar().evaluate("", "A", "-"));
		assertEquals("---",new SojReplaceRChar().evaluate("ABC", "", "-"));

	}

}
