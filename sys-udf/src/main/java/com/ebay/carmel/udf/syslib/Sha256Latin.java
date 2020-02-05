package com.ebay.carmel.udf.syslib;

import org.apache.hadoop.hive.ql.exec.UDF;
import java.security.MessageDigest;
import static java.nio.charset.StandardCharsets.UTF_8;

public class Sha256Latin extends UDF {

    public String evaluate(String latin)  throws Exception {
        if(null == latin) return null;
        char[] latinChars = latin.toCharArray();
        int flag = 0;
        int length = latinChars.length;
        while (length > 0 && flag == 0) {
            if (latinChars[length - 1] == 32) {
                length --;
            } else {
                flag = 1;
            }
        }
        char[] tmpResultUtfChars = new char[length*2+1];
        int idx = 0;
        for(int i = 0 ; i < length ; i++) {
            if (latinChars[i] < 128) {
                tmpResultUtfChars[idx++] = latinChars[i];
            } else if (latinChars[i]  < 192) {
                tmpResultUtfChars[idx++] = 194;
                tmpResultUtfChars[idx++] = latinChars[i];
            } else {
                tmpResultUtfChars[idx++] = 195;
                tmpResultUtfChars[idx++] = (char)(latinChars[i] - 64);
            }
        }
        char[] utfChars = new char[idx];
        System.arraycopy(tmpResultUtfChars, 0, utfChars, 0, idx);
        byte[] bytes = new byte[idx];
        for(int i = 0 ;i < idx ;i++) {
            bytes[i] = (byte) utfChars[i];
        }
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
//        byte[] encodedhash = digest.digest(new String(utfChars).getBytes(UTF_8));
        byte[] encodedhash = digest.digest(bytes);
        return bytesToHex(encodedhash);
    }


    private String bytesToHex(byte[] hash) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString().toUpperCase();
    }
}
