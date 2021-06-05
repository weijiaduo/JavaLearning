package com.wjd.sword;

public class CuttingRope {

    public static void main(String[] args) {
        int n = 100, m = 53;
        long start = System.currentTimeMillis();
        System.out.println(cutRopeDynamic(n,m));
        long end = System.currentTimeMillis();
        System.out.println("动态规划时间: " + (end - start) + " ms");

        start = System.currentTimeMillis();
        System.out.println(cutRopeDynamic2(n,m));
        end = System.currentTimeMillis();
        System.out.println("优化动态规划时间: " + (end - start) + " ms");
    }

    /**
     * 动态规划
     *
     * @param n
     * @param m
     * @return
     */
    public static long cutRopeDynamic(int n, int m){
        if (n <= 0 || m <= 0 || m > n) {
            return 0;
        }

        long[][] a = new long[n + 1][m + 1];
        for (int i = 0; i <= n; i++) {
            a[i][1] = i;
        }

        for (int k = 2; k <= m; k++) {
            for (int i = k; i <= n; i++) {
                long maxRes = 0;
                for (int j = 1; j <= i - k + 1; j++) {
                    long t = j * a[i - j][k - 1];
                    if (t > maxRes) {
                        maxRes = t;
                    }
                }
                a[i][k] = maxRes;
            }
        }

        return a[n][m];
    }

    /**
     * 优化动态规划
     *
     * @param n
     * @param m
     * @return
     */
    public static long cutRopeDynamic2(int n, int m){
        if (n <= 0 || m <= 0 || m > n) {
            return 0;
        }

        long[] a = new long[n + 1];
        for (int i = 0; i <= n; i++) {
            a[i] = i;
        }

        for (int k = 2; k <= m; k++) {
            for (int i = n; i >= k; i--) {// 先更新后面的值
                long maxRes = 0;
                for (int j = 1; j <= i - k + 1; j++) {
                    long t = j * a[i - j];
                    if (t > maxRes) {
                        maxRes = t;
                    }
                }
                a[i] = maxRes;
            }
        }

        return a[n];
    }
}
