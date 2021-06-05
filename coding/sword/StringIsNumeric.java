package com.wjd.sword;

import java.util.Arrays;

/**
 * 请实现一个函数用来判断字符串是否表示数值（包括整数和小数）。
 * 例如，字符串"+100","5e2","-123","3.1416"和"-1E-16"都表示数值。
 * 但是"12e","1a3.14","1.2.3","+-5"和"12e+4.3"都不是。
 */
public class StringIsNumeric {

    public static void main(String[] args) {
        char[] str = "--6".toCharArray();
        System.out.println(isNumeric(str));
    }

    public static boolean isNumeric(char[] str) {
        if (str == null || str.length == 0) {
            return false;
        }

        int pIndex = -1, eIndex = -1;
        for (int i = 0; i < str.length; i++) {
            if (str[i] == '.') {
                pIndex = i;
            }
            if (str[i] == 'e' || str[i] == 'E') {
                eIndex = i;
            }
        }

        boolean res;

        // 整数部分
        char[] inte;
        if (pIndex != -1) {
            inte = Arrays.copyOfRange(str, 0, pIndex);
        } else if(eIndex != -1) {
            inte = Arrays.copyOfRange(str, 0, eIndex);
        } else {
            inte = str;
        }
        res = isInteger(inte);

        // 小数部分
        if (res && pIndex != -1) {
            char[] decimal;
            if (pIndex < eIndex) {
                decimal = Arrays.copyOfRange(str, pIndex + 1, eIndex);
            } else {
                decimal = Arrays.copyOfRange(str, pIndex + 1, str.length);
            }
            res = res && isDecimal(decimal);
        }

        // 指数部分
        if (res && eIndex != -1) {
            char[] exp = Arrays.copyOfRange(str, eIndex + 1, str.length);
            res = res && isExp(exp);
        }

        return res;
    }

    /**
     * 整数部分
     *
     * @param str
     * @return
     */
    public static boolean isInteger(char[] str) {
        if (str.length == 0) {
            return true;
        }

        int index = 0;
        if (str[index] == '-' || str[index] == '+') {
            index++;
        }

        for (int i = index; i < str.length; i++) {
            char ch = str[i];
            if (ch < '0' || ch > '9') {
                return false;
            }
        }

        return true;
    }

    /**
     * 小数部分
     *
     * @param str
     * @return
     */
    private static boolean isDecimal(char[] str) {
        if (str.length == 0) {
            return false;
        }

        for (int i = 0; i < str.length; i++) {
            char ch = str[i];
            if (ch < '0' || ch > '9') {
                return false;
            }
        }

        return true;
    }

    /**
     * 指数部分
     *
     * @param str
     * @return
     */
    private static boolean isExp(char[] str) {
        if (str.length == 0) {
            return false;
        }

        int index = 0;
        if (str[index] == '-' || str[index] == '+') {
            index++;
        }

        if (index < str.length) {
            for (int i = index; i < str.length; i++) {
                char ch = str[i];
                if (ch < '0' || ch > '9') {
                    return false;
                }
            }
            return true;
        }

        return false;
    }
}
