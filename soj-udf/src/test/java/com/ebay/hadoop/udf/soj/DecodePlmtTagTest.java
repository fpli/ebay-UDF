package com.ebay.hadoop.udf.soj;

import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class DecodePlmtTagTest {
    @Test
    public void test() {

        String s ="{\"pg\":2047675,\"pi\":[{\"id\":100938,\"ci\":-9,\"ri\":\"a571c82b62bf4fd88ea730c88d9b8e56\",\"et\":[],\"ea\":[{\"ii\":[{\"i\":\"2l67n5cp\",\"m\":null,\"so\":null,\"p\":38912,\"f\":1}],\"ai\":\"999001\",\"av\":\"null\",\"mi\":0,\"mx\":0,\"oc\":0,\"as\":0,\"ao\":1}],\"ct\":1},{\"id\":100727,\"ci\":-9,\"ri\":\"da4081f643d04b57981a2aac1df5d75f\",\"et\":[],\"ea\":[{\"ii\":[{\"i\":\"2l67n5cp\",\"m\":null,\"so\":null,\"p\":38912,\"f\":1}],\"ai\":\"999001\",\"av\":\"null\",\"mi\":0,\"mx\":0,\"oc\":0,\"as\":0,\"ao\":1}],\"ct\":1},{\"id\":100565,\"ci\":-9,\"ri\":\"d9e1e3524ce54dd9add290a85e95d00b\",\"et\":[],\"ea\":[{\"ii\":[{\"i\":\"2l67n5cp\",\"m\":null,\"so\":null,\"p\":38912,\"f\":1}],\"ai\":\"999001\",\"av\":\"null\",\"mi\":0,\"mx\":0,\"oc\":0,\"as\":0,\"ao\":1}],\"ct\":1},{\"id\":100567,\"ci\":-9,\"ri\":\"68ac12a7751940f1b8935ba2e1bde5eb\",\"et\":[],\"ea\":[{\"ii\":[{\"i\":\"2l67n5cp\",\"m\":null,\"so\":null,\"p\":38912,\"f\":1}],\"ai\":\"999001\",\"av\":\"null\",\"mi\":0,\"mx\":0,\"oc\":0,\"as\":0,\"ao\":1}],\"ct\":1},{\"id\":100562,\"ci\":-9,\"ri\":\"f8c26f40813b40c6b647a12b06ad3200\",\"et\":[],\"ea\":[{\"ii\":[{\"i\":\"2l67n5cp\",\"m\":null,\"so\":null,\"p\":38912,\"f\":1}],\"ai\":\"999001\",\"av\":\"null\",\"mi\":0,\"mx\":0,\"oc\":0,\"as\":0,\"ao\":1}],\"ct\":1},{\"id\":100918,\"ci\":-9,\"ri\":\"320e3b6decc94329ac0802c245fbae8b\",\"et\":[],\"ea\":[{\"ii\":[{\"i\":\"2l67n5cp\",\"m\":null,\"so\":null,\"p\":38912,\"f\":1}],\"ai\":\"999001\",\"av\":\"null\",\"mi\":0,\"mx\":0,\"oc\":0,\"as\":0,\"ao\":1}],\"ct\":1},{\"id\":100919,\"ci\":-9,\"ri\":\"1c6c12309c864876a727646ba2746acc\",\"et\":[],\"ea\":[{\"ii\":[{\"i\":\"2l67n5cp\",\"m\":null,\"so\":null,\"p\":38912,\"f\":1}],\"ai\":\"999001\",\"av\":\"null\",\"mi\":0,\"mx\":0,\"oc\":0,\"as\":0,\"ao\":1}],\"ct\":1},{\"id\":100916,\"ci\":-9,\"ri\":\"e831cd1379bb44a99174bde15191dc7f\",\"et\":[],\"ea\":[{\"ii\":[{\"i\":\"2l67n5cp\",\"m\":null,\"so\":null,\"p\":38912,\"f\":1}],\"ai\":\"999001\",\"av\":\"null\",\"mi\":0,\"mx\":0,\"oc\":0,\"as\":0,\"ao\":1}],\"ct\":1},{\"id\":100917,\"ci\":-9,\"ri\":\"b1c097bad5a7488c84a5c0ec176f545e\",\"et\":[],\"ea\":[{\"ii\":[{\"i\":\"2l67n5cp\",\"m\":null,\"so\":null,\"p\":38912,\"f\":1}],\"ai\":\"999001\",\"av\":\"null\",\"mi\":0,\"mx\":0,\"oc\":0,\"as\":0,\"ao\":1}],\"ct\":1},{\"id\":100568,\"ci\":-9,\"ri\":\"e3c387bedb2844a096eee8ecf2238a19\",\"et\":[],\"ea\":[{\"ii\":[{\"i\":\"2l67n5cp\",\"m\":null,\"so\":null,\"p\":38912,\"f\":1}],\"ai\":\"999001\",\"av\":\"null\",\"mi\":0,\"mx\":0,\"oc\":0,\"as\":0,\"ao\":1}],\"ct\":1}],\"bit\":\"00\"}";
        assertEquals(s, new DecodePlmtTag().evaluate("LAgAAB%252BLCAAAAAAAAADNlE1u3DAMhe%252BitQuQkiiRc5WiC4qiigGmmUGTFgWC3L10knbhE3j1aFuP1gf%252BvKbH93TJUHvrtKXHNV2%252BvqbrTBcEkMJbsnj1Rbb0MzQpdTTOo%252BWx6prMrr2AMU8Z7NTSlvwlUnwL1Y9UnxnDnG%252BtP5E94tCPdHn6dbtt6fn%252BL3qkS2HBvKUVP3%252BLDLqbRAQAw6K%252F42k%252FutvjC4T8eZe7vYs%252Bf8j9024ve7D9p%252Bm5H2imVmBcrZYJdVAXRs2qhnPR7LTOTEONjjTi6IVyNac6p%252BicWUCZXGgCjHPTHGvTOOqQtXdCqbBwsBQamh3HdPKT0%252BQDzWLLbe3dVkYFa6PVrpgHNJ0lA5yZRvC4BeLGXkabbia1ZFEDhmy50hrqfOraCMqBBq1FpxUQ41a5N41F0WqLXuu1qdm5adqBxrmgTSxdxqhVRbDXmBiMMcJp%252FdQ7Le56oBloIH3oJO2V2bgqGbhhb4sq%252BZlpqB3nxosV7sPnyBy1AWnuzm4r58KKcj6aCMY1ohQr6u0vZSiPKywIAAA%253D"));
        assertNull(new DecodePlmtTag().evaluate(null));
        assertEquals("",new DecodePlmtTag().evaluate("abcde"));
    }
}