package com.wjd.sword;

import java.util.Arrays;

/**
 * 在数组中的两个数字，如果前面一个数字大于后面的数字，则这两个数字组成一个逆序对。
 * 输入一个数组,求出这个数组中的逆序对的总数P。
 * 并将P对1000000007取模的结果输出。 即输出P%1000000007
 */
public class InversePairs {

    public static void main(String[] args) {
        int[] a = {1,2,3,4,5,6,7,0};
        System.out.println(inversePairs(a));
    }

    public static int inversePairs(int [] array) {
        if (array == null || array.length == 0) {
            return 0;
        }

        int[] copy = Arrays.copyOf(array, array.length);
        int sum = inversePairs(array, copy, 0, array.length - 1);

        return sum;
    }

    private static int inversePairs(int[] array, int[] copy, int start, int end) {
        if (start >= end) {
            return 0;
        }

        long sum = 0;
        int mid = start + ((end - start) >> 1);
        sum += inversePairs(copy, array, start, mid);
        sum += inversePairs(copy, array, mid + 1, end);

        int i = mid, j = end, k = end;
        while (i >= start && j > mid) {
            if (array[i] > array[j]) {
                sum += j - mid;
                copy[k--] = array[i--];
            } else {
                copy[k--] = array[j--];
            }
        }
        while (i >= start) {
            copy[k--] = array[i--];
        }
        while (j > mid) {
            copy[k--] = array[j--];
        }

        return (int)(sum % 1000000007);
    }
}
