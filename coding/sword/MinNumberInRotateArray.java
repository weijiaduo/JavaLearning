package com.wjd.sword;

public class MinNumberInRotateArray {

    public static void main(String[] args) {
        int[] a = {2,2,2,2,2};
        System.out.println(minNumberInRotateArray(a));
    }

    public static int minNumberInRotateArray(int [] array) {
        if (array == null || array.length == 0) {
            return 0;
        }

        int left = 0, right = array.length - 1;
        while (left + 1 < right) {
            int mid = left + ((right - left) >> 1);
            // 无法确定中点属于左边还是右边
            if (array[mid] == array[left] && array[mid] == array[right]) {
                return minor(array, left, right);
            }
            if (array[mid] >= array[left]) {
                left = mid;
            }
            if (array[mid] <= array[right]) {
                right = mid;
            }
        }

        return array[right];
    }

    private static int minor(int[] array, int left, int right) {
        for (int i = left; i < right; i++) {
            if (array[i] > array[i + 1]) {
                return array[i + 1];
            }
        }
        return array[left];
    }
}
