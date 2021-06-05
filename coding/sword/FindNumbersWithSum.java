package com.wjd.sword;

import java.util.ArrayList;

/**
 * 输入一个递增排序的数组和一个数字S，在数组中查找两个数，
 * 使得他们的和正好是S，如果有多对数字的和等于S，输出两个数的乘积最小的。
 * 对应每个测试案例，输出两个数，小的先输出。
 *
 */
public class FindNumbersWithSum {

    public static void main(String[] args) {
        int sum = 15;
        int[] array = {1,2,4,7,11,15};
        System.out.println(findNumbersWithSum(array, sum));
    }

    public static ArrayList<Integer> findNumbersWithSum(int[] array, int sum) {
        ArrayList<Integer> res = new ArrayList<>();
        if (array != null && array.length > 0) {
            int lp = 0, rp = array.length - 1;
            while (lp < rp) {
                int temp = array[lp] + array[rp];
                if (temp == sum) {
                    break;
                }
                if (temp > sum) {
                    rp--;
                }
                if (temp < sum) {
                    lp++;
                }
            }
            if (lp < rp && (array[lp] + array[rp] == sum)) {
                res.add(array[lp]);
                res.add(array[rp]);
            }
        }
        return res;
    }
}
