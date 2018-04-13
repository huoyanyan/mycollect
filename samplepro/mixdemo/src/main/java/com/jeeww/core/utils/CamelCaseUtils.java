package com.jeeww.core.utils;

/**
 * 类说明:驼峰命名转换.
 * @author 蒋文武
 */
public final class CamelCaseUtils {
    /**
     * 默认构造函数.
     */
    private CamelCaseUtils() {
    }

    /**
     * 分隔字符.
     */
    private static final char SEPARATOR = '_';

    /**
     * 功能说明:驼峰名称转换成下划线名称.
     * @param s 传人的驼峰名称.
     * @return String 返回下划线命名模式的名称
     */
    public static String toUnderlineName(final String s) {
        if (s == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        boolean upperCase = false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            boolean nextUpperCase = true;
            if (i < (s.length() - 1)) {
                nextUpperCase = Character.isUpperCase(s.charAt(i + 1));
            }
            if ((i >= 0) && Character.isUpperCase(c)) {
                if (!upperCase || !nextUpperCase) {
                    if (i > 0) {
                        sb.append(SEPARATOR);
                    }
                }
                upperCase = true;
            } else {
                upperCase = false;
            }
            sb.append(Character.toLowerCase(c));
        }
        return sb.toString();
    }

    /**
     * 功能说明:驼峰名称转换成下划线名称.
     * @param underlineName 传人的驼峰名称.
     * @return String 返回下划线命名模式的名称
     */
    public static String toCamelCase(final String underlineName) {
        if (underlineName == null) {
            return null;
        }
        String s = underlineName.toLowerCase();
        StringBuilder sb = new StringBuilder(s.length());
        boolean upperCase = false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (c == SEPARATOR) {
                upperCase = true;
            } else if (upperCase) {
                sb.append(Character.toUpperCase(c));
                upperCase = false;
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * 功能说明:驼峰名称转换成下划线名称.
     * @param underlineName 传人的驼峰名称.
     * @return String 返回下划线命名模式的名称
     */
    public static String toCapitalizeCamelCase(final String underlineName) {
        if (underlineName == null) {
            return null;
        }
        String s = toCamelCase(underlineName);
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }
}
