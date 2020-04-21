package com.ebay.carmel.udf.ebaylib;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HmacSha256StrTest {

    @Test
    public void test() {
        HmacSha256Str hmacSha256Str = new HmacSha256Str();
        assertEquals("B613679A0814D9EC772F95D778C35FC5FF1697C493715653C6C712144292C5AD", hmacSha256Str.evaluate("",""));
        assertEquals(null, hmacSha256Str.evaluate(null, null));
        assertEquals("B4FB2FA4ECA1352820BE1E7AD16FECD109A0DE5236FDBC3B9486EF5E752DF1E4", hmacSha256Str.evaluate("abcdeßàèÀ", "abc"));
        assertEquals("21AB6A4A70D701D2EA49DF05BF9D6EC88191BEF43686294BDC9C75EB2F7E72FA", hmacSha256Str.evaluate("The 728 Club", "poker"));
    }
}
