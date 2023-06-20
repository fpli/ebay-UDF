package com.ebay.search.util;

import java.util.List;

import java.io.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.zip.GZIPInputStream;

/**
 * A common utility class. 
 */
public final class CommonUtils
{
  private static final boolean ms_bDebug="true".equalsIgnoreCase(System.getProperty("debug"));

  /**
   * Builds a map from a designated column (by @param iKeyPos) to the whole record.
   * As a contract, the input reader will NOT be closed
   */
  public static HashMap<String, String> buildHash(BufferedReader br, String szDelim, int iKeyPos, int iMax)
      throws IOException
    {
      HashMap<String, String> hm = new HashMap<String, String>();
      String szLine = null;
      while ( (szLine = br.readLine()) != null) {
        String[] szComps = szLine.split(szDelim, iMax); 
        hm.put(szComps[iKeyPos], szLine);
        // System.out.println("Key: " + szComps[iKeyPos] + " => " + szLine);
      }

      return hm;
    }


  /**
   * Builds a set from two designated columns (by @param iKeyPos1 and @param iKeyPos2) 
   * As a contract, the input reader will NOT be closed
   */
  public static HashSet<String> buildHashSet(BufferedReader br, String szDelim, int iKeyPos1, int iKeyPos2, int iMax, String szKeySeparator)
      throws IOException
    {
      HashSet<String> hs = new HashSet<String>();
      String szLine = null;
      while ( (szLine = br.readLine()) != null) {
        String[] szComps = szLine.split(szDelim, iMax); 
        hs.add(szComps[iKeyPos1].trim() + szKeySeparator + szComps[iKeyPos2].trim());
      }

      return hs;
    }


  /**
   * Builds a map from a designated column (by @param iKeyPos1 and @param iKeyPos2) to the whole record.
   * As a contract, the input reader will NOT be closed
   */
  public static HashMap<String, String> buildHash(BufferedReader br, String szDelim, int iKeyPos1, int iKeyPos2, int iMax, String szKeySeparator)
      throws IOException
    {
      HashMap<String, String> hm = new HashMap<String, String>();
      String szLine = null;
      while ( (szLine = br.readLine()) != null) {
        String[] szComps = szLine.split(szDelim, iMax); 
        hm.put(szComps[iKeyPos1].trim() + szKeySeparator + szComps[iKeyPos2].trim(), szLine);
        // System.out.println("Key: " + szComps[iKeyPos] + " => " + szLine);
      }

      return hm;
    }



  /**
   * Builds a map from a designated column (by @param iKeyPos) to the a target column.
   * As a contract, the input reader will NOT be closed
   */
  public static HashMap<String, String> buildHash(BufferedReader br, String szDelim, int iKeyPos, int iTargetPos, int iMax)
      throws IOException
    {
      HashMap<String, String> hm = new HashMap<String, String>();
      String szLine = null;
      while ( (szLine = br.readLine()) != null) {
        String[] szComps = szLine.split(szDelim, iMax); 
        hm.put(szComps[iKeyPos], szComps[iTargetPos]);
        System.out.println("Key: " + szComps[iKeyPos] + " => " + szComps[iTargetPos]);
      }

      return hm;
    }


  /**
   * Returns an integer from the given String value. If there is an exception
   * during parsing, the default integer value is returned.
   * This method will not throw an exception.
   */
  public static int safeParseInt(String s, int iDefaultVal)
  {
    int i;
    try {
      i = Integer.parseInt(s);
    }
    catch (Throwable t) {
      i = iDefaultVal;
    }
    return i;
  }


  /**
   * Returns an integer from the given String value. If there is an exception
   * during parsing, the default integer value is returned.
   * This method will not throw an exception.
   */
  public static long safeParseLong(String s, long lDefaultVal)
  {
    long l;
    try {
      l = Long.parseLong(s);
    }
    catch (Throwable t) {
      l = lDefaultVal;
    }
    return l;
  }


  public static List<String> collectStringValues(String szInputFileName)
      throws IOException
    {
      BufferedReader br = null;
      List<String> lst = new ArrayList<String>();

      try {
        br = new BufferedReader(new FileReader(szInputFileName));
        String szLine;
        while ( (szLine = br.readLine()) != null) {
          lst.add(szLine);
        }

      }
      finally {
        quietlyClose(br);
      }

      return lst;
    }


  public static void quietlyClose(Reader r)
  {
    try {
      r.close();
    }
    catch (Throwable t) {
    }
  }


  public static void quietlyClose(Writer w)
  {
    try {
      w.close();
    }
    catch (Throwable t) {
    }
  }


  public static String extractSubStrBetween(String s, String szStart, String szEnd)
  {
    // now extract the first category ID and confidence 
    int idxStart = s.indexOf(szStart);
    if (idxStart < 0) return "";

    int idxEnd = s.indexOf(szEnd, idxStart);
    if (idxEnd < 0) return "";

    return s.substring(idxStart + szStart.length(), idxEnd);
  }




  /**
   * PasscodeReader contains logic to read sensitive information for system console
   */
  static class PasscodeReader
  {
    public PasscodeReader()
    {
    }


    public char[] readPassword(String format, Object... args)
        throws IOException 
      {
        if (System.console() != null)
          return System.console().readPassword(format, args);
        return this.readLine(format, args).toCharArray();
      }


    private String readLine(String format, Object... args) throws IOException 
    {
      if (System.console() != null) {
        return System.console().readLine(format, args);
      }

      System.out.print(String.format(format, args));
      BufferedReader reader = new BufferedReader(new InputStreamReader(
            System.in));
      return reader.readLine();
    }
  }


  public static void writeDecimal(DataOutputStream dos, BigDecimal bd)
      throws IOException
    {
      if (bd == null) {
        // NOTE: I am using 2 zeros to indicate a null BigDecimal
        dos.writeInt(0);  // 0 for scale
        dos.writeInt(0);  // 0 for length
        return;
      }

      // write scale first
      int scale = bd.scale();
      dos.writeInt(scale);

      BigInteger bi = bd.unscaledValue();
      byte[] ba = bi.toByteArray();

      // write the size of the byte array
      dos.writeInt(ba.length);

      // write the content
      dos.write(ba, 0, ba.length);
    }


  public static BigDecimal readBigDecimal(DataInputStream dis)
      throws IOException
    {
      int scale  = dis.readInt();
      int length = dis.readInt();

      if (scale == 0 && length == 0) {
        return null;
      }

      byte[] ba = new byte[length];
      dis.readFully(ba);

      BigInteger bi = new BigInteger(ba); // unscaled value
      BigDecimal bd = new BigDecimal(bi, scale);
      return bd;
    }


    public static InputStream decompressStreamIfNeeded(InputStream input) 
        throws IOException {
        PushbackInputStream pb = new PushbackInputStream( input, 2 ); //we need a pushbackstream to look ahead
        byte [] signature = new byte[2];
        int len = pb.read( signature ); //read the signature
        pb.unread( signature, 0, len ); //push back the signature to the stream
        if( signature[ 0 ] == (byte) 0x1f && signature[ 1 ] == (byte) 0x8b ) //check if matches standard gzip magic number
          return new GZIPInputStream( pb );
        else 
          return pb;
    }


    /**
     * Returns an InputStream object to a file in the resources/ directory of the containing jar file
     */
    public static InputStream accessFile(String szFile) {
      InputStream input = CommonUtils.class.getResourceAsStream("/resources/" + szFile);
      if (input == null) {
        input = CommonUtils.class.getClassLoader().getResourceAsStream(szFile);
      }

      return input;
    }
}
