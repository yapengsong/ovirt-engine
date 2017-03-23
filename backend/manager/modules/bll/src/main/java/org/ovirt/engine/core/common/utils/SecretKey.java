package org.ovirt.engine.core.common.utils;

public class SecretKey {

    // 移位解密
    public static String decode(String tmp) {

        StringBuilder sb = new StringBuilder(tmp);
        tmp = sb.reverse().toString();
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < tmp.length(); i++) {
            char c = tmp.charAt(i);

            if ('-' == c) {
                c = '-';
            } else if ('0' == c || '1' == c || '2' == c) {
                c = (char) (c + 7);
            } else if ('A' == c || 'B' == c || 'C' == c) {
                c = (char) (c + 23);
            } else {
                c = (char) (c - 3);
            }

            result.append(c);
        }
        return result.toString().toUpperCase();
    }

    // 32进制转16进制
    public static String to16String(String tmp) {
        String[] s = tmp.split("-");
        StringBuffer sb = new StringBuffer();
        for (String p : s) {
            // Long.toHexString(p);
            Long l = Long.parseLong(p, 32);
            String x = l.toString(l, 16);
            if (x.length() < 8) {
                int n = 8 - (x.length());
                for (int i = 0; i < n; i++) {
                    x = "0" + x;
                }
            }
            sb.append(x);
            sb.append("-");
        }
        sb = sb.deleteCharAt(sb.length() - 1);
        return sb.toString().toUpperCase();
    }

    // 16进制UUID转32进制
    public static String to32String(String tmp) {
        String[] s = tmp.split("-");
        StringBuffer sb = new StringBuffer();
        for (String p : s) {
            // Long.toHexString(p);
            Long l = Long.parseLong(p, 16);
            String x = l.toString(l, 32);
            sb.append(x);
            sb.append("-");
        }
        sb = sb.deleteCharAt(sb.length() - 1);
        return sb.toString().toUpperCase();
    }

    // UUID去横线分四段
    public static String part(String tmp) {
        String s = tmp.replace("-", "");
        String one = s.substring(0, 8);
        String tow = s.substring(8, 16);
        String three = s.substring(16, 24);
        String four = s.substring(24, 32);
        StringBuffer sb = new StringBuffer();

        return one + "-" + tow + "-" + three + "-" + four;
    }

}
