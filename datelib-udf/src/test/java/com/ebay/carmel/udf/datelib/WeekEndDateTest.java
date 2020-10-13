package com.ebay.carmel.udf.datelib;

import org.apache.hadoop.io.Text;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import static org.junit.Assert.assertEquals;

public class WeekEndDateTest {

    @Test
    public void test() {
        WeekEndDate weekEndDate = new WeekEndDate();
        assertEquals("2020-03-21", weekEndDate.evaluate(new Text("2020-03-17")).toString());
        assertEquals("2020-03-21", weekEndDate.evaluate(new Text("2020-03-17 12:13:59")).toString());
        assertEquals("2020-03-21", weekEndDate.evaluate(new Text("1584437")).toString());

    }

    @Test
    public void multipleThreadTest() throws Exception {
        Callable<Exception> exceptionCallable = new Callable<Exception>() {
            @Override
            public Exception call() {
                WeekEndDate weekEndDate = new WeekEndDate();
                String result = weekEndDate.evaluate(new Text("2020-07-24")).toString();
                if(!result.equalsIgnoreCase("2020-07-25")) {
                    return new Exception("expected 2020-07-25 but returned "+ result);
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
    }

}
