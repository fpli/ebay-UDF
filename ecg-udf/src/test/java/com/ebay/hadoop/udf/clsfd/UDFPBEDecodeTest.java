package com.ebay.hadoop.udf.clsfd;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by jianyahuang on 2019.02.26
 *
 * used in ga-userid
 *
 * select ga_prfl_id
 *   ,dw_clsfd.array_struct_to_map(raw.customdimensions)[20] as encypted_user_id
 *   --,cast(dw_clsfd.parse_user("348A7C17292CC7D62CDBE8AAEF1B354B") as int)
 * from dw_clsfd.stg_clsfd_ga_raw_be raw
 * limit 13;
 *
 */

public class UDFPBEDecodeTest {

    @Test
    public void test(){

        //String str1 = new UDFPBEDecode().evaluate("4703FA67798F0026DC2C6C37F9B8ED43");
        String str2 = new UDFPBEDecode().evaluate("348A7C17292CC7D62CDBE8AAEF1B354B");
        //assertEquals("0017642998",str1);
        assertEquals(Integer.parseInt("28674820"),Integer.parseInt(str2));
    }

}
