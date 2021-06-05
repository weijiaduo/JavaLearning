package com.wjd.sword;

import com.wjd.sword.structure.TreeNode;

/**
 * 输入一棵二叉树，判断该二叉树是否是平衡二叉树。
 *
 */
public class IsBalancedTree {

    public static void main(String[] args) {
        String[] s = {"8","8","7","9","2"};
        TreeNode tree = TreeNode.buildTree(s);

        System.out.println(isBalanced(tree));
    }

    public static boolean isBalanced(TreeNode root) {
        if (root == null) {
            return true;
        }
        return depTree(root) != -1 ? true : false;
    }

    private static int depTree(TreeNode root) {
        if (root == null) {
            return 0;
        }

        int leftDepth = depTree(root.left);
        if (leftDepth == -1) {
            return -1;
        }
        int rightDepth = depTree(root.right);
        if (rightDepth == -1) {
            return -1;
        }
        if (Math.abs(leftDepth - rightDepth) > 1) {
            return -1;
        }
        return leftDepth > rightDepth ? leftDepth + 1 : rightDepth + 1;
    }
}
