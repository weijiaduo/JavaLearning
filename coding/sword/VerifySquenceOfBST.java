package com.wjd.sword;

/**
 * 输入一个整数数组，判断该数组是不是某二叉搜索树的后序遍历的结果。
 * 如果是则输出Yes,否则输出No。假设输入的数组的任意两个数字都互不相同。
 *
 */
public class VerifySquenceOfBST {

    public static void main(String[] args) {
        int[] sequence = {1,2,3};
        System.out.println(verifySquenceOfBST(sequence));
    }

    public static boolean verifySquenceOfBST(int[] sequence) {
        if (sequence == null || sequence.length <= 0) {
            return false;
        }

        return verifySquenceOfBST(sequence, 0, sequence.length - 1);
    }

    private static boolean verifySquenceOfBST(int[] sequence, int start, int end) {
        if (start >= end) {
            return true;
        }

        int root = sequence[end];
        int left = start;
        while (left < end && sequence[left] < root) {
            left++;
        }
        int right = left;
        while (right < end) {
            if (sequence[right++] <= root){
                return false;
            }
        }

        return verifySquenceOfBST(sequence, start, left - 1) && verifySquenceOfBST(sequence, left, end - 1);
    }
}
