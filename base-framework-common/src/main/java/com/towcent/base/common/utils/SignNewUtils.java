package com.towcent.base.common.utils;

import com.egzosn.pay.common.util.str.StringUtils;

import org.apache.http.message.BasicNameValuePair;

import java.util.*;

public enum SignNewUtils {
    MD5 {
        public String createSign(String content, String key, String characterEncoding) {
            return com.egzosn.pay.common.util.sign.encrypt.MD5.sign(content, key, characterEncoding);
        }

        public boolean verify(String text, String sign, String key, String characterEncoding) {
            return com.egzosn.pay.common.util.sign.encrypt.MD5.verify(text, sign, key, characterEncoding);
        }
    },
    RSA {
        public String createSign(String content, String key, String characterEncoding) {
            return com.egzosn.pay.common.util.sign.encrypt.RSA.sign(content, key, characterEncoding);
        }

        public boolean verify(String text, String sign, String publicKey, String characterEncoding) {
            return com.egzosn.pay.common.util.sign.encrypt.RSA.verify(text, sign, publicKey, characterEncoding);
        }
    },
    RSA2 {
        public String createSign(String content, String key, String characterEncoding) {
            return com.egzosn.pay.common.util.sign.encrypt.RSA2.sign(content, key, characterEncoding);
        }

        public boolean verify(String text, String sign, String publicKey, String characterEncoding) {
            return com.egzosn.pay.common.util.sign.encrypt.RSA2.verify(text, sign, publicKey, characterEncoding);
        }
    };

    private SignNewUtils() {
    }

    public static String parameterText(@SuppressWarnings("rawtypes") Map parameters) {
        return parameterText(parameters, "&");
    }

    public static String parameterText(@SuppressWarnings("rawtypes") Map parameters, String separator) {
        return parameterText(parameters, separator, "sign", "key", "sign_type");
    }

    @SuppressWarnings({ "rawtypes", "unused", "unchecked" })
	public static String parameterText(Map parameters, String separator, String... ignoreKey) {
        if (parameters == null) {
            return "";
        } else {
            StringBuffer sb = new StringBuffer();
            if (null != ignoreKey) {
                Arrays.sort(ignoreKey);
            }

            if (parameters instanceof SortedMap) {
                Iterator i$ = parameters.keySet().iterator();

                while(true) {
                    String k;
                    Object v;
                    do {
                        do {
                            do {
                                if (!i$.hasNext()) {
                                    if (sb.length() > 0 && !"".equals(separator)) {
                                        sb.deleteCharAt(sb.length() - 1);
                                    }

                                    return sb.toString();
                                }

                                k = (String)i$.next();
                                v = parameters.get(k);
                            } while(null == v);
                        } while("".equals(v.toString().trim()));
                    } while(null != ignoreKey && Arrays.binarySearch(ignoreKey, k) >= 0);

                    sb.append(k).append("=").append(v.toString().trim()).append(separator);
                }
            } else {
                List<String> keys = new ArrayList(parameters.keySet());
                Collections.sort(keys);
                Iterator i$ = keys.iterator();

                while(true) {
                    String k;
                    String valueStr;
                    do {
                        do {
                            label96:
                            do {
                                while(i$.hasNext()) {
                                    k = (String)i$.next();
                                    valueStr = "";
                                    Object o = parameters.get(k);
                                    if (o instanceof String[]) {
                                        String[] values = (String[])((String[])o);
                                        if (null == values) {
                                            continue;
                                        }

                                        int i = 0;

                                        while(true) {
                                            if (i >= values.length) {
                                                continue label96;
                                            }

                                            String value = values[i].trim();
                                            if (!"".equals(value)) {
                                                valueStr = valueStr + (i == values.length - 1 ? value : value + ",");
                                            }

                                            ++i;
                                        }
                                    }

                                    if (o != null) {
                                        valueStr = o.toString();
                                    }
                                    continue label96;
                                }

                                if (sb.length() > 0) {
                                    sb.deleteCharAt(sb.length() - 1);
                                }

                                return sb.toString();
                            } while(null == valueStr);
                        } while("".equals(valueStr.toString().trim()));
                    } while(null != ignoreKey && Arrays.binarySearch(ignoreKey, k) >= 0);

                    sb.append(k).append("=").append(valueStr).append(separator);
                }
            }
        }
    }
    @SuppressWarnings("rawtypes") 
    public static String parameters2MD5Str(Object parameters, String separator) {
        StringBuffer sb = new StringBuffer();
        if (parameters instanceof LinkedHashMap) {
            @SuppressWarnings("unchecked")
			Set<String> keys = ((LinkedHashMap)parameters).keySet();
            Iterator i$ = keys.iterator();

            while(i$.hasNext()) {
                String key = (String)i$.next();
                String val = ((LinkedHashMap)parameters).get(key).toString();
                if (StringUtils.isNotBlank(val)) {
                    sb.append(val).append(separator);
                }
            }
        } else if (parameters instanceof List) {
            Iterator i$ = ((List)parameters).iterator();

            while(i$.hasNext()) {
                BasicNameValuePair bnv = (BasicNameValuePair)i$.next();
                if (StringUtils.isNotBlank(bnv.getValue())) {
                    sb.append(bnv.getValue()).append(separator);
                }
            }
        }

        return StringUtils.isBlank(sb.toString()) ? "" : sb.deleteCharAt(sb.length() - 1).toString();
    }

    public static String randomStr() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public String sign(@SuppressWarnings("rawtypes") Map parameters, String key, String characterEncoding) {
        return this.createSign(parameterText(parameters, "&"), key, characterEncoding);
    }

    public String sign(@SuppressWarnings("rawtypes") Map parameters, String key, String separator, String characterEncoding) {
        return this.createSign(parameterText(parameters, separator), key, characterEncoding);
    }

    public abstract String createSign(String var1, String var2, String var3);

    public boolean verify(@SuppressWarnings("rawtypes") Map params, String sign, String key, String characterEncoding) {
        return this.verify(parameterText(params), sign, key, characterEncoding);
    }

    public abstract boolean verify(String var1, String var2, String var3, String var4);
}
