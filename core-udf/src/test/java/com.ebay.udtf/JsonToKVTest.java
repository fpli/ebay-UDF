package com.ebay.udtf;

import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Unit test for json_to_kv.
 */
public class JsonToKVTest
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue() throws HiveException, IOException {
        assertArrayEquals(new Object[]{"PROCESSOR_NAME", "ADYEN"},new JsonToKV().processInputRecord("{\"PROCESSOR_NAME\":\"ADYEN\",\"PayoutEntity\":\"EBAY_SARL\",\"Instrument\":\"BILLING_EBAY_SARL\",\"EBAY_BOOK_SYMBOL\":\"EBAY_BOOK\",\"PROCESSOR_REQUEST_REF_ID\":\"v2_0285e8e5-7a1d-40e8-9f26-24cd82f18395_2_1\",\"PROCESSOR_REF_ID\":\"853602670234322B\",\"EXTERNAL_TRX_ID_2\":\"16-00014-11604\",\"PayinEntity\":\"EBAY_SARL\",\"EXTERNAL_TRX_ID_1\":\"5004350193\"}").get(0));
        assertArrayEquals(new Object[]{"PayoutEntity", "EBAY_SARL"},new JsonToKV().processInputRecord("{\"PROCESSOR_NAME\":\"ADYEN\",\"PayoutEntity\":\"EBAY_SARL\",\"Instrument\":\"BILLING_EBAY_SARL\",\"EBAY_BOOK_SYMBOL\":\"EBAY_BOOK\",\"PROCESSOR_REQUEST_REF_ID\":\"v2_0285e8e5-7a1d-40e8-9f26-24cd82f18395_2_1\",\"PROCESSOR_REF_ID\":\"853602670234322B\",\"EXTERNAL_TRX_ID_2\":\"16-00014-11604\",\"PayinEntity\":\"EBAY_SARL\",\"EXTERNAL_TRX_ID_1\":\"5004350193\"}").get(1));
        assertArrayEquals(new Object[]{"json error", "json error"},new JsonToKV().processInputRecord("asdvfwrefwrvwrtbert").get(0));
        assertArrayEquals(new Object[]{"json error", "json error"},new JsonToKV().processInputRecord(null).get(0));
    }
}
