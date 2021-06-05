package com.wjd.sword;

/**
 * 一只青蛙一次可以跳上1级台阶，也可以跳上2级……它也可以跳上n级。
 * 求该青蛙跳上一个n级的台阶总共有多少种跳法。
 *
 */
public class JumpFloorII {

    public static void main(String[] args) {
        int n = 3;
        System.out.println(jumpFloorII(n));
    }

    /**
     * 因为
     * f(n) = f(n-1) + f(n-2) + ... + f(1)
     * f(n-1) = f(n-2) + f(n-3) + ... + f(1)
     * 所以
     * f(n) = f(n-1) + f(n-1)
     *
     * @param target
     * @return
     */
    public static int jumpFloorII (int target) {
        if (target <= 0) {
            return 1;
        }

        int a1 = 1;
        a1 <<= (target-1);

        return a1;
    }
}
