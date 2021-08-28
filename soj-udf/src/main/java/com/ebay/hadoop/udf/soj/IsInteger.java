package com.ebay.hadoop.udf.soj;

import com.ebay.hadoop.udf.tags.ETLUdf;
import com.google.common.primitives.Ints;
import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;

@ETLUdf(name = "soj_isint")
public class IsInteger extends UDF {

    @SuppressWarnings("unused")
    public IntWritable evaluate( Text inst) {
        if (inst == null || StringUtils.isEmpty(inst.toString())) {
            return new IntWritable(0);
        }
        // Never use JDK's Integer.valueOf which throws exception for invalid integer and hurts performance
        Integer value = null;
        if (inst.charAt(0) != '+') {
            value = Ints.tryParse(inst.toString());
        } else {
            value = Ints.tryParse(inst.toString().substring(1));
        }

        if (value == null) {
            if (inst.getLength() == 0) {
                return new IntWritable(1);
            } else {
                return new IntWritable(0);
            }
        } else {
            return new IntWritable(1);
        }
    }

    public static void test(String a) {
        Integer ai = Integer.valueOf(a);
        Integer gi = Ints.tryParse(a);
        System.out.println(ai + " vs. " + gi);
        IsInteger isa = new IsInteger();
        System.out.println(isa.evaluate(new Text(a)));
    }
    public static void main(String args[]) {
        test("+1234");
        test("-1234");
        test("1a");
    }
}