package com.wjd.sword;

/**
 * 数组中有一个数字出现的次数超过数组长度的一半，请找出这个数字。
 * 例如输入一个长度为9的数组{1,2,3,2,2,2,5,4,2}。
 * 由于数字2在数组中出现了5次，超过数组长度的一半，因此输出2。
 * 如果不存在则输出0。
 *
 */
public class MoreThanHalfNum {

    public static void main(String[] args) {
        int[] array = {2,2,2,2,2,1,3,4,5};
        System.out.println(moreThanHalfNum(array));
        System.out.println(quickMoreThanHalfNum(array));
    }

    public static int moreThanHalfNum(int[] array) {
        if (array == null || array.length == 0) {
            return 0;
        }

        int k = array[0], num = 1;
        for (int i = 1; i < array.length; i++) {
            if (num == 0) {
                k = array[i];
                num = 1;
            } else {
              if (k == array[i]){
                  num++;
              } else {
                  num--;
              }
            }
        }

        if (checkMoreThanHalfNum(array, k)){
            return k;
        }

        return 0;
    }

    public static int quickMoreThanHalfNum(int[] array) {
        if (array == null || array.length == 0) {
            return 0;
        }

        int k = 0;
        int left = 0, right = array.length - 1, mid = array.length >> 1;
        while (left <= right) {
            int index = quickPartSort(array, left, right);
            if (index == mid) {
                k = array[index];
                break;
            } else if (index > mid) {
                right = index - 1;
            } else {
                left = index + 1;
            }
        }

        if (checkMoreThanHalfNum(array, k)) {
            return k;
        }

        return 0;
    }

    private static int quickPartSort(int[] array, int start, int end) {
        int lp = start, rp = end;
        int x = array[start];
        while (lp < rp) {
            while (lp < rp && array[rp] >= x) {
                rp--;
            }
            while (lp < rp && array[lp] <= x) {
                lp++;
            }
            if (lp != rp) {
                array[lp] = array[lp] ^ array[rp];
                array[rp] = array[lp] ^ array[rp];
                array[lp] = array[lp] ^ array[rp];
            }
        }

        if (start != lp) {
            array[lp] = array[lp] ^ array[start];
            array[start] = array[lp] ^ array[start];
            array[lp] = array[lp] ^ array[start];
        }

        return lp;
    }

    /**
     * 检查该数字是否超过一半
     * 
     * @param array
     * @param k
     * @return
     */
    private static boolean checkMoreThanHalfNum(int[] array, int k) {
        int count = 0, len = array.length;
        for (int i = 0; i < len; i++) {
            if (array[i] == k) {
                count++;
            }
        }
        if (count > (len >> 1)) {
            return true;
        }
        return false;
    }
}
