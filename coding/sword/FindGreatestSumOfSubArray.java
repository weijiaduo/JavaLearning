package com.wjd.sword;

import java.util.Arrays;


/**
 * 给一个数组，返回它的最大连续子序列的和
 * 例如:{6,-3,-2,7,-15,1,2,2},连续子向量的最大和为8(从第0个开始,到第3个为止)。
 *
 */
public class FindGreatestSumOfSubArray {

    public static void main(String[] args) {
        int[] a = {6,-3,-2,7,-15,1,2,2};
        System.out.println(findGreatestSumOfSubArray(a));
    }

    public static int findGreatestSumOfSubArray(int[] array) {
        if (array == null || array.length == 0) {
            return 0;
        }

        int[] sum = new int[array.length];
        Arrays.fill(sum, 0);

        int greatestSum = Integer.MIN_VALUE;
        for (int i = 0; i < array.length; i++) {
            if (i == 0 || sum[i - 1] <= 0) {
                sum[i] = array[i];
            } else {
                sum[i] = array[i] + sum[i - 1];
            }
            if (sum[i] > greatestSum) {
                greatestSum = sum[i];
            }
        }

        return greatestSum;
    }
}
