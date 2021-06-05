package com.wjd.sword;

import com.wjd.sword.structure.TreeNode;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * 从上往下打印出二叉树的每个节点，同层节点从左至右打印。
 *
 */
public class PrintTreeFromTopToBottom {

    public static void main(String[] args) {
        String[] s = {"8","8","7","9","2","#","#","#","#","4","7"};
        TreeNode tree = TreeNode.buildTree(s);

        ArrayList<Integer> rs = printFromTopToBottom(tree);
        System.out.println(rs);
    }

    public static ArrayList<Integer> printFromTopToBottom(TreeNode root) {
        ArrayList<Integer> result = new ArrayList<>();

        if (root != null) {
            LinkedList<TreeNode> queue = new LinkedList<>();
            queue.push(root);
            while (queue.size() > 0) {
                TreeNode cur = queue.poll();
                result.add(cur.val);

                if (cur.left != null) {
                    queue.add(cur.left);
                }
                if (cur.right != null) {
                    queue.add(cur.right);
                }
            }
        }

        return result;
    }
}
