package com.ebay.hadoop.udf.soj;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SojListElementCountTest {

	 @Test
	    public void evaluate() throws Exception {
		 assertEquals(null, new SojListElementCount().evaluate(null,","));
		 assertEquals(null, new SojListElementCount().evaluate("abc,defc,hef,,,",null));
		 assertEquals(Integer.valueOf(1), new SojListElementCount().evaluate("abc,defc,hef,,,",""));
		 assertEquals(Integer.valueOf(0), new SojListElementCount().evaluate("",","));
		 assertEquals(Integer.valueOf(1), new SojListElementCount().evaluate("abc",","));
		 assertEquals(Integer.valueOf(2), new SojListElementCount().evaluate("abc,",","));
		 assertEquals(Integer.valueOf(2), new SojListElementCount().evaluate(",abc",","));
		 assertEquals(Integer.valueOf(2), new SojListElementCount().evaluate("abc,def",","));
		 assertEquals(Integer.valueOf(2), new SojListElementCount().evaluate(",",","));
	 }
}
