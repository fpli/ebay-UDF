package com.ebay.hadoop.udf.soj;

import com.google.common.collect.Lists;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import org.apache.hadoop.hive.ql.exec.UDF;

public class TagParser extends UDF {

    public List<String> evaluate(String statement) {
        if (statement == null) {
            return null;
        }

        final String target = "sojlib.soj_nvl";
        Set<String> tags = new HashSet<>();

        int i = statement.indexOf(target);
        while (i >= 0) {
            // there is tag usage
            int subEnd = Math.min(i + target.length() + 100, statement.length());
            String sub = statement.substring(i + target.length(), subEnd);

            int start = sub.indexOf("(");
            int end = sub.indexOf(")");

            if (start >= 0 && end >= 0 && end >= start) {

                String parameters = sub.substring(start + 1, end);

                String[] p = parameters.split(",");
                if (p.length == 2 && p[0].toLowerCase(Locale.ROOT).contains("soj")) {
                    int from = p[1].indexOf("'");
                    int to = p[1].indexOf("'", from + 1);
                    if (from >= 0 && to >= 0) {
                        String tag = p[1].substring(from + 1, to);
                        tags.add(tag);
                    }
                }
            }

            i = statement.indexOf(target, i + 1);
        }

        return Lists.newArrayList(tags);
    }
}
