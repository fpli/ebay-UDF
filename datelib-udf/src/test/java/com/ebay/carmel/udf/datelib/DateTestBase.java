package com.ebay.carmel.udf.datelib;

import java.util.TimeZone;
import java.util.concurrent.Callable;

public class DateTestBase {

  public <T> T withTimeZone(TimeZone timeZone, Callable<T> callable) throws Exception {
    TimeZone defaultZone = TimeZone.getDefault();
    TimeZone.setDefault(timeZone);
    try {
      return callable.call();
    } finally {
      TimeZone.setDefault(defaultZone);
    }
  }

  public <T> T withUtcTimeZone(Callable<T> callable) throws Exception {
    return withTimeZone(TimeZone.getTimeZone("UTC"), callable);
  }
}
