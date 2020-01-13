package com.ebay.hadoop.udf.soj;

/**
 * Created by xiaoding on 2017/1/25.
 */
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;

public class Base36Decoder extends UDF
{
    public Text evaluate(Text enc)
    {
        if (enc == null) {
            return null;
        }
        String inst = enc.toString();
        StringBuilder decoded = new StringBuilder();
        if (inst.contains(",")) {
            String[] numArray = inst.split(",");
            for (String num : numArray) {
                if (decoded.length() > 0)
                    decoded.append(",");
                try
                {
                    decoded.append(Long.parseLong(num, 36));
                } catch (Exception e) {
                    return null;
                }
            }
        } else {
            try {
                decoded.append(Long.parseLong(inst, 36));
            } catch (NumberFormatException e) {
                return null;
            }
        }
        return new Text(decoded.toString());
    }
}
