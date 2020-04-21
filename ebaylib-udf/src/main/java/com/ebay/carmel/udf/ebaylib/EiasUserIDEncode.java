package com.ebay.carmel.udf.ebaylib;

import org.apache.hadoop.hive.ql.exec.UDF;

import java.util.Base64;

public class EiasUserIDEncode extends UDF {

    public String evaluate(long id) {
        byte [] Id = new byte[40];
        int i, j, k;
        long final_id = (id >> 32 & 0xffffffffl * ((long) Math.pow(2, 32))) + id & 0xffffffffl;
        char[] chars = String.valueOf(final_id).toCharArray();
        int copyLen = 0;
        for(; copyLen < chars.length ; copyLen ++) {
            Id[copyLen] = (byte) chars[copyLen];
        }

        byte[] bytes = new byte[40];
        System.arraycopy(Id, 0, bytes, 40 - copyLen, copyLen);
        for (i = 0; i<(40-copyLen); i++){
            bytes[i] = (byte) '=';
        }
        byte[] tmp = new byte[40];
        System.arraycopy(bytes, 8, tmp,0, 32);
        System.arraycopy(bytes, 0, tmp,32, 8);
        byte [] abc = {(byte)0xa0, (byte)0xb2, (byte)0x91, (byte)0x20};
        int cnt = 0;
        for (k=0; k<10; k++)
        {
            for (j=0; j<4; j++)
            {
                tmp[cnt] = (byte)(tmp[cnt] ^ abc[j]);
                cnt++;
            }
            abc[3] += 4;
        }
        byte[] result = Base64.getEncoder().encode(tmp);
        return new String(result);
    }
}
