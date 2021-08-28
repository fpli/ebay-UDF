package com.ebay.hadoop.udf.common;

import static java.nio.charset.StandardCharsets.UTF_8;

import com.ebay.hadoop.udf.tags.ETLUdf;
import org.apache.hadoop.hive.ql.exec.UDF;

/**
 * Created by szang on 7/31/17.
 *
 * This module defines UDF: IsASCII, checking if the string contains ONLY ASCII characters.
 *
 * In case of ONLY ASCII characters, return 0;
 *
 * Otherwise, return the position of the first non-ASCII character in the string, starting from 1.
 */
@ETLUdf(name = "is_ascii")
public class IsAscii extends UDF {

    public int evaluate(String input) {
        if (input == null)
            return 0;

        for (int i = 0; i < input.length(); i++) {

            String character = String.valueOf(input.charAt(i));

            byte[] utf8encoding = character.getBytes(UTF_8);

            if (utf8encoding.length != 1) return i+1;
        }
        return 0;
    }
}
