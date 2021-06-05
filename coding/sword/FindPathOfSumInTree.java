package com.wjd.sword;

import com.wjd.sword.structure.TreeNode;

import java.util.ArrayList;
import java.util.Collections;

/**
 * 输入一颗二叉树的跟节点和一个整数，打印出二叉树中结点值的和为输入整数的所有路径。
 * 路径定义为从树的根结点开始往下一直到叶结点所经过的结点形成一条路径。
 * (注意: 在返回值的list中，数组长度大的数组靠前)
 *
 */
public class FindPathOfSumInTree {

    public static void main(String[] args) {
        String[] s = {"8","7","7","9","2","3","5","#","#","#","#","6","#","#","3","#","#","#","1"};
        TreeNode tree = TreeNode.buildTree(s);

        System.out.println(findPath(tree, 24));
    }

    public static ArrayList<ArrayList<Integer>> findPath(TreeNode root, int target) {
        ArrayList<ArrayList<Integer>> res = new ArrayList<>();

        if (root != null) {
            findPath(root, target, new ArrayList<>(), res);
            Collections.sort(res, (ArrayList<Integer> o1, ArrayList<Integer> o2) -> o2.size() - o1.size());
        }

        return res;
    }

    public static void findPath(TreeNode root, int target, ArrayList<Integer> path, ArrayList<ArrayList<Integer>> res) {
        if (root == null) {
            return;
        }

        path.add(root.val);
        target -= root.val;

        if (root.left == null && root.right == null) {
            if (target == 0) {
                res.add(new ArrayList<>(path));
            }
        } else {
            findPath(root.left, target, path, res);
            findPath(root.right, target, path, res);
        }

        path.remove(path.size() - 1);
    }
}
