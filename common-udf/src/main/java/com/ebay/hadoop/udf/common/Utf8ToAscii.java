package com.ebay.hadoop.udf.common;

import com.ebay.hadoop.udf.tags.ETLUdf;
import org.apache.hadoop.hive.ql.exec.UDF;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Created by szang on 7/31/17.
 *
 * This module defines UDF: utf8_to_ascii() with following use cases:
 *
 * Case 1: utf8_to_ascii(input)
 *      User provides string input only.
 *
 *      The UDF will return the input if non-ASCII character was found; Otherwise, throw exception.
 *
 * Case 2: utf8_to_ascii(input, mode)
 *      User provides string input
 *                    int (0 or 1) indicating if they believe the input contains non-ASCII or not.
 *
 *      0 indicates they assume only ASCII characters exist in the string, the UDF will throw exception when there is non-ASCII character.
 *      1 indicates it is possible that the string contains non-ASCII character, the UDF will convert all non-ASCII to the default replacement character.
 *
 * Case 3: utf8_to_ascii(input, mode, subchar)
 *      User provides string input
 *                    int (0 or 1) indicating if they believe the input contains non-ASCII or not
 *                    string subchar (user-defined) replacement character(s)）.
 *
 *      The UDF will convert all non-ASCII characters to user-defined subchar.
 */
@ETLUdf(name = "utf8_to_ascii")
public class Utf8ToAscii extends UDF {

    /*
    * This implements case 1, where user assumes the input contains ASCII characters ONLY.
    *
    * This is also the default method when user provides the input string as the only argument.
    *
    * It throws Exception() when non-ASCII character is encountered and returns the original input if it goes through.
    *
    * */
    public String evaluate(String input) throws Exception {
        // check case of 1st argument being null
        if (input == null) {
            return null;
        }
        for (int i = 0; i < input.length(); i++) {
            if (! isASCII(input.charAt(i))) {
                String exceptionMsg = "ERROR: Non-ASCII character encountered when converting " + input;
                throw new Exception(exceptionMsg);
            }
        }
        return input;
    }

    /*
    * This implements case 2, where :
    *
    * If a user is sure that the input string does not contain non-ASCII characters, set the 2nd parameter(mode) to 0, it calls case 1 evaluate().
    *
    * If a user is not sure about it, set the 2nd parameter(mode) to 1 and it will convert all non-ASCII characters to the default substitute character 0x1A (same as TD documentation about replacement character).
    *
    * */
    /*
    public String evaluate(String input, int mode) throws Exception {
        // check case of 1st argument being null
        if (input == null) {
            return null;
        }
        // check case of 2nd argument being invalid
        if (mode != 0 && mode != 1) {
            throw new Exception("ERROR: The second parameter can only be 0 (throw exception upon non-ASCII characters) or 1 (substitude non-ASCII characters with the default/provided character.)");
        }
        // use the overload function defined below
        if (mode == 0) {
            return evaluate(input);
        }

        char defaultSubChar = 0x1A;

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < input.length(); i++) {
            if (! isASCII(input.charAt(i))) {
                sb.append(defaultSubChar);
            }
            else {
                sb.append(input.charAt(i));
            }
        }
        return sb.toString();
    }
*/
    /*
    * This implements case 3, where the input string might contain non-ASCII characters and users want to substitute it with their own choices of character.
    *
    * In this case, the 2nd parameter must be set 1, indicating that "As a user, I don't know if the input contains non-ASCII characters or not."
    * */
    /*
    public String evaluate(String input, int mode, String subChar) throws Exception {
        // check case of 1st argument being null
        if (input == null) {
            return null;
        }
        // check case of 2nd argument being invalid
        if (mode != 1) {
            throw new Exception("ERROR: If you want to substitute non-ASCII characters with customized character, please set the 2nd parameter 1.");
        }

        if (subChar == null) {
            throw new Exception("ERROR: replacement character must not be 'null'");
        }
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < input.length(); i++) {
            if (! isASCII(input.charAt(i))) {
                sb.append(subChar);
            }
            else {
                sb.append(input.charAt(i));
            }
        }
        return sb.toString();
    }
*/
    /*
    * This implements case 4, which can be viewed as case 3 without mode argument.
    * */
    public String evaluate(String input, String subChar) throws Exception {
        // check case of 1st argument being null
        if (input == null) {
            return null;
        }

        if (subChar == null) {
            throw new Exception("ERROR: replacement character must not be 'null'");
        }

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < input.length(); i++) {
            if (! isASCII(input.charAt(i))) {
                sb.append(subChar);
            }
            else {
                sb.append(input.charAt(i));
            }
        }
        return sb.toString();
    }

    /*
    * This is the same implementation of what is in IsACSII class, checking if the given char is ASCII character or not.
    * */
    private boolean isASCII(char ch) {
        String strChar = String.valueOf(ch);
        byte[] utf8encoding = strChar.getBytes(UTF_8);
        return utf8encoding.length == 1;
    }
}
