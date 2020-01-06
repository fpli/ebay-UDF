package com.ebay.hadoop.udf.common;


import com.ebay.hadoop.udf.common.encrypt.AesEncrypterDecrypter;
import com.ebay.hadoop.udf.common.encrypt.Base64Ebay;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;

public final class Aes2Text extends UDF {
	private AesEncrypterDecrypter aes;

	public Text evaluate(final Text s, String filePath) throws Exception {
		if (s == null || filePath == null) {
			return null;
		}
		if (aes == null) {
			aes = new AesEncrypterDecrypter(filePath);
		}
		return new Text(aes.decrypt(Base64Ebay.decode(s.toString())));
	}
}
