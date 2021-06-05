package com.wjd.sword;

public class FindNumberInSortedArray {

    public static void main(String[] args) {
        int[][] a = {{1, 2}, {3, 4}, {5, 6}};
        System.out.println(find(2, a));
    }

    public static boolean find(int target, int [][] array) {
        boolean flag = false;

        if (array != null) {
            int rows = array.length, cols = array[0].length;
            int i = 0, j = cols - 1;
            while (i < rows && j >= 0) {
                if (array[i][j] == target) {
                    flag = true;
                    break;
                } else if (array[i][j] > target) {
                    j--;
                } else {
                    i++;
                }
            }
        }

        return flag;
    }
}
