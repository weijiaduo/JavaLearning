package com.wjd.sword;

import java.util.Arrays;

/**
 * 在一个字符串(0<=字符串长度<=10000，
 * 全部由字母组成)中找到第一个只出现一次的字符,并返回它的位置,
 * 如果没有则返回 -1（需要区分大小写）.
 *
 */
public class FirstNotRepeatingChar {

    public static void main(String[] args) {
        String str = "fereffr";
        System.out.println(firstNotRepeatingChar(str));
    }

    public static int firstNotRepeatingChar(String str) {
        if (str == null || "".equals(str)) {
            return -1;
        }

        int[] nums = new int[127];
        Arrays.fill(nums, 0);

        for (int i = 0; i < str.length(); i++) {
            nums[str.charAt(i)]++;
        }

        for (int i = 0; i < str.length(); i++) {
            if (nums[str.charAt(i)] == 1) {
                return i;
            }
        }

        return -1;
    }
}
