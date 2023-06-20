package com.ebay.hadoop.udf.tags;

import java.lang.annotation.*;

/**
 * Note that this UDF is also used by ETL jobs.
 *
 * If there is any behavior change, please contact Hadoop team.
 */
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.TYPE})
public @interface ETLUdf {
  String name() default "";

  String description() default "";
}
