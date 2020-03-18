package com.ebay.hadoop.udf.clsfd;

//import java.security.MessageDigest;
//import java.util.Arrays;
//import javax.crypto.KeyGenerator;
//import javax.crypto.SecretKey;
//import java.util.List;
//import org.apache.commons.codec.binary.Hex;
//import javax.crypto.spec.IvParameterSpec;
//import javax.crypto.spec.SecretKeySpec;

import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.log4j.Logger;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;



public class UserIDDecryptAES extends UDF {

  static String IV = "0000000000000000";
  static String plaintext = ""; /*Note null padding*/
//  static int plaintextint = 20160831; /*Note null padding*/
//  static String plaintextintstr= Integer.toString(plaintextint);
  static String encryptionKey = "867f6e6ba179867f";
  SecretKeySpec key = new SecretKeySpec((encryptionKey.getBytes()), "AES");
  
  private static final Logger logger = Logger.getLogger(UserIDDecryptAES.class);
  
  public String evaluate(String input) throws Exception{
		if(input == null ){
			return null;
		}
		String DecryptString=null;
		try{
			byte[] cipher_bytes=DatatypeConverter.parseHexBinary(input);
			DecryptString=decrypt(cipher_bytes, encryptionKey);
		}catch(Exception e){
			//e.printStackTrace();
            logger.error(null,new Exception(e));
		}
		return DecryptString;
  }

  public static byte[] encrypt(String plainText, String encryptionKey) throws Exception {
    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding ");
	//  Cipher cipher = Cipher.getInstance("AES", "SunJCE")
    SecretKeySpec key = new SecretKeySpec(encryptionKey.getBytes("UTF-8"), "AES");
    cipher.init(Cipher.ENCRYPT_MODE, key,new IvParameterSpec(IV.getBytes("UTF-8")));
    return cipher.doFinal(plainText.getBytes("UTF-8"));
  }

  public static String decrypt(byte[] cipherText, String encryptionKey) throws Exception{
    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding ");
    //, "SunJCE"
    SecretKeySpec key = new SecretKeySpec(encryptionKey.getBytes("UTF-8"), "AES");
    cipher.init(Cipher.DECRYPT_MODE, key,new IvParameterSpec(IV.getBytes("UTF-8")));
    return new String(cipher.doFinal(cipherText),"UTF-8");
  }

}