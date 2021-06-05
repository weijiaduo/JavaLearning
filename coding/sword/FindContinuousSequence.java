package com.wjd.sword;

import java.util.ArrayList;

/**
 * 输出所有和为S的连续正数序列。序列内按照从小至大的顺序，序列间按照开始数字从小到大的顺序
 *
 */
public class FindContinuousSequence {

    public static void main(String[] args) {
        int sum = 10;
        ArrayList<ArrayList<Integer>> res = findContinuousSequence(sum);
        System.out.println(res);
    }

    public static ArrayList<ArrayList<Integer>> findContinuousSequence(int sum) {
        ArrayList<ArrayList<Integer>> res = new ArrayList<>();
        if (sum > 0) {
            int i = 1, j = 2, num = i;
            while (i < j && j <= sum) {
                if (num == sum) {
                    ArrayList<Integer> t = new ArrayList<>();
                    for (int k = i; k < j; k++) {
                        t.add(k);
                    }
                    res.add(t);

                    num -= i++;
                    num += j++;
                }
                if (num > sum) {
                    num -= i++;
                }
                if (num < sum) {
                    num += j++;
                }
            }
        }
        return res;
    }
}
