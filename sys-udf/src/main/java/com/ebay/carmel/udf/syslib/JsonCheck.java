package com.ebay.carmel.udf.syslib;

import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

public class JsonCheck extends UDF {

    public Text evaluate(final Text jsonstring) {
        if(jsonstring == null) return null;
        try {
            String jsonString = jsonstring.toString().trim();
            if(StringUtils.isBlank(jsonString)) return new Text("OK");
            if (jsonString.equalsIgnoreCase("true") || jsonString.equalsIgnoreCase("false"))
                return new Text("INVALID");
            try {
                Double.parseDouble(jsonString);
                return new Text("INVALID");
            } catch (NumberFormatException e) { }
            JsonParser parser = new ObjectMapper().getJsonFactory()
              .createJsonParser(jsonString);
            while (parser.nextToken() != null) { }
            return new Text("OK");
        } catch (JsonProcessingException jsonProc) {
            return new Text("INVALID");
        } catch (IOException e) {
            return new Text("INVALID");
        } catch (NullPointerException npe) {
            return null;
        }
    }
}
