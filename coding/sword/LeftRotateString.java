package com.wjd.sword;

/**
 * 对于一个给定的字符序列S，请你把其循环左移K位后的序列输出。
 * 例如，字符序列S=”abcXYZdef”,要求输出循环左移3位后的结果，即“XYZdefabc”。
 *
 */
public class LeftRotateString {

    public static void main(String[] args) {
        String str = "asdfghjkl";
        System.out.println(leftRotateString2(str, 12));
    }

    public static String leftRotateString(String str, int n) {
        if (str == null || str.length() == 0 || n <= 0) {
            return str;
        }

        int tn = n % str.length();
        char[] sChars = str.toCharArray();
        reverse(sChars, 0, tn - 1);
        reverse(sChars, tn, sChars.length - 1);
        reverse(sChars, 0, sChars.length - 1);

        return new String(sChars);
    }

    public static String leftRotateString2(String str, int n) {
        if (str == null || str.length() == 0 || n <= 0) {
            return str;
        }

        int tn = n % str.length();
        str += str;
        return str.substring(tn, tn + (str.length() >> 1));
    }

    private static void reverse(char[] str, int start, int end) {
        if (start < end) {
            int lp = start, rp = end;
            while (lp < rp) {
                char ch = str[lp];
                str[lp] = str[rp];
                str[rp] = ch;
                lp++;
                rp--;
            }
        }
    }
}
