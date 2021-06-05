package com.wjd.sword;

import com.wjd.sword.structure.TreeNode;

public class ReConstructBinaryTree {

    public static void main(String[] args) {
        int[] pre = {1,2,4,7,3,5,6,8};
        int[] in = {4,7,2,1,5,3,8,6};

        TreeNode tree = reConstructBinaryTree(pre, in);
        System.out.println(TreeNode.preTraverse(tree));
        System.out.println(TreeNode.innerTraverse(tree));
        System.out.println(TreeNode.postTraverse(tree));
    }

    public static TreeNode reConstructBinaryTree(int [] pre, int [] in){
        if (pre == null || in == null || pre.length!=in.length){
            return null;
        }

        TreeNode tree = TreeNode.buildByPreAndInner(pre, 0, in, 0, pre.length);

        return tree;
    }
}
