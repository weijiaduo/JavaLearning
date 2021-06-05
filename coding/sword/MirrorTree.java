package com.wjd.sword;


import com.wjd.sword.structure.TreeNode;

/**
 * 操作给定的二叉树，将其变换为源二叉树的镜像。
 * 二叉树的镜像定义：源二叉树
 *       8
 *     /  \
 *    6   10
 *   / \  / \
 *  5  7 9 11
 * 镜像二叉树
 *      8
 *    /  \
 *   10   6
 *  / \  / \
 * 11 9 7  5
 *
 */
public class MirrorTree {

    public static void main(String[] args) {
        String[] s = {"7", "8", "9"};
        TreeNode tree = TreeNode.buildTree(s);

        System.out.println(TreeNode.preTraverse(tree));
        mirror(tree);
        System.out.println(TreeNode.preTraverse(tree));
    }

    public static void mirror(TreeNode root) {
        if (root == null) {
            return;
        }

        TreeNode temp = root.left;
        root.left = root.right;
        root.right = temp;

        mirror(root.left);
        mirror(root.right);
    }
}
