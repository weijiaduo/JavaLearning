package com.wjd.sword;

import com.wjd.sword.structure.TreeNode;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * 请实现一个函数按照之字形打印二叉树，
 * 即第一行按照从左到右的顺序打印，第二层按照从右至左的顺序打印，
 * 第三行按照从左到右的顺序打印，其他行以此类推。
 */
public class ZPrintTree {

    public static void main(String[] args) {
        String[] s = {"8","6","10","5","7","9","11"};
        TreeNode tree = TreeNode.buildTree(s);

        System.out.println(print(tree));
    }

    public static ArrayList<ArrayList<Integer>> print(TreeNode pRoot) {
        ArrayList<ArrayList<Integer>> res = new ArrayList<>();

        if (pRoot != null) {
            // 双向栈
            LinkedList<TreeNode> doubleStack = new LinkedList<>();
            doubleStack.add(pRoot);
            int level = 0, levelNum = doubleStack.size();
            ArrayList<Integer> levelList = new ArrayList<>();
            while (!doubleStack.isEmpty()) {
                TreeNode cur;
                if (level % 2 == 0) {
                    cur = doubleStack.pollLast();
                    if (cur.left != null) {
                        doubleStack.push(cur.left);
                    }
                    if (cur.right != null) {
                        doubleStack.push(cur.right);
                    }
                } else {
                    cur = doubleStack.pop();
                    if (cur.right != null) {
                        doubleStack.add(cur.right);
                    }
                    if (cur.left != null) {
                        doubleStack.add(cur.left);
                    }
                }
                levelList.add(cur.val);
                if (--levelNum == 0) {
                    res.add(new ArrayList<>(levelList));
                    levelList.clear();
                    levelNum = doubleStack.size();
                    level++;
                }
            }
        }

        return res;
    }
}
