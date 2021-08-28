package com.ebay.hadoop.udf.soj;

import com.ebay.hadoop.udf.tags.ETLUdf;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

@ETLUdf(name = "soj_url_decode_escapes")
public class SOJURLDecodeEscapeHive extends UDF{

	public Text evaluate(Text url, String enc){
		if(url == null)
			return null;
		String result = javaNetUrlDecode(url.toString(),  enc);
		if(result == null)
			return null;
		else return new Text(result);
	}

	public static int[] hexlookup = new int[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, -1, -1, -1, -1, -1, -1, -1, 10, 11, 12, 13, 14, 15, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 10, 11, 12, 13, 14, 15, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};

	public static String decodeEscapes(String url, char esc_char) {
		if (StringUtils.isBlank(url)) {
			return null;
		} else {
			int i = 0;
			StringBuilder ret = new StringBuilder();
			char[] src = url.toCharArray();
			int srcend = url.length();

			while(i < srcend) {
				if (src[i] == esc_char) {
					if (i + 2 >= srcend) {
						ret.append(src, i, srcend - i);
						break;
					}

					int h1 = sg_get_hex(src[i + 1]);
					int h2 = sg_get_hex(src[i + 2]);
					if (h1 >= 0 && h2 >= 0) {
						h1 <<= 4;
						ret.append((char)(h1 | h2));
					} else {
						ret.append(src, i, 3);
					}

					i += 3;
				} else {
					ret.append(src[i++]);
				}
			}

			return ret.toString();
		}
	}

	private static int sg_get_hex(char c) {
		int res = -1;
		if (c > 0 && c < 127) {
			res = hexlookup[c];
		}

		return res;
	}

	public static String javaNetUrlDecode(String url, String enc) {
		if (StringUtils.isBlank(url)) {
			return null;
		} else {
			if (enc.equals("%")) {
				return decodeEscapes(url, '%');
			} else {
				try {
					return URLDecoder.decode(url, enc);
				} catch (UnsupportedEncodingException var3) {
					return decodeEscapes(url, '%');
				} catch (IllegalArgumentException var4) {
					return decodeEscapes(url, '%');
				}
			}
		}
	}
}
