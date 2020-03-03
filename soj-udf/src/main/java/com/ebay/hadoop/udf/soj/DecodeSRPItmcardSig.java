package com.ebay.hadoop.udf.soj;

import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;

public class DecodeSRPItmcardSig extends UDF {
    private SOJURLDecodeEscapeHive sojurlDecodeEscape;
    private SojListGetValByIdx sojListGetValByIdx;
    private SojGetBase64EncodedBitsSet sojGetBase64EncodedBitsSet;
    private SojValueInList sojValueInList;

    public DecodeSRPItmcardSig() {
        super();
        sojurlDecodeEscape = new SOJURLDecodeEscapeHive();
        sojListGetValByIdx = new SojListGetValByIdx();
        sojGetBase64EncodedBitsSet = new SojGetBase64EncodedBitsSet();
        sojValueInList = new SojValueInList();
    }

    public IntWritable evaluate(Text itmlt48sig, int itemIndex, int bitPosition) {

        Text urlDecoded = sojurlDecodeEscape.evaluate(itmlt48sig, "%");

        Text val = sojListGetValByIdx.evaluate(urlDecoded, ",", itemIndex);

        if (val == null) {
            return new IntWritable(0);
        }

        String strVal = val.toString();

        int totalCount = strVal.length() * 6;

        int countEqual = (strVal.length() - strVal.replaceAll("=", "").length()) * 8;

        int bitPos = totalCount - countEqual - 1 - bitPosition;
        Text bitSet = sojGetBase64EncodedBitsSet.evaluate(strVal);
        if (bitSet == null) {
            return new IntWritable(0);
        }

        String ret = sojValueInList.evaluate(bitSet.toString(), ",", String.valueOf(bitPos));
        if (ret == null) {
            return new IntWritable(0);
        } else {
            return new IntWritable(1);
        }

    }
}
