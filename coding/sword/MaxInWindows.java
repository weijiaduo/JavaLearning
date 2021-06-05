package com.wjd.sword;

import java.util.ArrayList;

/**
 * 给定一个数组和滑动窗口的大小，找出所有滑动窗口里数值的最大值。
 * 例如，如果输入数组{2,3,4,2,6,2,5,1}及滑动窗口的大小3，
 * 那么一共存在6个滑动窗口，他们的最大值分别为{4,4,6,6,6,5}；
 * 针对数组{2,3,4,2,6,2,5,1}的滑动窗口有以下6个： {[2,3,4],2,6,2,5,1}， {2,[3,4,2],6,2,5,1}，
 * {2,3,[4,2,6],2,5,1}， {2,3,4,[2,6,2],5,1}， {2,3,4,2,[6,2,5],1}， {2,3,4,2,6,[2,5,1]}。
 */
public class MaxInWindows {

    public static void main(String[] args) {
        int[] a = {2,3,4,2,6,8,5,7};
        System.out.println(maxInWindows(a, 3));
    }

    public static ArrayList<Integer> maxInWindows(int [] num, int size) {
        ArrayList<Integer> res = new ArrayList<>();

        if (num.length >= size && size > 0) {
            int maxIndex = 0;
            for (int i = 0, j = 0; i <= num.length && j <= num.length - size;) {
                if (i - j < size) {
                    if (num[i] >= num[maxIndex]){
                        maxIndex = i;
                    }
                    i++;
                } else {
                    res.add(num[maxIndex]);
                    if (j == maxIndex) {
                        maxIndex = j + 1;
                        for (int k = maxIndex; k < i; k++) {
                            if (num[k] >= num[maxIndex]) {
                                maxIndex = k;
                            }
                        }
                    }
                    j++;
                }
            }
        }

        return res;
    }

}
