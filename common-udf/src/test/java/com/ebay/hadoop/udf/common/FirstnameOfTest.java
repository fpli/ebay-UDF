package com.ebay.hadoop.udf.common;


import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by szang on 7/24/17.
 */
public class FirstnameOfTest {
    @Test
    public void evaluate() throws Exception {

        assertEquals(null, new FirstnameOf().evaluate(null));
        assertEquals("", new FirstnameOf().evaluate("    "));
        assertEquals("SeanZang", new FirstnameOf().evaluate("  SeanZang"));
        assertEquals("Sean", new FirstnameOf().evaluate("Sean Zang   "));
        assertEquals("Sean", new FirstnameOf().evaluate("  Sean Jr Zang   "));
        assertEquals("Sean", new FirstnameOf().evaluate("  Sean    Zang Jr"));
    }
    
    @Test
    public void evaluate2() throws Exception {

        assertEquals(null, new FirstnameOf().evaluate(null, " "));
        assertEquals("", new FirstnameOf().evaluate(""," "));
        assertEquals("", new FirstnameOf().evaluate("    "," "));
        assertEquals("SeanZang", new FirstnameOf().evaluate("  SeanZang"," "));
        assertEquals("Sean", new FirstnameOf().evaluate("Sean 	M	Zang   "," "));
        assertEquals("\tSean", new FirstnameOf().evaluate("\tSean 	M	Zang   "," "));
        assertEquals("\tSean", new FirstnameOf().evaluate("\tSean 	M	Zang   "," "));
        assertEquals("Sean", new FirstnameOf().evaluate("\u0008Sean\u0008 	M	Zang   "," "));
    }
}