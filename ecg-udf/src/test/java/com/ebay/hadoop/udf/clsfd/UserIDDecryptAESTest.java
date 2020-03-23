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
 *   -- cast(encypted_user_id) as int
 * from dw_clsfd.stg_clsfd_ga_raw_de raw
 * where encypted_user_id is not null
 * limit 3
 * ;
 *
 */

public class UserIDDecryptAESTest {

    @Test
    public void test() throws Exception {

        UserIDDecryptAES userIDDecryptAES = new UserIDDecryptAES();
        String str = userIDDecryptAES.evaluate("08e3e78060c7af531e2484da0e6f2806");
        assertEquals(Integer.parseInt("49352494"),Integer.parseInt(str));

        try {

            String plaintext = "";
            String encryptionKey = "867f6e6ba179867f";

            System.out.println("==Java==");
            System.out.println("plain:   " + plaintext);

            byte[] cipher = userIDDecryptAES.encrypt(plaintext, encryptionKey);

            System.out.print("Google reads encrypted Cipher Text in HexString:\n  ");
            for (int i=0; i<cipher.length; i++)
                System.out.print( "\n ciper:"+cipher[i]+"; and tostring:" + String.format("%02x", cipher[i] & 0xff));

            System.out.print("\nput together:");
            for (int i=0; i<cipher.length; i++)
                System.out.print( String.format("%02x", cipher[i] & 0xff));

            //System.out.println("\ndecrypted:" + userIDDecryptAES.decrypt(cipher, encryptionKey));
            //System.out.println("\nUDF decrypted:"+ userIDDecryptAES.evaluate("b3ee052d0d5219af95aa61d4289b9e56"));
            System.out.println("\nUDF decrypted:"+ userIDDecryptAES.evaluate("08e3e78060c7af531e2484da0e6f2806"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
