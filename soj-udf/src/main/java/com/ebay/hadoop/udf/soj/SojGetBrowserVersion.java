package com.ebay.hadoop.udf.soj;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hive.ql.exec.UDF;

/**
 * 
 * @author naravipati
 * 
 *         This UDF takes userAgent as input and returns Browser Version.
 *
 */

public class SojGetBrowserVersion extends UDF {

	public String evaluate(String userAgent) {

		// check useragent is NULL or ""

		if (StringUtils.isBlank(userAgent)) {

			return new String("NULL UserAgent");

		}

		else {

			if (userAgent.contains("Firefox")) {

				if (userAgent.contains("Firefox/1.5")) {
					return new String("1.5");
				}

				if (userAgent.contains("Firefox/2.0")) {
					return new String("2.0");
				}
				if (userAgent.contains("Firefox/3.0")) {
					return new String("3.0");
				}
				if (userAgent.contains("Firefox/3.5")) {
					return new String("3.5");
				}
				if (userAgent.contains("Firefox/3.6")) {
					return new String("3.6");
				}

				return new String("<1.5");
			}

			if (userAgent.contains("Opera")) {

				if (userAgent.contains("Opera/10") || userAgent.contains("Opera 10")) {
					return new String("10");
				}
				if (userAgent.contains("Opera/9") || userAgent.contains("Opera 9")) {
					return new String("9");
				}
				if (userAgent.contains("Opera/8") || userAgent.contains("Opera 8")) {
					return new String("7.x-8.x");
				}
				if (userAgent.contains("Opera/7") || userAgent.contains("Opera 7")) {
					return new String("7.x-8.x");
				}

				return new String("LowerBrowserVersion");
			}

			if (userAgent.contains("Safari") && !userAgent.contains("Chrome")) {

				if (userAgent.contains("Safari/4")) {
					return new String("2.x");
				}
				if (userAgent.contains("Safari/1")) {
					return new String("1.1-1.2");
				}
				if (userAgent.contains("Safari/3")) {
					return new String("1.3");
				}
				if (userAgent.contains("Safari/8")) {
					return new String("1.0");
				}

			}

			if (userAgent.contains("WebTV")) {

				if (userAgent.contains("WebTV/1")) {
					return new String("1.0");
				}

				if (userAgent.contains("WebTV/2")) {
					return new String("2.x");
				}
			}

			if (userAgent.contains("Netscape")) {

				if (userAgent.contains("Netscape6/6")) {
					return new String("4.x-6.x");
				}

				if (userAgent.contains("Netscape/7.02")) {
					return new String("7.02");
				}
				if (userAgent.contains("Netscape/7")) {
					return new String("7.x-8.x");
				}
				if (userAgent.contains("Netscape/8")) {
					return new String("7.x-8.x");
				}
			}

			if (userAgent.contains("MSIE")) {

				if (userAgent.contains("MSIE 9.")) {
					return new String("9.x");
				}
				if (userAgent.contains("MSIE 8.")) {
					return new String("8.x");
				}
				if (userAgent.contains("MSIE 7.")) {
					return new String("7.x");
				}
				if (userAgent.contains("MSIE 6.")) {
					return new String("6.x");
				}
				if (userAgent.contains("MSIE 5.5")) {
					return new String("5.5");
				}
				if (userAgent.contains("MSIE 5.2")) {
					return new String("5.2.x");
				}
				if (userAgent.contains("MSIE 5.1")) {
					return new String("5.1.x");
				}
				if (userAgent.contains("MSIE 5.")) {
					return new String("5.x");
				}
				if (userAgent.contains("MSIE 4.")) {
					return new String("4.x");
				}
				return new String("LowerBrowserVersion");
			}

			if (userAgent.contains("Mozilla/4")) {

				return new String("4.x-6.x");
			}

			if (userAgent.contains("Chrome")) {

				if (userAgent.contains("Chrome/0.")) {
					return new String("0.x");
				}
				if (userAgent.contains("Chrome/1.")) {
					return new String("1.x");
				}
				if (userAgent.contains("Chrome/2.")) {
					return new String("2.x");
				}
				if (userAgent.contains("Chrome/3.")) {
					return new String("3.x");
				}
				if (userAgent.contains("Chrome/4.")) {
					return new String("4.x");
				}

				return new String("Unknown Browser Version");
			}

			return new String("Unknown Browser Version");
		}

	}

}
