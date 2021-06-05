package com.wjd.sword;

/**
 * 请实现一个函数用来匹配包括'.'和'*'的正则表达式。
 * 模式中的字符'.'表示任意一个字符，而'*'表示它前面的字符可以出现任意次（包含0次）。
 * 在本题中，匹配是指字符串的所有字符匹配整个模式。
 * 例如，字符串"aaa"与模式"a.a"和"ab*ac*a"匹配，但是与"aa.a"和"ab*a"均不匹配
 */
public class PatternMatch {

    public static void main(String[] args) {
        String str = "";
        String pattern = ".*";
        System.out.println(match(str.toCharArray(), pattern.toCharArray()));
    }

    public static boolean match(char[] str, char[] pattern) {
        if (str == null || pattern == null) {
            return false;
        }
        return match(str, 0, pattern, 0);
    }

    private static boolean match(char[] str, int i, char[] pattern, int k) {
        if (str.length == i && pattern.length == k) {
            return true;
        }
        if (str.length != i && pattern.length == k) {
            return false;
        }

        if (k < pattern.length - 1 && pattern[k+1] == '*') {
            if (i < str.length && (str[i] == pattern[k] || pattern[k] == '.')) {
                return match(str, i+1, pattern, k) || match(str, i, pattern, k+2);
            } else {
                return match(str, i, pattern, k+2);
            }
        } else if (i < str.length && (str[i] == pattern[k] || pattern[k] == '.')) {
            return match(str, i+1, pattern, k+1);
        }

        return false;
    }
}
