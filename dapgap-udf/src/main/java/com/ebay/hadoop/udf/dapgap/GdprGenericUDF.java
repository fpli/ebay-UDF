package com.ebay.hadoop.udf.dapgap;

import com.ebay.hadoop.udf.common.encrypt.AesEncrypterDecrypter;
import com.ebay.hadoop.udf.common.encrypt.Base64Ebay;
import com.google.common.collect.ImmutableList;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF;

public abstract class GdprGenericUDF extends GenericUDF {

  protected static final String DELETED_STRING = "DELETED";
  protected static final String DELETED_AES_STRING = "NRl/5n95st+Y5fmVHrHZ/Q==";
  protected static final String DELETED_DATE = "1800-01-01";
  protected static final String DELETED_BINARY = "LQcHBw==";
  protected static final int DELETED_NUMBER = -777;
  protected static final List<Integer> DELETED_NUMBER_LIST = ImmutableList.of(-777, -77, 777);
  protected AesEncrypterDecrypter aes;

  protected AesEncrypterDecrypter initAesEncrypterDecrypter() throws UDFArgumentException {
    try {
      //FIXME Get aes.properties from resource.
      //TODO Remove the hardcode of aes.properties
      return new AesEncrypterDecrypter("aes.properties");
    } catch (InvalidKeyException | InvalidAlgorithmParameterException | NoSuchAlgorithmException |
        NoSuchPaddingException e) {
      throw new UDFArgumentException("Cannot initialize aes");
    } catch (IOException ioException) {
      // Try to get from resource folder.
      URL url = getClass().getResource("/aes.properties");
      try {
        return new AesEncrypterDecrypter(url.getPath());
      } catch (Exception e) {
        throw new UDFArgumentException("Cannot initialize aes");
      }
    }
  }

  protected byte[] decrypt(Object value) throws BadPaddingException, IllegalBlockSizeException {
    if (value == null) {
      return null;
    }
    return aes.decrypt(Base64Ebay.decode(value.toString()));
  }

  protected String encrypt(Object value) throws BadPaddingException, IllegalBlockSizeException,
      UnsupportedEncodingException {
    if (value == null) {
      return null;
    }
    return Base64Ebay.encode(aes.encrypt(value.toString()));
  }

}
