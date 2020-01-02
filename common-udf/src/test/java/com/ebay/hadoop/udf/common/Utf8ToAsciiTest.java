package com.ebay.hadoop.udf.common;


import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by szang on 8/3/17.
 */
public class Utf8ToAsciiTest {
    @Test
    public void evaluate() throws Exception {
        assertEquals(null, new Utf8ToAscii().evaluate(null));
        assertEquals("abc123", new Utf8ToAscii().evaluate("abc123"));

        // try throwing exception when there is non-ASCII characters.
        try {
            new Utf8ToAscii().evaluate("Ω abc123");
        } catch (Exception e) {
            assertEquals("ERROR: Non-ASCII character encountered when converting Ω abc123", e.getMessage());
        }
    }
/*
    @Test
    public void evaluate1() throws Exception {

        // mode 0 : same as the above evaluate()
        assertEquals(null, new Utf8ToAscii().evaluate(null, 0));
        assertEquals("abc123", new Utf8ToAscii().evaluate("abc123", 0));

        try {
            new Utf8ToAscii().evaluate("Ω abc123", 0);
        } catch (Exception e) {
            assertEquals("ERROR: Non-ASCII character encountered when converting Ω abc123", e.getMessage());
        }

        // mode 1 : convert non-ASCII to default replacement character 0x1A
        assertEquals(null, new Utf8ToAscii().evaluate(null, 1));
        assertEquals("abc123", new Utf8ToAscii().evaluate("abc123", 1));
        assertEquals((char) 0x1A + " abc123", new Utf8ToAscii().evaluate("Ω abc123", 1));
    }
*/
    /*
    @Test
    public void evaluate2() throws Exception {

        assertEquals(null, new Utf8ToAscii().evaluate(null, 1, "@"));
        assertEquals("abc123", new Utf8ToAscii().evaluate("abc123", 1, "@"));
        assertEquals("@ abc123", new Utf8ToAscii().evaluate("Ω abc123", 1, "@"));
        assertEquals("@ abc @ 123", new Utf8ToAscii().evaluate("Ω abc Ω 123", 1, "@"));
        assertEquals(" abc123", new Utf8ToAscii().evaluate("Ω abc123", 1, ""));
        assertEquals("@@ abc123", new Utf8ToAscii().evaluate("Ω abc123", 1, "@@"));
    }
*/
    @Test
    public void evaluate3() throws Exception {
        assertEquals(null, new Utf8ToAscii().evaluate(null, "@"));

        // exception when subChar is null
        try {
            new Utf8ToAscii().evaluate("abc123", null);
        } catch (Exception e) {
            assertEquals("ERROR: replacement character must not be 'null'", e.getMessage());
        }

        assertEquals(" abc123", new Utf8ToAscii().evaluate("Ω abc123", ""));
        assertEquals("@@ abc123", new Utf8ToAscii().evaluate("Ω abc123", "@@"));
        assertEquals("@ 123 @ abc &&&", new Utf8ToAscii().evaluate("Ω 123 Ω abc &&&", "@"));


    }

}