package com.wjd.sword;

/**
 * 只有因子是2，3，5的数才是丑数，求第n个丑数
 *
 */
public class UglyNumber {
    public static void main(String[] args) {
        int index = 6;
        System.out.println(getUglyNumber_Solution(index));
    }

    public static int getUglyNumber_Solution(int index) {
        if (index <= 0) {
            return 0;
        }

        int[] a = new int[index + 1];
        a[0] = 1;

        int i = 0, j = 0, k = 0, p = 1;
        while (p < index) {
            int p1 = a[i] * 2;
            int p2 = a[j] * 3;
            int p3 = a[k] * 5;
            a[p] = p1 < p2 ? p1 < p3 ? p1 : p3 : p2 < p3 ? p2 : p3;
            if (a[p] == p1) {
                i++;
            }
            if (a[p] == p2) {
                j++;
            }
            if (a[p] == p3) {
                k++;
            }
            p++;
        }

        return a[index - 1];
    }
}
