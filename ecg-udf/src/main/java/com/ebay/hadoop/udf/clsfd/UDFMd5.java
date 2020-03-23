package com.ebay.hadoop.udf.clsfd;


import org.apache.commons.codec.binary.Hex;
import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.Text;
import org.apache.log4j.Logger;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Description(name="md5", value="_FUNC_(str or bin) - Calculates an MD5 128-bit checksum for the string or binary.", extended="The value is returned as a string of 32 hex digits, or NULL if the argument was NULL.\nExample:\n  > SELECT _FUNC_('ABC');\n  '902fbdd2b1df0c4f70b4a5d23525e932'\n  > SELECT _FUNC_(binary('ABC'));\n  '902fbdd2b1df0c4f70b4a5d23525e932'")
public class UDFMd5 extends UDF
{
    private static final Logger logger = Logger.getLogger(UDFMd5.class);
    private final Text result = new Text();
    private final MessageDigest digest;

    public UDFMd5() {
        try {
            this.digest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            logger.error("runtime exception while get instance of md5", new RuntimeException(e));
            throw new RuntimeException(e);
        }
    }

    public Text evaluate(Text n) {
        if (n == null) {
            logger.warn("input is null");
            return null;
        }

        this.digest.reset();
        this.digest.update(n.getBytes(), 0, n.getLength());
        byte[] md5Bytes = this.digest.digest();
        String md5Hex = Hex.encodeHexString(md5Bytes);

        this.result.set(md5Hex);
        return this.result;
    }

    public Text evaluate(BytesWritable b){
        if (b == null) {
            logger.warn("input is null");
            return null;
        }

        this.digest.reset();
        this.digest.update(b.getBytes(), 0, b.getLength());
        byte[] md5Bytes = this.digest.digest();
        String md5Hex = Hex.encodeHexString(md5Bytes);

        this.result.set(md5Hex);
        return this.result;
    }
}
