package com.ebay.hadoop.udf.common;

import java.util.Arrays;
import java.util.HashSet;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hive.ql.exec.UDF;

public class UrlHexEncode extends UDF{

	static final String hex = "0123456789abcdef";
	
	static final HashSet<Character> set = new HashSet<Character>(Arrays.asList('-','_','.','~','!','*','(',')',';','/','?',':','@','&','=','+','$',',', (char) 39));
	
    public String evaluate(String url) {

        
        /*
         * checking if the given url is null
         * 
         */
    	
    	if (url != null) {
    		
    		/*
            checking url is alphanumeric or marked or reserved symbols.

            reserved      = ";" | "/" | "?" | ":" | "@" | "&" | "=" | "+" | "$" | ","
            mark          = "-" | "_" | "." | "!" | "~" | "*" | "'" | "(" | ")"
            lowalpha      = "a" | "b" | "c" | "d" | "e" | "f" | "g" | "h" | "i" |
                            "j" | "k" | "l" | "m" | "n" | "o" | "p" | "q" | "r" |
                            "s" | "t" | "u" | "v" | "w" | "x" | "y" | "z"
            upalpha       = "A" | "B" | "C" | "D" | "E" | "F" | "G" | "H" | "I" |
                            "J" | "K" | "L" | "M" | "N" | "O" | "P" | "Q" | "R" |
                            "S" | "T" | "U" | "V" | "W" | "X" | "Y" | "Z"
            digit         = "0" | "1" | "2" | "3" | "4" | "5" | "6" | "7" | "8" | "9"

             */
    		
    		
    		StringBuilder result = new StringBuilder();
    		
            char[] chars = url.toCharArray();

            for (int i = 0; i < chars.length; i++) {
            	
                if (StringUtils.isAlphanumeric(String.valueOf(chars[i])) || set.contains(chars[i])) {

                    result.append(chars[i]);
                    

                } else if (chars[i] == ' ') {

                    result.append("+");

                } else {

                    result.append("%");
                    result.append(UrlHexEncode.url_to_hex((char)(chars[i] >> 4)));
                    result.append(UrlHexEncode.url_to_hex((char)(chars[i] & 15)));

                }

            }

            return result.toString();
    		
    	}
    	
    	else {
    		
    		return url;
    	}
    	
    	
    }

    private static char url_to_hex (char code) {

    	
        return hex.charAt(code & 15);
    }

}
