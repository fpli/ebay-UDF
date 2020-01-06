package com.ebay.hadoop.udf.common.encrypt;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;

/**
 *  
 * @author pmustafi
 */
public class KeyAndIV {

	private String key;
	private String iv;
	
	public KeyAndIV(String keyAnIVPropertyFile) 
	   throws FileNotFoundException, IOException, InvalidKeyException, InvalidAlgorithmParameterException, 
	          NoSuchAlgorithmException, NoSuchPaddingException {
		Properties properties = new Properties();
		properties.load(new FileInputStream(keyAnIVPropertyFile));
		iv = properties.getProperty("IV");
		key = properties.getProperty("KEY");
		if (iv != null && iv.length() > 0 &&
				key != null && key.length() > 0) {

			IvParameterSpec ivps = new IvParameterSpec(iv.getBytes(AesEncrypterDecrypter.UTF8_ENCODING));
			SecretKeySpec seckey = new SecretKeySpec(Base64Ebay.decode(key), AesEncrypterDecrypter.PROVIDER);

			Cipher.getInstance(AesEncrypterDecrypter.CIPHER_MODE).init(Cipher.ENCRYPT_MODE, seckey, ivps);
		}
	}

	public String getKey() {
		return key;
	}

	public String getIV() {
		return iv;
	}
}
