package com.ebay.hadoop.udf.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class ResourceUtils {

    public static String getResourceAsString(String resource) throws IOException {
        InputStream ins = null;
        BufferedReader reader = null;
        StringBuilder ddlBuilder = new StringBuilder();
        String line;
        try {
            ins = ResourceUtils.class.getResourceAsStream(resource);
            reader = new BufferedReader(new InputStreamReader(ins, Charset.forName("UTF-8")));
            while ((line = reader.readLine()) != null) {
                ddlBuilder.append(line);
            }
            return ddlBuilder.toString();
        } finally {
            if (reader != null) {
                reader.close();
                reader = null;
            }
            ins = null;
        }
    }
}
