package com.wjd.sword;

import java.util.ArrayList;

/**
 * 输入n个整数，找出其中最小的K个数。
 * 例如输入4,5,1,6,2,7,3,8这8个数字，则最小的4个数字是1,2,3,4,。
 *
 */
public class LeastNumbers {

    public static void main(String[] args) {
        int[] input = {1,2,3,4,5,6,7,8,9,0};
        ArrayList<Integer> res = getLeastNumbers(input, 4);
        System.out.println(res);
    }

    public static ArrayList<Integer> getLeastNumbers(int[] input, int k) {
        ArrayList<Integer> res = new ArrayList<>();
        if (input != null && k <= input.length){
            for (int i = 0; i < input.length; i++) {
                if (res.size() < k) {
                    res.add(input[i]);
                } else {
                    int cur = input[i];
                    for (int j = 0; j < res.size(); j++) {
                        if (cur < res.get(j)) {
                            int temp = res.get(j);
                            res.set(j, cur);
                            cur = temp;
                        }
                    }
                }
            }
        }
        return res;
    }
}
