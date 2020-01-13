package com.ebay.hadoop.udf.soj;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Naresh on 10/18/17
 */

public class SojGetBrowserVersionTest {

	@Test
	public void evaluate() throws Exception {

		assertEquals("6.x", new SojGetBrowserVersion().evaluate("Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; .NET CLR 2.0.50727)"));
		assertEquals("NULL UserAgent", new SojGetBrowserVersion().evaluate(""));
		assertEquals("NULL UserAgent", new SojGetBrowserVersion().evaluate(null));
		assertEquals("1.0", new SojGetBrowserVersion().evaluate("Mozilla/5.0 (iPad; CPU OS 6_1_3 like Mac OS X) AppleWebKit/536.26 (KHTML, like Gecko) Version/6.0 Mobile/10B329 Safari/8536.25"));
		
	}

}
