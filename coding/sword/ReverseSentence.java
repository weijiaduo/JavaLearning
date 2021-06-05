package com.wjd.sword;

public class ReverseSentence {

    public static void main(String[] args) {
        String str = " ";
        System.out.println(reverseSentence(str));
    }

    public static String reverseSentence(String str) {
        if (str == null || str.length() == 0) {
            return str;
        }

        char[] sChars = str.toCharArray();
        reverse(sChars, 0, sChars.length - 1);
        int lp = 0, rp = lp;
        while (rp < sChars.length) {
            while (lp < sChars.length && sChars[lp] == ' ') {
                lp++;
            }
            rp = lp;
            while (rp < sChars.length && sChars[rp] != ' ') {
                rp++;
            }
            reverse(sChars, lp, rp - 1);
            lp = rp;
        }

        return new String(sChars);
    }

    private static void reverse(char[] str, int start, int end) {
        if (start < end) {
            int lp = start, rp = end;
            while (lp < rp) {
                char ch = str[lp];
                str[lp++] = str[rp];
                str[rp--] = ch;
            }
        }
    }
}
