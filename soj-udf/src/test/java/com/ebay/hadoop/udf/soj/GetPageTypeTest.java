package com.ebay.hadoop.udf.soj;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GetPageTypeTest {

	@Test
	public void test1() {
		GetPageType obj = new GetPageType();
		Integer x = 5;
		assertEquals(x, obj.evaluate("290499010052", "QhAZMKAAAABAAAIANwg*", 0, 4340, 5, 0));
	}

	@Test
	public void test2() {
		GetPageType obj = new GetPageType();
		Integer x = -1;
		assertEquals(x, obj.evaluate("290499010052", "QhAZMKAAAABAAAIANwg*", 0, 4340, -1, 0));
	}
}
//SOJGetPageType.soj_get_page_type("290499010052", "QhAZMKAAAABAAAIANwg*", 0, 4340, -1, 0 ); 