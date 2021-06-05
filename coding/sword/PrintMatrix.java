package com.wjd.sword;

import java.util.ArrayList;

/**
 * 输入一个矩阵，按照从外向里以顺时针的顺序依次打印出每一个数字，
 * 例如，如果输入如下4 X 4矩阵： 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16
 * 则依次打印出数字1,2,3,4,8,12,16,15,14,13,9,5,6,7,11,10.
 */
public class PrintMatrix {

    public static void main(String[] args) {
        int[][] matrix = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        ArrayList<Integer> result = printMatrix(matrix);
        System.out.println(result);
    }

    public static ArrayList<Integer> printMatrix(int[][] matrix) {
        ArrayList<Integer> result = new ArrayList<>();

        if (matrix != null && matrix.length > 0 && matrix[0].length > 0) {
            int rows, cols;
            rows = matrix.length;
            cols = matrix[0].length;

            // 上下左右边界
            int l = 0, r = cols - 1, u = 0, d = rows - 1;

            while (l <= r && u <= d) {

                // 从左到右
                for (int i = l; u <= d && i <= r; i++) {
                    result.add(matrix[u][i]);
                }
                u++;

                // 从上到下
                for (int i = u; l <= r && i <= d; i++) {
                    result.add(matrix[i][r]);
                }
                r--;

                // 从右到左
                for (int i = r; u <= d && i >= l; i--) {
                    result.add(matrix[d][i]);
                }
                d--;

                // 从下到上
                for (int i = d; l <= r && i >= u; i--) {
                    result.add(matrix[i][l]);
                }
                l++;
            }
        }

        return result;
    }
}
