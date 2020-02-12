package com.ebay.carmel.udf.syslib;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class Sha256LationTest {

    @Test
    public void testUdf() throws Exception {
        Sha256Latin sha256Latin = new Sha256Latin();
        assertEquals("0AAFAD1BF6FF270D8E96CB5573707F69C2B5EF4E302F4A539C25AD59A3B08885",
                sha256Latin.evaluate("abcdeßàèÀ"));
        assertEquals("E3B0C44298FC1C149AFBF4C8996FB92427AE41E4649B934CA495991B7852B855",
                sha256Latin.evaluate(""));
        assertEquals(null,
                sha256Latin.evaluate(null));
    }
}
