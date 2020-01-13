package com.ebay.hadoop.udf.soj;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Naresh on 11/16/17
 */

public class SojValueInListTest {

	@Test
	public void evaluate() throws Exception {

		assertEquals(null, new SojValueInList().evaluate("abc,defc,hef", ",", "ghd"));
		assertEquals("2", new SojValueInList().evaluate("abc,defc,hef", ",", "defc"));
		assertEquals("3", new SojValueInList().evaluate("abc,defc,hef,", ",", "hef"));
		assertEquals("3", new SojValueInList().evaluate("abc,defc,hef", ",", "hef"));
		assertEquals("1", new SojValueInList().evaluate("hef", ",", "hef"));
		assertEquals(null, new SojValueInList().evaluate("hef", ",", "Hef"));
		assertEquals("1", new SojValueInList().evaluate("abc,abc,def", ",", "abc"));
		assertEquals(null, new SojValueInList().evaluate(null,null,null));
		assertEquals("1", new SojValueInList().evaluate("hef,hef", ",", "hef"));
	}
}
