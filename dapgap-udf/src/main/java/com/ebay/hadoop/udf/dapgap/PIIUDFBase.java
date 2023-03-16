package com.ebay.hadoop.udf.dapgap;

import com.ebay.hadoop.udf.common.encrypt.AesEncrypterDecrypter;
import com.ebay.hadoop.udf.common.encrypt.Base64Ebay;
import com.google.common.collect.ImmutableList;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import java.sql.Date;
import java.util.List;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import org.apache.commons.lang3.StringUtils;

import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF;
import org.apache.hadoop.hive.serde2.objectinspector.PrimitiveObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorUtils;

public abstract class PIIUDFBase extends GenericUDF {

  protected static final String MASK_TEXT = "DELETED";
  protected static final List<Integer> DELETED_NUMBER_LIST = ImmutableList
      .of(-777, -77, 777);
  protected static final int MASK_NUMBER = -777;
  protected static final byte MASK_BYTE_NUMBER = -77;
  protected static final Date MASK_DATE = new Date(-5364604800000L); // mask_date = "1800-01-01"
  protected static final byte[] MASK_BINARY = {'-',7,7,7};
  protected static final String DEFAULT_SECRET_FILE = "aes.properties";

  protected AesEncrypterDecrypter cipher;

  protected void setupCipher(DeferredObject[] arguments, int index, PrimitiveObjectInspector oi)
      throws HiveException {
    DeferredObject argument = arguments.length > index && index >= 0 ? arguments[index] : null;
    String secretFile;
    if (argument == null || oi == null) {
      secretFile = DEFAULT_SECRET_FILE;
    } else {
      String secretFileTmp = PrimitiveObjectInspectorUtils.getString(argument.get(), oi);
      if (StringUtils.isBlank(secretFileTmp)) {
        secretFile = DEFAULT_SECRET_FILE;
      } else {
        secretFile = secretFileTmp;
      }
    }

    try {
      cipher = new AesEncrypterDecrypter(secretFile);
    } catch (InvalidKeyException | InvalidAlgorithmParameterException | NoSuchAlgorithmException |
             NoSuchPaddingException e) {
      throw new UDFArgumentException(
          "Cipher initialize error by " + secretFile + ", detail: " + e.getMessage());
    } catch (IOException e) {
      throw new UDFArgumentException(
          "Cipher IO error by " + secretFile + ", detail:" + e.getMessage());
    } catch (Exception e) {
      throw new UDFArgumentException(
          "Cipher Unknown error by " + secretFile + ", detail:" + e.getMessage());
    }
  }

  protected byte[] decrypt(Object value) throws BadPaddingException, IllegalBlockSizeException {
    if (value == null) {
      return null;
    }
    return cipher.decrypt(Base64Ebay.decode(value.toString()));
  }

  protected String encrypt(Object value) throws BadPaddingException, IllegalBlockSizeException,
      UnsupportedEncodingException {
    if (value == null) {
      return null;
    }
    return Base64Ebay.encode(cipher.encrypt(value.toString()));
  }

  protected String encryptedMaskValue() throws HiveException {
    try {
      return encrypt(MASK_TEXT);
    } catch (IllegalBlockSizeException | UnsupportedEncodingException | BadPaddingException e) {
      throw new HiveException("Calculate mask value error", e);
    }
  }
}
