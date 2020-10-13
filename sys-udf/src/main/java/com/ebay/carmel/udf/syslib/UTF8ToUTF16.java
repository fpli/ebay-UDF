package com.ebay.carmel.udf.syslib;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hive.ql.exec.UDF;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class UTF8ToUTF16 extends UDF {

    // TODO impl need refer to https://github.corp.ebay.com/APD/APD-Arch/tree/master/udf/syslib
    public String evaluate(String input) throws UnsupportedEncodingException {
        if(StringUtils.isBlank(input)) return null;
        byte[] utf16Bytes = input.getBytes("UTF-16");
        ArrayList<Character> characters = new ArrayList<>();
        for(int i = 2 ; i < utf16Bytes.length ; i = i + 2) {
            int left  = (utf16Bytes[i] & 0xFF) * 100;
            int right = utf16Bytes[i + 1] & 0xFF;
            char c = (char)(left + right);
            characters.add(c);
        }
        char[] chars = new char[characters.size()];
        int index = 0;
        for(Character c : characters) {
            chars[index ++] = c.charValue();
        }
        return new String(chars);
    }
}
