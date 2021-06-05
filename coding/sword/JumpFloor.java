package com.wjd.sword;

/**
 * 一只青蛙一次可以跳上1级台阶，也可以跳上2级。
 * 求该青蛙跳上一个n级的台阶总共有多少种跳法（先后次序不同算不同的结果）。
 *
 */
public class JumpFloor {

    public static void main(String[] args) {
        int n = 3;
        System.out.println(jumpFloor(n));
    }

    public static int jumpFloor (int target) {
        if (target <= 0) {
            return 1;
        }

        int a1 = 1, a2 = 2;
        int k = 2;
        while (k <= target) {
            a2 = a1 + a2;
            a1 = a2 - a1;
            k++;
        }

        return a1;
    }
}
