package com.ebay.hadoop.udf.tokenizer;

import org.junit.Test;

public class FindTokenizerMetadataTest {
  @Test(expected = TokenizerException.class)
  public void testFindTokenizerMetadata() {
    FindTokenizerMetadata findTokenizerMetadata = new FindTokenizerMetadata();
    findTokenizerMetadata.evaluate("shortType", "shortName", "tableName", "columnName");
  }
}
