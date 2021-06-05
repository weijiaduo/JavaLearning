package com.wjd.sword;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 一个整型数组里除了两个数字之外，其他的数字都出现了偶数次。
 * 请写程序找出这两个只出现一次的数字。
 *
 */
public class FindNumsAppearOnce {

    public static void main(String[] args) {
        int[] array = {1,2,3,4,4,1};
        int[] num1 = new int[1];
        int[] num2 = new int[1];
        findNumsAppearOnce1(array, num1, num2);
        System.out.println(num1[0] + " " + num2[0]);
    }

    public static void findNumsAppearOnce1(int[] array, int[] num1, int[] num2) {
        if (array == null || array.length < 2) {
            return;
        }
        Map<Integer, Integer> nums = new HashMap<>();
        for (int t: array) {
            if (nums.containsKey(t)) {
                nums.remove(t);
            } else {
                nums.put(t, 1);
            }
        }
        if (nums.size() == 2) {
            Integer[] keys = nums.keySet().toArray(new Integer[0]);
            num1[0] = keys[0];
            num2[0] = keys[1];
        }
    }

    public static void findNumsAppearOnce(int[] array, int[] num1, int[] num2) {
        if (array == null || array.length < 2) {
            return;
        }

        int groupFlag = getGroupFlag(array);
        num1[0] = 0;
        num2[0] = 0;
        for (int t: array) {
            if ((groupFlag & t) == 0) {
                num1[0] ^= t;
            } else {
                num2[0] ^= t;
            }
        }
    }

    private static int getGroupFlag(int[] array) {
        int flag = 0;
        for (int t: array) {
            flag ^= t;
        }

        flag = flag & (flag - 1) ^ flag;

        return flag;
    }
}
