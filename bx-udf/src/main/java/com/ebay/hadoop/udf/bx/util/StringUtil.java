package com.ebay.hadoop.udf.bx.util;


import java.util.ArrayList;
import java.util.Collection;


public class StringUtil {

    // java's built-in split is problematic
    public static String[] split(String str, String delimeter) {
        ArrayList<String> list = new ArrayList<String>();
        int index = 0, offset = 0;
        int l = delimeter.length();
        if (str.startsWith(delimeter)) {
            // in case the first field is empty
            list.add("");
            offset = offset + l;
        }
        while ((index = str.indexOf(delimeter, index + 1)) != -1) {
            list.add(str.substring(offset, index));
            offset = index + l;
        }
        // add the last field, or the str doesn't contain delimiter at all
        list.add(str.substring(offset));
        return list.toArray(new String[0]);
    }

    public static String join(String[] tokens) {
        return join(tokens, ",");
    }

    public static String join(String[] tokens, String delimeter) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < tokens.length; ++i) {
            builder.append(tokens[i]);
            if (i != tokens.length - 1)
                builder.append(delimeter);
        }
        return builder.toString();
    }

    public static String join(Collection<String> tokens, String delimeter) {
        return join(tokens.toArray(new String[0]), delimeter);
    }


}
