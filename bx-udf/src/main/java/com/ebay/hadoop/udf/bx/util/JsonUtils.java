package com.ebay.hadoop.udf.bx.util;


import com.ebay.hadoop.udf.bx.exception.JsonConvertException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created  05/15/2017 | 18:54
 *
 * @author : Johnnie Zhang
 * @version : 1.0.0
 * @since : 1.0.0
 */
public class JsonUtils {
    private static Logger log = LoggerFactory.getLogger(JsonUtils.class);
    private static ObjectMapper objectMapper = new ObjectMapper();

    private JsonUtils() {

    }

    /**
     * Convert object to JSON
     *
     * @param obj object
     * @param <T> Class of instance
     * @return JSON string
     */
    public static <T> String toJson(T obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new JsonConvertException("Convert " + obj.getClass() + " to json failed, " + e.getMessage(),
                    e.getCause());
        }
    }


    /**
     * Convert object from JSON
     *
     * @param json  Json string
     * @param clazz Class of instance
     * @param <T>   Class of instance
     * @return Instance of T
     */
    public static <T> T fromJson(String json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new JsonConvertException("convert json to object failed ," + e.getMessage(), e.getCause());
        }
    }

    /**
     * Convert object from InputStream
     * <p>
     *
     * @param input input stream
     * @param clazz Class of instance
     * @param <T>   Class of instance
     * @return Instance of T
     */
    public static <T> T fromJson(InputStream input, Class<T> clazz) {
        try {
            return objectMapper.readValue(input, clazz);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new JsonConvertException("convert InputStream to object failed," + e.getMessage(),
                    e.getCause());
        }
    }


    /**
     * Convert object from JSON String
     *
     * @param json          Json String
     * @param typeReference typeReference of return object
     * @return Instance of T
     */
    public static <T> T fromJson(String json, TypeReference<T> typeReference) {
        try {
            return objectMapper.readValue(json, typeReference);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new JsonConvertException("convert json to object failed, " + e.getMessage(), e.getCause());
        }
    }

    /**
     * Convert object from InputStream
     * <p>
     *
     * @param input         input stream
     * @param typeReference typeReference of return object
     * @param <T>           Class of instance
     * @return Instance of T
     */
    public static <T> T fromJson(InputStream input, TypeReference<T> typeReference) {
        try {
            return objectMapper.readValue(input, typeReference);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new JsonConvertException("convert InputStream to object failed," + e.getMessage(), e.getCause());
        }
    }
}
