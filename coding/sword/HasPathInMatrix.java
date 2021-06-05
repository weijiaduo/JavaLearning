package com.wjd.sword;

import java.util.ArrayList;
import java.util.List;

/**
 * 请设计一个函数，用来判断在一个矩阵中是否存在一条包含某字符串所有字符的路径。
 * 路径可以从矩阵中的任意一个格子开始，每一步可以在矩阵中向左，向右，向上，向下移动一个格子。
 * 如果一条路径经过了矩阵中的某一个格子，则之后不能再次进入这个格子。
 * 例如 a b c e s f c s a d e e 这样的3 X 4 矩阵中包含一条字符串"bcced"的路径，但是矩阵中不包含"abcb"路径，
 * 因为字符串的第一个字符b占据了矩阵中的第一行第二个格子之后，路径不能再次进入该格子。
 */
public class HasPathInMatrix {

    public static void main(String[] args) {
        char[] matrix = "ABCESFCSADEE".toCharArray();
        char[] str = "ABCB".toCharArray();
        System.out.println(hasPath(matrix, 3, 4, str));
    }

    public static boolean hasPath(char[] matrix, int rows, int cols, char[] str) {
        if (matrix == null || str == null || matrix.length == 0 || str.length == 0
                || rows <= 0 || cols <= 0) {
            return false;
        }

        List<Integer> path = new ArrayList<>();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (hasPath(matrix, rows, cols, i, j, str, 0, path)) {
                    return true;
                } else {
                    path.clear();
                }
            }
        }

        return false;
    }

    private static boolean hasPath(char[] matrix, int rows, int cols, int x, int y,
                            char[] str, int index, List<Integer> path) {
        if (x < 0 || x >= rows || y < 0 || y >= cols || index >= str.length) {
            return false;
        }

        int i = x * cols + y;
        if (matrix[i] != str[index] || path.contains(i)) {
            return false;
        } else {
            path.add(i);
        }

        boolean flag = false;

        // 匹配结束
        if (index == str.length - 1) {
            flag = true;
        }

        // 上
        if (!flag && !path.contains(i - cols)) {
            flag = hasPath(matrix, rows, cols, x - 1, y, str, index + 1, path);
        }
        // 右
        if (!flag && !path.contains(i + 1)) {
            flag = hasPath(matrix, rows, cols, x, y + 1, str, index + 1, path);
        }
        // 下
        if (!flag && !path.contains(i + cols)) {
            flag = hasPath(matrix, rows, cols, x + 1, y, str, index + 1, path);
        }
        // 左
        if (!flag && !path.contains(i - 1)) {
            flag = hasPath(matrix, rows, cols, x, y - 1, str, index + 1, path);
        }

        return flag;
    }
}
