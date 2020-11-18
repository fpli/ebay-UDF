package com.ebay.risk.normalize;

public class NormalizationException extends Exception{
    public NormalizationException(String message){
      super(message);
    }
    public NormalizationException(Throwable cause){
      super(cause);
    }

    public NormalizationException(String message, Throwable cause){
      super(message, cause);
    }

    public NormalizationException(){}

}
