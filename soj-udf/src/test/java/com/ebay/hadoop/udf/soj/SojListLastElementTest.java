package com.ebay.hadoop.udf.soj;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Naresh on 10/18/17
 */

public class SojListLastElementTest {

	 @Test
	    public void evaluate() throws Exception {
		 
		 assertEquals("hef",new SojListLastElement().evaluate("abc,defc,hef",","));
		 assertEquals(null, new SojListLastElement().evaluate("abc,defc,",","));
		 assertEquals(null, new SojListLastElement().evaluate("abc,defc,hef,,,",","));
		 assertEquals("90", new SojListLastElement().evaluate("91.121.6.90","."));
		 assertEquals(null, new SojListLastElement().evaluate(null,null));
		 
		 
	 }
}
