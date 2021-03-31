package com.ebay.hadoop.udf.bx.exception;

/**
 * Created  5/16/18 | 12:43
 *
 * @author : Johnnie Zhang
 * @version : 1.0.0
 */
public class JsonConvertException extends RuntimeException {

    private static final long serialVersionUID = 7604638818059673389L;

    public JsonConvertException() {
        super();
    }

    public JsonConvertException(String message) {
        super(message);
    }

    public JsonConvertException(String message, Throwable cause) {
        super(message, cause);
    }

    public JsonConvertException(Throwable cause) {
        super(cause);
    }
}
