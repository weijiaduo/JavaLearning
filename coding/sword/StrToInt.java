package com.wjd.sword;

/**
 * 将一个字符串转换成一个整数(实现Integer.valueOf(string)的功能，
 * 但是string不符合数字要求时返回0)，要求不能使用字符串转换整数的库函数。
 * 数值为0或者字符串不是一个合法的数值则返回0。
 *
 */
public class StrToInt {

    public static void main(String[] args) {
        String str = "2147483648";
        System.out.println(strToInt(str));
    }

    public static int strToInt(String str) {
        if (str == null || str.isEmpty()) {
            return 0;
        }

        boolean isNegative = false;
        int startIndex = 0;
        if (str.charAt(0) == '+') {
            isNegative = false;
            startIndex = 1;
        }
        if (str.charAt(0) == '-') {
            isNegative = true;
            startIndex = 1;
        }

        int num = 0, c = Integer.MIN_VALUE / 10, r = Integer.MIN_VALUE % 10;
        for (int i = startIndex; i < str.length(); i++) {
            int digit = str.charAt(i) - '0';
            // 非数字以及超出负数范围
            if (digit < 0 || digit > 9 || num < c || (num == c && -digit < r)) {
                return 0;
            }
            num = num * 10 - digit;
        }

        if (!isNegative) {
            // 超出正数范围
            if (num == Integer.MIN_VALUE) {
                num = 0;
            } else {
                num = -num;
            }
        }

        return num;
    }
}
