package com.ebay.hadoop.udf.common;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

/**
 * Created by szang on 7/31/17.
 */
public class IsAsciiTest {
    @Test
    public void evaluate() throws Exception {
        assertEquals(0, new IsAscii().evaluate("abs"));
        assertEquals(0, new IsAscii().evaluate("    !@#$%^&*( ABCDE%^   #$&*("));
        assertEquals(4, new IsAscii().evaluate("abs™ "));
        assertEquals(1, new IsAscii().evaluate("哈哈"));
        assertEquals(2, new IsAscii().evaluate(" ™£¢∞"));
        assertEquals(20, new IsAscii().evaluate("~!@#$%^&*()_+{ }|: •"));
        assertEquals(0, new IsAscii().evaluate(null));
// If you run the following, you will see that all negative binaries map to the same char in UTF-8 Charset.
// If you convert the char back to byte[], you will see that it its length is 3, different from what it was.
//        for (int i=127; i >= -128; i--) {
//            byte[] bb = new byte[] {(byte)i};
//            System.out.println("byte[] length:" + bb.length);
//            System.out.println("byte[0]:" + bb[0]);
//            String s = new String(bb);
//            System.out.println(s);
//            byte[] newbb = s.getBytes();
//            System.out.println("new_byte[] length:" + newbb.length);
//            System.out.println("new_byte[0]:" + newbb[0]);
//            System.out.println("---------------------------------");
//        }
    }
}