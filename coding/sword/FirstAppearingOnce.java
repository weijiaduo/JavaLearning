package com.wjd.sword;

/**
 * 请实现一个函数用来找出字符流中第一个只出现一次的字符。
 * 例如，当从字符流中只读出前两个字符"go"时，第一个只出现一次的字符是"g"。
 * 当从该字符流中读出前六个字符“google"时，第一个只出现一次的字符是"l"。
 * 如果当前字符流没有存在出现一次的字符，返回#字符。
 */
public class FirstAppearingOnce {

    private static StringBuffer str = new StringBuffer();
    private static int[] charNum = new int[256];

    public static void main(String[] args) {
        String s = "google";
        for (int i = 0; i < s.length(); i++) {
            Insert(s.charAt(i));
        }
        System.out.println(FirstAppearingOnce());
    }

    public static void Insert(char ch) {
        charNum[ch]++;
        if (charNum[ch] == 1) {
            str.append(ch);
        }
    }

    public static char FirstAppearingOnce() {
        for (int i = 0; i < str.length(); i++) {
            if (charNum[str.charAt(i)] == 1) {
                return str.charAt(i);
            }
        }
        return '#';
    }
}
