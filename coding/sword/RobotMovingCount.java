package com.wjd.sword;

import java.util.Arrays;

/**
 * 地上有一个m行和n列的方格。一个机器人从坐标0,0的格子开始移动，
 * 每一次只能向左，右，上，下四个方向移动一格，但是不能进入行坐标和列坐标的数位之和大于k的格子。
 * 例如，当k为18时，机器人能够进入方格（35,37），因为3+5+3+7 = 18。
 * 但是，它不能进入方格（35,38），因为3+5+3+8 = 19。请问该机器人能够达到多少个格子？
 */
public class RobotMovingCount {

    public static void main(String[] args) {
        System.out.println(movingCount(18, 40, 40));
    }

    public static int movingCount(int threshold, int rows, int cols) {
        int[] flag = new int[rows * cols];
        Arrays.fill(flag, 0);
        movingCount(threshold, rows, cols, 0, 0, flag);
        int count = 0;
        for (int i = 0; i < flag.length; i++) {
            if (flag[i] == 1) {
                count++;
            }
        }
        return count;
    }

    private static void movingCount(int threshold, int rows, int cols, int x, int y, int[] flag) {
        if (x < 0 || x >= rows || y < 0 || y >= cols) {
            return;
        }

        int cur = x * cols + y;
        if (flag[cur] != 0) {
            return;
        }

        if (canEnter(threshold, x, y)) {
            flag[cur] = 1;
        } else {
            flag[cur] = -1;
            return;
        }

        // 上
        movingCount(threshold, rows, cols, x - 1, y, flag);
        // 右
        movingCount(threshold, rows, cols, x, y + 1, flag);
        // 下
        movingCount(threshold, rows, cols, x + 1, y, flag);
        // 左
        movingCount(threshold, rows, cols, x, y - 1, flag);

    }

    private static boolean canEnter(int threshold, int x, int y) {
        int sum = 0;
        while (x != 0) {
            sum += x % 10;
            x /= 10;
        }
        while (y != 0) {
            sum += y % 10;
            y /= 10;
        }

        return sum <= threshold;
    }
}
