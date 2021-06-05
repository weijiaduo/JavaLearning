package com.wjd.sword;

import com.wjd.sword.structure.TreeNode;

/**
 * 输入两棵二叉树A，B，判断B是不是A的子结构。
 * （ps：我们约定空树不是任意一个树的子结构）
 *
 */
public class HasSubtree {

    public static void main(String[] args) {
        String[] s = {"8","8","7","9","2","#","#","#","#","4","7"};
        TreeNode tree = TreeNode.buildTree(s);

        String[] s1 = {"8", "9", "2"};
        TreeNode tree1 = TreeNode.buildTree(s1);

//        System.out.println(TreeNode.preTraverse(tree));
//        System.out.println(TreeNode.preTraverse(tree1));

        System.out.println(hasSubtree(tree,tree1));
        System.out.println(contain(tree.left, tree1));
    }

    public static boolean hasSubtree(TreeNode root1, TreeNode root2) {
        if (root1 == null || root2 == null) {
            return false;
        }

        boolean flag = false;
        if (root1.val == root2.val) {
            flag = contain(root1, root2);
        }
        if (!flag){
            flag = (hasSubtree(root1.left, root2) || hasSubtree(root1.right, root2));
        }

        return flag;
    }

    public static boolean contain(TreeNode root1, TreeNode root2) {
        if (root2 == null) {
            return true;
        }
        if (root1 == null) {
            return false;
        }

        if (root1.val == root2.val) {
            return (contain(root1.left, root2.left) && contain(root1.right, root2.right));
        }

        return false;
    }
}
