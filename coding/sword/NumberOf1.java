package com.wjd.sword;

/**
 * 二进制中1的数量
 */
public class NumberOf1 {

    public static void main(String[] args) {
        int a = 190;
        System.out.println(getNumberOf1(a));
        System.out.println(getNumberOf1Quick(a));
    }

    public static int getNumberOf1(int n) {
        int count = 0;
        while (n != 0) {
            if ((n & 1) == 1) {
                count++;
            }
            n >>>= 1;
        }

        return count;
    }

    public static int getNumberOf1Quick(int n) {
        int count = 0;
        while (n != 0) {
            count++;
            n = (n - 1) & n;
        }

        return count;
    }
}
