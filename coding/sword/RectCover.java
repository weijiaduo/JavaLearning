package com.wjd.sword;

/**
 * 我们可以用2*1的小矩形横着或者竖着去覆盖更大的矩形。
 * 请问用n个2*1的小矩形无重叠地覆盖一个2*n的大矩形，总共有多少种方法？
 *
 */
public class RectCover {

    public static void main(String[] args) {
        int n = 3;
        System.out.println(rectCover(n));
    }

    public static int rectCover (int target) {
        if (target < 1) {
            return 1;
        }

        int a1 = 1, a2 = 2;
        int k = 1;
        while (k < target) {
            a2 += a1;
            a1 = a2 - a1;
            k++;
        }

        return a1;
    }
}
