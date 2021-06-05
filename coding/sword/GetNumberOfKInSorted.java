package com.wjd.sword;

/**
 * 统计一个数字在排序数组中出现的次数。
 *
 */
public class GetNumberOfKInSorted {

    public static void main(String[] args) {
        int[] array = {1,2,3,3,3,4};
        System.out.println(getNumberOfK(array, 3));
    }

    public static int getNumberOfK(int[] array, int k) {
        if (array == null || array.length == 0) {
            return 0;
        }
        int left = getMinIndex(array, 0, array.length - 1, k);
        int right = getMaxIndex(array, 0, array.length - 1, k);
        if (left != -1 && right != -1) {
            return right - left + 1;
        }
        return 0;
    }

    /**
     * 找出k的最小下标
     *
     * @param array
     * @param start
     * @param end
     * @param k
     * @return k不存在返回 -1
     */
    private static int getMinIndex(int[] array, int start, int end, int k) {
        if (start > end) {
            return -1;
        }
        int mid = (start + end) >> 1;
        if (array[mid] == k) {
            if (mid == start || array[mid - 1] < k) {
                return mid;
            } else {
                return getMinIndex(array, start, mid - 1, k);
            }
        }
        if (array[mid] > k) {
            return getMinIndex(array, start, mid - 1, k);
        } else {
            return getMinIndex(array, mid + 1, end, k);
        }
    }

    /**
     * 找出k的最大下标
     *
     * @param array
     * @param start
     * @param end
     * @param k
     * @return k不存在返回 -1
     */
    private static int getMaxIndex(int[] array, int start, int end, int k) {
        if (start > end) {
            return -1;
        }
        int mid = (start + end) >> 1;
        if (array[mid] == k) {
            if (mid == end || array[mid + 1] > k) {
                return mid;
            } else {
                return getMaxIndex(array, mid + 1, end, k);
            }
        }
        if (array[mid] < k) {
            return getMaxIndex(array, mid + 1, end, k);
        } else {
            return getMaxIndex(array, start, mid - 1, k);
        }
    }
}
