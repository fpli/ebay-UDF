package com.ebay.hadoop.udf.common;

import com.ebay.hadoop.udf.tags.ETLUdf;
import org.apache.hadoop.hive.ql.exec.UDF;

import static java.lang.Character.isDigit;

/**
 * Created by szang on 7/31/17.
 *
 * This UDF exams if the input String can be interpreted as a valid decimal number.

 * The source code reference: https://github.corp.ebay.com/APD/DINT-UDF/blob/master/udf/syslib/udf_IsDecimal2.c
 *
 * The initial request on JIRA ticket:
 *
 *      Hi Team,
 *         The below UDF function is not supported in Spark. Please let me know the equivalent.
 *         syslib.udf_isdecimal(encrypted_user_id)=1
 *
 * On a side note, the source code doesn't deal with the following corner case:
 *      input: "0000.1"
 */

@ETLUdf(name = "is_decimal")
public class IsDecimal extends UDF {

    public boolean evaluate(String input, int digitLimit) {

        // TD return false in case of null input.
        if (input == null) return false;

        input = input.trim();

        int length = input.length();

        if (length == 0) return false;

        int dpCnt = 0; // decimal points counter
        int chCnt = 0; // char pointer counter

        // one leading +|- sign is ok
        if (input.charAt(chCnt) == '+' || input.charAt(chCnt) == '-') {
            chCnt++;
        }

        while (true) {
            if (chCnt == length) {
                break;
            }
            if (input.charAt(chCnt) == '.') {
                dpCnt++; // count the number of decimalpoints in the input
            }
            else if (!isDigit(input.charAt(chCnt))) {
                return false;
            }
            chCnt++;
        }
        return chCnt <= digitLimit && dpCnt <= 1;
    }

    // Overloads the above method with a defalut digitLimit == 18, which is verified in TD by Daniel.
    public boolean evaluate(String input) {
        return evaluate(input, 18);
    }
}
