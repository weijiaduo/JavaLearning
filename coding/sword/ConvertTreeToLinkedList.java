package com.wjd.sword;

import com.wjd.sword.structure.TreeNode;

/**
 * 输入一棵二叉搜索树，将该二叉搜索树转换成一个排序的双向链表。
 * 要求不能创建任何新的结点，只能调整树中结点指针的指向。
 *
 */
public class ConvertTreeToLinkedList {

    public static void main(String[] args) {
        String[] s = {"5","4","#","3","#","2","#","1"};
        TreeNode tree = TreeNode.buildTree(s);

        TreeNode root = convert(tree);
        TreeNode p = root;
        while (p != null){
            System.out.print(p.val + " ");
            p = p.right;
        }
    }

    private static TreeNode head = null;
    private static TreeNode prev = null;

    public static TreeNode convert(TreeNode pRootOfTree) {
        convertTo(pRootOfTree);
        return head;
    }

    public static void convertTo(TreeNode root){
        if (root == null){
            return;
        }

        convertTo(root.left);
        root.left = prev;
        if (prev != null){
            prev.right = root;
        }else {
            head = root;
        }
        prev = root;
        convertTo(root.right);
    }
}
