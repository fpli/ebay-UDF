package com.ebay.hadoop.udf.common.encrypt;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class AesEncrypterDecrypter {
    public static final String PROVIDER = "AES";
    public static final String CIPHER_MODE = PROVIDER + "/CBC/PKCS5Padding";
    public static final String UTF8_ENCODING = "UTF-8";
    private IvParameterSpec IVPS;
    private SecretKey SECRETKEY;
	private Cipher ecipher;
	private Cipher dcipher;

	public AesEncrypterDecrypter(String keyAndIvFileName) throws
		InvalidKeyException, FileNotFoundException, InvalidAlgorithmParameterException,
		NoSuchAlgorithmException, NoSuchPaddingException, IOException
	{
		KeyAndIV keyiv = new KeyAndIV(keyAndIvFileName);
		initialize(keyiv.getKey(), keyiv.getIV());
	}
	
	public AesEncrypterDecrypter(String key, String iv) throws
		UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException,
		InvalidKeyException, InvalidAlgorithmParameterException
	{
		initialize(key, iv);
	}

	private void initialize(String key, String iv) throws
		UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException,
		InvalidKeyException, InvalidAlgorithmParameterException
	{
		IVPS = new IvParameterSpec(iv.getBytes(UTF8_ENCODING));
		SECRETKEY = new SecretKeySpec(Base64Ebay.decode(key), PROVIDER);

		ecipher = Cipher.getInstance(CIPHER_MODE);
		dcipher = Cipher.getInstance(CIPHER_MODE);

		// CBC requires an initialization vector
		ecipher.init(Cipher.ENCRYPT_MODE, SECRETKEY, IVPS);
		dcipher.init(Cipher.DECRYPT_MODE, SECRETKEY, IVPS);
	}
	
	public byte[] encrypt(String in) throws
		IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException
	{
		return ecipher.doFinal(in.getBytes(UTF8_ENCODING));
	}

	public byte[] decrypt(String in) throws
		IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException
	{
		return dcipher.doFinal(in.getBytes(UTF8_ENCODING));
	}
	
	public byte[] encrypt(byte[] in) throws IllegalBlockSizeException, BadPaddingException {
		return ecipher.doFinal(in);
	}

	public byte[] decrypt(byte[] in) throws IllegalBlockSizeException, BadPaddingException {
		return dcipher.doFinal(in);
	}
	
	public OutputStream getEncryptionOutputStream(OutputStream out) {
		return new CipherOutputStream(out, ecipher);
	}
	
	public InputStream getDescryptionInputStream(InputStream in) {
		return new CipherInputStream(in, dcipher);
	}
	
	// Buffer used to transport the bytes from one stream to another
	byte[] buf = new byte[4096];
	public void encrypt(InputStream in, OutputStream out) throws IOException {
		try {
			// Bytes written to out will be encrypted
			out = new CipherOutputStream(out, ecipher);
			// Read in the cleartext bytes and write to out to encrypt
			int numRead = 0;
			while ((numRead = in.read(buf)) >= 0) {
				out.write(buf, 0, numRead);
			}
		} finally {
			out.close();
		}
	}

	public void decrypt(InputStream in, OutputStream out) throws IOException {
		try {
			// Bytes read from in will be decrypted
			in = new CipherInputStream(in, dcipher);
			// Read in the decrypted bytes and write the cleartext to out
			int numRead = 0;
			while ((numRead = in.read(buf)) >= 0) {
				out.write(buf, 0, numRead);
			}
		} finally {
			out.close();
		}
	}
	
	public void gzencrypt(InputStream in, OutputStream out) throws IOException {
		try {
			// Bytes written to out will be encrypted
			out = new GZIPOutputStream(new CipherOutputStream(out, ecipher));
			// Read in the cleartext bytes and write to out to encrypt
			int numRead = 0;
			while ((numRead = in.read(buf)) >= 0) {
				out.write(buf, 0, numRead);
			}
		} finally {
			out.close();
		}
	}

	public void gzdecrypt(InputStream in, OutputStream out) throws IOException {
		try {
			// Bytes read from in will be decrypted
			in = new GZIPInputStream(new CipherInputStream(in, dcipher));
			// Read in the decrypted bytes and write the cleartext to out
			int numRead = 0;
			while ((numRead = in.read(buf)) >= 0) {
				out.write(buf, 0, numRead);
			}
		} finally {
			out.close();
		}
	}
	
	public static void main(String args[]) {
		try {
			// Generate a key. In practice, you would save this key in a file
			KeyGenerator keygen = KeyGenerator.getInstance("AES");
			keygen.init(128);
			SecretKey key = keygen.generateKey();
			System.out.println(Base64Ebay.encode(key.getEncoded()));

			
			// Create encrypter/decrypter class
			AesEncrypterDecrypter encrypter = new AesEncrypterDecrypter("7gywXhFsS/EasaNVB4VKKg==", "2f14e36d58c7b9a0");

			encrypter.gzencrypt(new FileInputStream("wideinput.txt"),
			        new FileOutputStream("ciphertext"));
			
			encrypter.gzdecrypt(new FileInputStream("ciphertext"),
			        new FileOutputStream("decryptedtext"));
			
			// Encrypt bytes
			long t1 = System.currentTimeMillis();
			String input = "hello, how are you";
			byte encdata[] = encrypter.encrypt(input.getBytes(UTF8_ENCODING));
			long t2 = System.currentTimeMillis();
			System.out.println("Encrypt took " + (t2 - t1) + " ms");
			// Decrypt bytes
			byte decdata[] = encrypter.decrypt(encdata);
			long t3 = System.currentTimeMillis();
			
			if (!input.equals(new String(decdata, UTF8_ENCODING))) {
				System.out.println("FAILED");
			} else {
				System.out.println(new String(decdata, UTF8_ENCODING));
			}
			System.out.println("Decrypt took " + (t3 - t2) + " ms");
			
			
			
			// Encrypt using stream
			t1 = System.currentTimeMillis();
			encrypter.encrypt(new FileInputStream("input.txt"),
					new FileOutputStream("ciphertext"));
			t2 = System.currentTimeMillis();
			System.out.println("Encrypt took " + (t2 - t1) + " ms");

			// Decrypt using stream
			encrypter.decrypt(new FileInputStream("ciphertext"),
					new FileOutputStream("output.txt"));
			t3 = System.currentTimeMillis();
			System.out.println("Decrypt took " + (t3 - t2) + " ms");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
