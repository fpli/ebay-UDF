package com.ebay.hadoop.udf.tokenizer;

import java.util.Collections;
import org.junit.Test;

public class BatchDeTokenizeTest extends AbstractTokenizerTest {
  @Test(expected = TokenizerException.class)
  public void testBatchDeTokenize() {
    BatchDeTokenize batchDeTokenize = new BatchDeTokenize();
    batchDeTokenize.evaluate("email-tokenizer-2way", Collections.singletonList("token"));
  }
}
