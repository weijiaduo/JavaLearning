package com.wjd.sword;

/**
 * 求1+2+3+...+n，要求不能使用乘除法、for、while、if、else、switch、case等关键字及条件判断语句（A?B:C）
 *
 */
public class ConditionSum {

    public static void main(String[] args) {
        int n = 10;
        System.out.println(sum(n));
        System.out.println(sum2(n));
    }

    public static int sum(int n) {
        return sum(new int[n + 1], 1, 0);
    }

    public static int sum2(int n) {
        int ans = n;
        boolean a = n > 0 && (ans = ans + sum(n - 1)) > 0;
        return ans;
    }

    private static int sum(int[] t, int index, int sum) {
        try {
            int a = t[index];
            sum += index;
            return sum(t, index + 1, sum);
        } catch (Exception e) {
            return sum;
        }
    }
}
