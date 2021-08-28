package com.ebay.carmel.udf.datelib;

import org.apache.hadoop.io.Text;
import org.junit.Test;
import parquet.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

import static org.junit.Assert.assertEquals;

public class WeekStartDateTest extends DateTestBase {

    @Test
    public void test() throws Exception {
        withUtcTimeZone(() -> {
            WeekStartDate weekStartDate = new WeekStartDate();
            assertEquals("2020-03-15", weekStartDate.evaluate(new Text("2020-03-17")).toString());
            assertEquals("2020-03-15", weekStartDate.evaluate(new Text("2020-03-17 12:13:59")).toString());
            assertEquals("2020-03-15", weekStartDate.evaluate(new Text("1584374")).toString());
            return null;
        });
    }

    @Test
    public void multipleThreadTest() throws Exception {
        withUtcTimeZone(() -> {
            Callable<Exception> exceptionCallable = new Callable<Exception>() {
                @Override
                public Exception call() {
                    WeekStartDate weekStartDate = new WeekStartDate();
                    String result = weekStartDate.evaluate(new Text("2020-07-24")).toString();
                    if(!result.equalsIgnoreCase("2020-07-19")) {
                        return new Exception("expected 2020-07-19 but returned "+ result);
                    }
                    return null;
                }
            };

            ThreadPoolExecutor pool = new ThreadPoolExecutor(10, 10, 6000, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(10));
            List<Future<Exception>> futureList = new ArrayList<>();
            for(int i = 0 ; i < 10 ; i++) {
                futureList.add(pool.submit(exceptionCallable));
            }
            pool.shutdown();
            pool.awaitTermination(10, TimeUnit.MINUTES);
            for(int i = 0 ; i< 10 ; i++ ) {
                if (null != futureList.get(i).get()) {
                    throw futureList.get(i).get();
                }
            }
            return null;
        });
    }
}
