package com.ebay.cassini.log.realtime.udf;

import java.io.ByteArrayOutputStream;

import org.apache.commons.codec.binary.Base64;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.Text;

/*
 * Code is a copy of https://github.corp.ebay.com/SBE-SRE/cassini-realtime/blob/master/hive-udf/src/main/java/com/ebay/cassini/log/realtime/udf/ToBase64.java
 * Author: Jeff jingfwang
 */
public class ToBase64 extends UDF {

    private final transient Text result = new Text();

    public Text evaluate(BytesWritable b){
      if (b == null) {
        return null;
      }
      byte[] bytes = new byte[b.getLength()];
      System.arraycopy(b.getBytes(), 0, bytes, 0, b.getLength());
      result.set(toBase64(bytes));
      return result;
    }

    private String toBase64(byte[] blob) {
        byte[] escapedBlob = unescapeBlob(blob);
        return new String(Base64.encodeBase64(escapedBlob, false));
    }

    private byte[] unescapeBlob(byte[] blob) {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        boolean skip = false;

        for (int i = 0; i < blob.length; i++) {
            if (!skip) {
                byte currentChar = blob[i];
                if (i + 1 < blob.length && currentChar == 0x5C) {
                    if (blob[i + 1] == 0x6E) {
                        buffer.write(0xA);
                        skip = true;
                    } else if (blob[i + 1] == 0x5C) {
                        buffer.write(0x5C);
                        skip = true;
                    } else {
                        buffer.write(currentChar);
                    }
                } else {
                    buffer.write(currentChar);
                }
            } else {
                skip = false;
            }
        }
        return buffer.toByteArray();
    }
}
