package com.wjd.sword;

/**
 * 要求输入一个整数n，请你输出斐波那契数列的第n项（从0开始，第0项为0）。
 *
 */
public class Fibonacci {

    public static void main(String[] args) {
        int n = 4;
        System.out.println(fibonacci(n));
    }

    public static int fibonacci (int n) {
        if (n < 0) {
            return -1;
        }

        int a0 = 0, a1 = 1;
        int k = 1;
        while (k <= n) {
            a1 = a0 + a1;
            a0 = a1 - a0;
            k++;
        }

        return a0;
    }
}
