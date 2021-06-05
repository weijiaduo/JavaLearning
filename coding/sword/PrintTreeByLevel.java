package com.wjd.sword;

import com.wjd.sword.structure.TreeNode;

import java.util.ArrayList;
import java.util.LinkedList;

public class PrintTreeByLevel {

    public static void main(String[] args) {
        String[] s = {"8","6","10","5","7","9","11"};
        TreeNode tree = TreeNode.buildTree(s);

        System.out.println(print(tree));
    }

    public static ArrayList<ArrayList<Integer>> print(TreeNode pRoot) {
        ArrayList<ArrayList<Integer>> res = new ArrayList<>();
        if (pRoot != null) {
            LinkedList<TreeNode> queue = new LinkedList<>();
            ArrayList<Integer> levelList = new ArrayList<>();
            queue.add(pRoot);
            int levelNum =  queue.size();
            while (!queue.isEmpty()) {
                TreeNode cur = queue.poll();
                levelList.add(cur.val);
                if (cur.left != null) {
                    queue.add(cur.left);
                }
                if (cur.right != null) {
                    queue.add(cur.right);
                }
                if (--levelNum == 0) {
                    res.add(new ArrayList<>(levelList));
                    levelList.clear();
                    levelNum = queue.size();
                }
            }
        }
        return res;
    }
}
