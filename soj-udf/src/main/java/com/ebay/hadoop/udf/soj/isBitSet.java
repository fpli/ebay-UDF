package com.ebay.hadoop.udf.soj;

import com.ebay.sojourner.common.sojlib.Base64Ebay;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;

public class isBitSet extends UDF {

    public Boolean evaluate( final Text content, int position) {
        if (content == null || position <0) {
            return false;
        }

        byte[] flagBytes = Base64Ebay.decode(content.toString(), false);
        if (flagBytes == null) {
            return false;
        } else {
            if (isBitSet(flagBytes, 0) != 0) {
               return true;
            }

        }
        return false;
    }
    private static int isBitSet(byte[] decodedBuckets, int position) {
        int bucket = position / 8;
        if (bucket < decodedBuckets.length) {
            int actualFlag = decodedBuckets[bucket];
            int bitLocation = position % 8;
            int bitValue = actualFlag >> 7 - bitLocation & 1;
            return bitValue;
        }
        return 0;
    }
}