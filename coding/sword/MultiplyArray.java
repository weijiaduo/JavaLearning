package com.wjd.sword;

import java.util.Arrays;

/**
 * 给定一个数组A[0,1,...,n-1],请构建一个数组B[0,1,...,n-1],
 * 其中B中的元素B[i]=A[0]*A[1]*...*A[i-1]*A[i+1]*...*A[n-1]。
 * 不能使用除法。
 */
public class MultiplyArray {

    public static void main(String[] args) {
        int[] a =  {1,2,3};
        int[] b = multiply(a);
        System.out.println(Arrays.toString(b));
    }

    public static int[] multiply(int[] A) {
        if (A == null) {
            return null;
        }

        int[] b = new int[A.length];
        int[] c = new int[A.length];

        if (A.length > 0) {
            c[0] = 1;
            for (int i = 1; i < A.length; i++) {
                c[i] = c[i-1] * A[i-1];
            }

            int temp = 1;
            for (int i = A.length-1; i >= 0 ; i--) {
                b[i] = c[i] * temp;
                temp *= A[i];
            }
        }

        return b;
    }

}
