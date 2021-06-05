package com.wjd.sword;

import com.wjd.sword.structure.TreeNode;

/**
 * 输入一棵二叉树，求该树的深度。
 * 从根结点到叶结点依次经过的结点（含根、叶结点）形成树的一条路径，最长路径的长度为树的深度。
 *
 */
public class TreeDepth {

    public static void main(String[] args) {
        String[] s = {"8","8","7","9","2","#","#","#","#","4","7"};
        TreeNode tree = TreeNode.buildTree(s);

        System.out.println(treeDepth(tree));
    }

    public static int treeDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }

        return depTree(root, 0);
    }

    private static int depTree(TreeNode root, int depth) {
        if (root == null) {
            return depth;
        }
        int leftDepth = depTree(root.left, depth + 1);
        int rightDepth = depTree(root.right, depth + 1);

        return leftDepth > rightDepth ? leftDepth : rightDepth;
    }
}
