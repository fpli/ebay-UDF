package com.ebay.hadoop.udf.soj;

import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;

public class SojTagFetcher2 extends UDF {

    private ISojNvl sojNvl;

    public SojTagFetcher2() {
        sojNvl = new FastSojNvlImpl();
    }

    public Text evaluate(final Text payloadText, String key) {
        if (payloadText == null || key == null) {
            return null;
        }

        String payload = payloadText.toString();
        String tagValue = null;
        try {
            tagValue = sojNvl.getTagValue(payload, key);
        } catch (Exception e) {
            tagValue = "-99999";
        }
        if (tagValue == null) {
            return null;
        } else {
            return new Text(tagValue);
        }
    }


    public interface ISojNvl {
        public String getTagValue(String value, String key);

        public String ToString();
    }

    public class FastSojNvlImpl implements ISojNvl {
        private long _startWithMatched = 0;
        private long _indexOfMatched = 0;

        public long startWithMatched() {
            return _startWithMatched;
        }

        public long indexOfMatched() {
            return _indexOfMatched;
        }

        @Override
        public String getTagValue(String querystring, String param) {
            if (querystring == null || param == null || param.length() == 0) {
                return null;
            }

            String v = null;
            int l = param.length();
            if (querystring.startsWith(param) && querystring.length() > param.length() && querystring.charAt(l) == '=') {
                int pos = querystring.indexOf('&');
                if (pos > 0) {
                    v = querystring.substring(l + 1, pos);
                } else {
                    v = querystring.substring(l + 1);
                }
                _startWithMatched++;
            }

            if (v == null) {
                if ((querystring.startsWith("_" + param) || querystring.startsWith("!" + param)) && (querystring.length() > param.length() + 1) && querystring.charAt(l + 1) == '=') {
                    int pos = querystring.indexOf('&');
                    if (pos > 0) {
                        v = querystring.substring(l + 2, pos);
                    } else {
                        v = querystring.substring(l + 2);
                    }
                    _startWithMatched++;
                }
            }

            if (v == null) {
                int begin = querystring.indexOf("&" + param + "=");
                if (begin >= 0) {
                    int begin2 = begin + l + 2;
                    int pos = querystring.indexOf('&', begin2);
                    if (pos > 0) {
                        v = querystring.substring(begin2, pos);
                    } else {
                        v = querystring.substring(begin2);
                    }
                    _indexOfMatched++;
                }
            }

            if (v == null) {
                int begin_1 = querystring.indexOf("&_" + param + "=");
                int begin_2 = querystring.indexOf("&!" + param + "=");

                int begin = -1;
                if (begin_1 >= 0) {
                    begin = begin_1;
                } else if (begin_2 >= 0) {
                    begin = begin_2;
                }

                if (begin >= 0) {
                    int begin2 = begin + l + 3;
                    int pos = querystring.indexOf('&', begin2);
                    if (pos > 0) {
                        v = querystring.substring(begin2, pos);
                    } else {
                        v = querystring.substring(begin2);
                    }
                }
                _indexOfMatched++;
            }

            if (v != null && v.length() == 0) {
                return null;
            }
            return v;
        }

        @Override
        public String ToString() {
            return this.getClass().getName();
        }
    }


}