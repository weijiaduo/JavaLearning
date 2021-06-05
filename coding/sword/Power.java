package com.wjd.sword;

/**
 * 给定一个double类型的浮点数base和int类型的整数exponent。
 * 求base的exponent次方。
 *
 */
public class Power {

    public static void main(String[] args) {
        double base = 2;
        int exponent = 3;
        System.out.println(power(base, exponent));
    }

    public static double power(double base, int exponent) {
        if (base == 0) {
            return 0;
        }

        if (exponent == 0) {
            return 1;
        }

        if (exponent < 0) {
            base = 1 / base;
            exponent = -exponent;
        }

        double res = 1;
        while (exponent != 0) {
            if ((exponent & 1) == 1) {
                res *= base;
            }
            exponent >>= 1;
            base = base * base;
        }

        return res;
    }
}
