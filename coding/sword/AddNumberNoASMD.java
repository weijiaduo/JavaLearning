package com.wjd.sword;


/**
 * 求两个整数之和，要求在函数体内不得使用+、-、*、/四则运算符号。
 */
public class AddNumberNoASMD {

    public static void main(String[] args) {
        int num1 = -3, num2 = -9;
        System.out.println(add1(num1, num2));
        System.out.println(add(num1, num2));
    }

    public static int add1(int num1, int num2) {
        int sum, c;
        do {
            sum = num1 ^ num2;
            c = (num1 & num2) << 1;
            num1 = sum;
            num2 = c;
        } while (num2 != 0);
        return sum;
    }

    public static int add(int num1, int num2) {
        int[] s1 = numToArray(num1);
        int[] s2 = numToArray(num2);
        int[] sum = new int[s1.length];

        int c = 0;
        for (int i = s1.length - 1; i >= 0 ; i--) {
            sum[i] = s1[i] ^ s2[i] ^ c;
            // 三个数中至少有两个1时向前进位
            if ((s1[i] & s2[i] & c) == 1 || (sum[i] == 0 && (s1[i] | s2[i] | c) == 1)) {
                c = 1;
            } else {
                c = 0;
            }
        }

        int res = 0;
        for (int i = 0; i < sum.length; i++) {
            res <<= 1;
            res ^= sum[i];
        }

        return res;
    }

    private static int[] numToArray(int num) {
        int[] arr = new int[32];
        for (int i = 31; i >= 0; i--) {
            if ((num & 1) == 1) {
                arr[i] = 1;
            } else {
                arr[i] = 0;
            }
            num >>>= 1;
        }
        return arr;
    }

}
