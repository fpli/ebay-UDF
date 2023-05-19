package com.ebay.hadoop.udf.tokenizer;

import java.util.Collections;
import org.junit.Test;

public class BatchTokenizeTest extends AbstractTokenizerTest {
  @Test(expected = TokenizerException.class)
  public void testBatchTokenize() {
    BatchTokenize batchTokenize = new BatchTokenize();
    batchTokenize.evaluate("email-tokenizer-2way", Collections.singletonList("iuyjhue@kru.khe"));
  }
}
