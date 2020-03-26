package com.ebay.carmel.udf.syslib;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Sha256Utf16StrTest implements  TestBase{

    @Test
    public void testUdf() throws Exception {
        Sha256Utf16Str sha256Utf16 = new Sha256Utf16Str();
        assertEquals("0xE3B0C44298FC1C149AFBF4C8996FB92427AE41E4649B934CA495991B7852B855", sha256Utf16.evaluate(""));
        assertEquals("0xA665A45920422F9D417E4867EFDC4FB8A04A1F3FFF1FA07E998E86F7F7A27AE3", sha256Utf16.evaluate("123"));
        assertEquals(null, sha256Utf16.evaluate(null));
        assertEquals("0x8CD70BAD4F1B03DDC07571E380C5D302EA0D569D8B9399BEC9FDB46E6570BDB1", sha256Utf16.evaluate("2878"));
    }
}
