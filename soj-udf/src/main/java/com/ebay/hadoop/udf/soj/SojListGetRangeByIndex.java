package com.ebay.hadoop.udf.soj;

import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;

import java.util.Arrays;
import java.util.List;

public class SojListGetRangeByIndex extends UDF {

    private static List<String> stringList = Arrays.asList("$", "(", ")", "*", "+", ".", "[", "]", "?", "\\", "^", "{", "}", "|");

    public Text evaluate( Text list, String delimiter, int start_index, int num_values ) {

        if (list == null || delimiter == null) {
            return null;
        }

        String input_str = list.toString();
        String output_str = getRangeByIdx(input_str, delimiter, start_index, num_values);

        if (output_str == null) {
            return null;
        } else {
            return new Text(output_str);
        }
    }

    private String getRangeByIdx( String list, String delimiter, int start_index, int num_values ) {
        // checking NULL value for parameter:str_vec
        if (list == null || list.length() == 0) {
            return null;
        }

        // checking NULL value for parameter:vec_delimit
        if (delimiter == null || delimiter.length() == 0) {
            return null;
        }
        String deli = delimiter;
        if (stringList.contains(delimiter)) {
            deli = "\\" + deli;
        }
        String[] list_split = list.split(deli, -1);
        String[] result = new String[Math.max(start_index - 1 + num_values, list_split.length)];
        int index = 0;
        int count = 0;
        boolean isFound = false;
        for (int i = 0; i < list_split.length; i++) {
            if (i >= start_index - 1) {
                count++;
                result[index] = list_split[i];
                index++;
            }
            if (count >= num_values) {
                isFound = true;
                break;
            }

        }
        if (count > 0) {
            isFound = true;
        }
        if (count == 0) {
            return null;
        } else {
            if ("".equals(StringUtils.join(result, delimiter, 0, Math.min(count, result.length)))) {
                return null;
            } else {
                return StringUtils.join(result, delimiter, 0, Math.min(count, result.length));
            }
        }


    }
}