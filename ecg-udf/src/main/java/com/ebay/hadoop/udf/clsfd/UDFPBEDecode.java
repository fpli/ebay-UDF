package com.ebay.hadoop.udf.clsfd;


import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.jasypt.encryption.BigIntegerEncryptor;
import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PBEBigIntegerEncryptor;
import org.jasypt.encryption.pbe.PBEStringEncryptor;
import org.jasypt.encryption.pbe.StandardPBEBigIntegerEncryptor;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.salt.ZeroSaltGenerator;

import java.math.BigInteger;

@Description(name="PBEDecode", value="_FUNC_(string) - from the input encoded string ", extended="Example:\n > SELECT _FUNC_(string) FROM src;")
public class UDFPBEDecode extends UDF
{
    private static final Logger logger = Logger.getLogger(UDFPBEDecode.class);
    private static final String STANDARD_ENCRYPTION_ALGORITHM = "PBEWithMD5AndDES";
    private static final String ENCRYPTION_PASSWORD = "867f6e6ba179";
    private static final PBEBigIntegerEncryptor ENCRYPTOR = getPBEBigIntegerEncryptor();
    private static final PBEBigIntegerEncryptor ENCRYPTOR_OLD = getPBEBigIntegerEncryptorOld();
    private static final PBEStringEncryptor STRING_ENCRYPTOR = getPBEPBEStringEncryptor();

    private static PBEBigIntegerEncryptor getPBEBigIntegerEncryptor() {
        StandardPBEBigIntegerEncryptor encryptor = new StandardPBEBigIntegerEncryptor();
        encryptor.setAlgorithm(STANDARD_ENCRYPTION_ALGORITHM);
        encryptor.setPassword("$sysop{encrypted.id.password}");
        encryptor.setSaltGenerator(new ZeroSaltGenerator());
        return encryptor;
    }
    private static PBEBigIntegerEncryptor getPBEBigIntegerEncryptorOld() {
        StandardPBEBigIntegerEncryptor encryptor = new StandardPBEBigIntegerEncryptor();
        encryptor.setAlgorithm(STANDARD_ENCRYPTION_ALGORITHM);
        encryptor.setPassword(ENCRYPTION_PASSWORD);
        encryptor.setSaltGenerator(new ZeroSaltGenerator());
        return encryptor;
    }
    private static PBEStringEncryptor getPBEPBEStringEncryptor() {
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setAlgorithm(STANDARD_ENCRYPTION_ALGORITHM);
        encryptor.setPassword(ENCRYPTION_PASSWORD);
        encryptor.setSaltGenerator(new ZeroSaltGenerator());
        encryptor.setStringOutputType("hexadecimal");
        return encryptor;
    }

    public static String decrypt(Object encryptor, String hashedString) {
        String ret = null;
        try {
            if ((encryptor instanceof StringEncryptor))
                ret = ((StringEncryptor)encryptor).decrypt(hashedString);
            else if ((encryptor instanceof BigIntegerEncryptor))
                ret = ((BigIntegerEncryptor)encryptor).decrypt(new BigInteger(hashedString)).toString();
        } catch (Exception localException) {
            logger.debug("cast type err ,pls check");
        }

        return ret;
    }
    public static  String encrypt(Object encryptor, String rawStr) {
        String hashedString = null;
        try{
            if ((encryptor instanceof StringEncryptor))
                hashedString = ((StringEncryptor)encryptor).encrypt(rawStr);
            else if ((encryptor instanceof BigIntegerEncryptor))
                hashedString = ((BigIntegerEncryptor)encryptor).encrypt(new BigInteger(rawStr)).toString();
        }catch (Exception e){
            logger.debug("cast type err ,pls check");
        }

        return hashedString;
    }
    public String evaluate(String hashedString) {
        //logger.setLevel(Level.INFO);
        //logger.addAppender(org.apache.log4j.ConsoleAppender);
        BasicConfigurator.configure();
        if (hashedString == null) return null;
        String ret = null;
        if (ret == null) ret = decrypt(ENCRYPTOR, hashedString);
        if (ret == null) ret = decrypt(ENCRYPTOR_OLD, hashedString);
        if (ret == null) ret = decrypt(STRING_ENCRYPTOR, hashedString);
        return ret;
    }

}
