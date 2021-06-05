package com.wjd.sword;

import java.util.ArrayList;
import java.util.Collections;

/**
 * 输入一个字符串,按字典序打印出该字符串中字符的所有排列。
 * 例如输入字符串abc,则打印出由字符a,b,c所能排列出来的所有字符串abc,acb,bac,bca,cab和cba。
 *
 */
public class Permutation {

    public static void main(String[] args) {
        String str = "abc";
        ArrayList<String> res = permutation(str);
        System.out.println(res);
    }

    public static ArrayList<String> permutation(String str) {
        ArrayList<String> res = new ArrayList<>();
        if (str != null && !str.isEmpty()) {
            char[] s = str.toCharArray();
            permutation(s, 0, res);
            Collections.sort(res);
        }
        return res;
    }

    private static void permutation(char[] str, int index, ArrayList<String> res) {
        if (index == str.length - 1) {
            res.add(new String(str));
            return;
        }

        // 以 str[index] 开头的字符串
        permutation(str, index + 1, res);
        // 以索引 index 以后的字符开头的字符串
        for (int i = index + 1; i < str.length; i++) {
            // 防止两个字符相同，交换没意义
            if (str[index] != str[i]) {
                char temp = str[index];
                str[index] = str[i];
                str[i] = temp;
                permutation(str, index + 1, res);
                str[i] = str[index];
                str[index] = temp;
            }
        }
    }
}
