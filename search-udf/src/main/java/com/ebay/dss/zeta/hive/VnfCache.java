package com.ebay.dss.zeta.hive;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hive.ql.exec.UDF;
import com.ebay.hadoop.scalaplatform.helpers.SojHelpers;
import com.ebay.hadoop.scalaplatform.spark.SparkUtils;
import java.util.*;
import java.io.*;
import java.util.zip.GZIPInputStream;

public class VnfCache {

    private static long ms_lStartTime = System.currentTimeMillis();

    private HashSet<String> m_hs = null;

    private VnfCache(String szFileName) {
        m_hs = buildHashSet(szFileName);
    }


    static void quietlyClose(InputStream is) 
    {
        try {
            is.close();
        }
        catch (Throwable t) {
        }
    }


    static void quietlyClose(Reader r) 
    {
        try {
            r.close();
        }
        catch (Throwable t) {
        }
    }


    private void addMarker(String szMarker)
    {
        m_hs.add(szMarker.trim());
    }


    static HashSet<String> buildHashSet(String szFileName) 
    {
        InputStream is = null;
        GZIPInputStream gis = null;
        BufferedReader br = null;

        HashSet<String> hs = new HashSet<String>();

        try {
            is = accessFile(szFileName);
            gis = new GZIPInputStream(is);
            br = new BufferedReader(new InputStreamReader(gis));

            String szLine = null;

            while ( (szLine = br.readLine()) != null) {
                szLine = java.net.URLDecoder.decode(szLine.trim(), "UTF-8");
                hs.add(szLine);
            }

            // Files.lines(fileToLoad, ENCODING) .map(Function.identity()) .collect(Collectors.toSet());

        }
        catch (Throwable t) {
        }
        finally {
            quietlyClose(br);
            quietlyClose(gis);
            quietlyClose(is);
        }

        return hs;
    }
    

    public static InputStream accessFile(String szFile) 
    {
        InputStream input = VnfCache.class.getResourceAsStream("/resources/" + szFile);
        if (input == null) {
            input = VnfCache.class.getClassLoader().getResourceAsStream(szFile);
        }

        return input;
    }


    private static VnfCache ms_vcUS20 = null;
    private static VnfCache ms_vcUK20 = null;
    private static VnfCache ms_vcUS30 = null;
    private static VnfCache ms_vcUK30 = null;
    private static VnfCache ms_vcDE30 = null;
    private static VnfCache ms_vcDef  = null;

    static {
        ms_vcUS20 = new VnfCache("vnf20us.txt.gz");  ms_vcUS20.addMarker("ebay_marker:vnf20us");
        ms_vcUK20 = new VnfCache("vnf20uk.txt.gz");  ms_vcUK20.addMarker("ebay_marker:vnf20uk");
        ms_vcUS30 = new VnfCache("vnf30us.txt.gz");  ms_vcUS30.addMarker("ebay_marker:vnf30us");
        ms_vcUK30 = new VnfCache("vnf30uk.txt.gz");  ms_vcUK30.addMarker("ebay_marker:vnf30uk");
        ms_vcDE30 = new VnfCache("vnf30de.txt.gz");  ms_vcDE30.addMarker("ebay_marker:vnf30de");

        ms_vcDef  = new VnfCache("vnfdummy.txt.gz"); ms_vcDef.addMarker("ebay_marker:vnfdummy");
    }


    public static VnfCache getUsVnf20Instance()
    {
        return ms_vcUS20;
    }


    public static VnfCache getInstance(String tag)
    {
        if ("20us".equalsIgnoreCase(tag)) { return ms_vcUS20; }
        if ("20uk".equalsIgnoreCase(tag)) { return ms_vcUK20; }
        if ("30us".equalsIgnoreCase(tag)) { return ms_vcUS30; }
        if ("30uk".equalsIgnoreCase(tag)) { return ms_vcUK30; }
        if ("30de".equalsIgnoreCase(tag)) { return ms_vcDE30; }

        return ms_vcDef;
    }


    public long getSize()
    {
        return m_hs.size();
    }


    /**
     * Returns "1" if the entry is contained in the cache. 
     * Returns "0" otherwise.
     * @param decodeFirst if non zero, the input string will be URL decoded first before checking the cache.
     */
    public String contains(String k, String v, int decodeFirst)
    {
        return contains(k + ":" + v, decodeFirst);
    }


    /**
     * Returns "1" if the entry is contained in the cache. 
     * Returns "0" otherwise.
     * @param decodeFirst if non zero, the input string will be URL decoded first before checking the cache.
     */
    public String contains(String s, int decodeFirst)
    {
        if (s == null || s.length() == 0) {
            return "0";
        }

        try {
            if (decodeFirst != 0) {
                if (m_hs.contains(java.net.URLDecoder.decode(s.trim(), "UTF-8"))) {
                    return "1";
                }
            }
            else {
                if (m_hs.contains(s.trim())) {
                    return "1";
                }
            }

        }
        catch (Throwable t) {
            return "0";
        }

        return "0";
    }


    public static void main(String[] szArgs)
        throws Exception
    {
        VnfCache vc = VnfCache.getUsVnf20Instance();
        System.out.println("" + (System.currentTimeMillis() - ms_lStartTime));
        System.out.println("cache size " + vc.getSize());

        System.out.println("contains: Material:blown%20glass = " + vc.contains("Material:blown%20glass", 0));
        System.out.println("contains: Material:blown%20glass = " + vc.contains("Material:blown%20glass", 1));
    }
}
