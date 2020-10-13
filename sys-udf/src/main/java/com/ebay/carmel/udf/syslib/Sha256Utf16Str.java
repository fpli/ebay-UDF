package com.ebay.carmel.udf.syslib;

import org.apache.hadoop.hive.ql.exec.UDF;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Sha256Utf16Str extends UDF {

    public String evaluate(String input) throws NoSuchAlgorithmException {
        if(input == null) return null;
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] encodedhash = digest.digest(input.getBytes());
        return bytesToHex(encodedhash);
    }

    private String bytesToHex(byte[] hash) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return "0x"+hexString.toString().toUpperCase();
    }
}
