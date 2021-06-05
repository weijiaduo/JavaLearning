package com.wjd.sword;

import java.util.Arrays;

/**
 * 输入一个整数数组，实现一个函数来调整该数组中数字的顺序，
 * 使得所有的奇数位于数组的前半部分，所有的偶数位于数组的后半部分，
 * 并保证奇数和奇数，偶数和偶数之间的相对位置不变。
 *
 */
public class ReOrderArray {

    public static void main(String[] args) {
        int[] a = {1,2,3,4,5,6,7};
        reOrderArray(a);
        System.out.println(Arrays.toString(a));
    }

    public static void reOrderArray (int[] array) {
        if (array == null || array.length == 0) {
            return;
        }

        int[] tArr = Arrays.copyOf(array, array.length);

        int k = 0;
        for (int i = 0; i < tArr.length; i++) {
            if ((tArr[i] & 1) == 1) {
                array[k++] = tArr[i];
            }
        }

        k = tArr.length-1;
        for (int i = tArr.length-1; i >= 0; i--) {
            if ((tArr[i] & 1) == 0) {
                array[k--] = tArr[i];
            }
        }
    }
}
