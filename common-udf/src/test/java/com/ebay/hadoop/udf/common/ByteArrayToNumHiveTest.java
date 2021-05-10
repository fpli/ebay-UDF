package com.ebay.hadoop.udf.common;

import org.apache.hadoop.io.BytesWritable;
import org.junit.Test;

public class ByteArrayToNumHiveTest {

  @Test
  public void test() {
    ByteArrayToNumHive test = new ByteArrayToNumHive();
    byte[] bytes = new byte[64];
    for(int i = 0; i < 64; i++){
      bytes[i] = (byte) Math.round(Math.random());
    }
    BytesWritable bw = new BytesWritable(bytes);
    Object s = test.evaluate(bw);
    System.out.println(bytes[31]+" "+s + " "+s.getClass());
    System.out.println(ByteArrayToNum.getInt(bytes));
    System.out.print(ByteArrayToNum.getLong(bytes));
  }
}
