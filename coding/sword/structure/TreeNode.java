package com.wjd.sword.structure;

import java.util.LinkedList;

public class TreeNode {
    public int val;
    public TreeNode left;
    public TreeNode right;

    public TreeNode(int val) {
        this.val = val;
    }

    public static TreeNode buildByPreAndInner(int[] pre, int ps, int[] in, int is, int length) {
        if (length <= 0) {
            return null;
        }

        TreeNode root = new TreeNode(pre[ps]);
        int rootIndex = is;
        while (rootIndex < in.length && in[rootIndex] != pre[ps]) {
            rootIndex++;
        }

        int len = rootIndex - is;
        root.left = buildByPreAndInner(pre, ps + 1, in, is, len);
        root.right = buildByPreAndInner(pre, ps + 1 + len, in, rootIndex + 1, length - len - 1);

        return root;
    }

    public static TreeNode buildTree(String[] s){
        if (s == null || s.length == 0){
            return null;
        }

        for (int i = 0; i < s.length; i++) {
            if (s[i] == "#"){
                s[i] = null;
            }
        }

        return build(s);
    }

    /**
     * 根据广度优先遍历序列（空节点为null）生成树
     *
     * @param s
     * @return
     */
    private static TreeNode build(String[] s) {
        if (s == null || s.length == 0) {
            return null;
        }

        int i = 0;
        TreeNode tree = new TreeNode(Integer.parseInt(s[i++]));
        LinkedList<TreeNode> queue = new LinkedList<>();
        queue.add(tree);
        while (!queue.isEmpty() && i < s.length) {
            TreeNode curNode = queue.poll();

            // 左节点
            if (i < s.length) {
                String leftVal = s[i++];
                TreeNode leftNode = null;
                if (leftVal != null) {
                    leftNode = new TreeNode(Integer.parseInt(leftVal));
                    queue.add(leftNode);
                }
                curNode.left = leftNode;
            }

            // 右节点
            if (i < s.length) {
                String rightVal = s[i++];
                TreeNode rightNode = null;
                if (rightVal != null) {
                    rightNode = new TreeNode(Integer.parseInt(rightVal));
                    queue.add(rightNode);
                }
                curNode.right = rightNode;
            }
        }

        return tree;
    }

    /**
     * 前序遍历
     *
     * @param tree
     * @return
     */
    public static String preTraverse(TreeNode tree){
        if (tree == null){
            return null;
        }

        StringBuilder sb = new StringBuilder();

        // 根结点
        sb.append(tree.val);

        // 左节点
        if (tree.left != null){
            sb.append(preTraverse(tree.left));
        }

        // 右节点
        if (tree.right != null){
            sb.append(preTraverse(tree.right));
        }

        return sb.toString();
    }

    /**
     * 中序遍历
     *
     * @param tree
     * @return
     */
    public static String innerTraverse(TreeNode tree){
        if (tree == null){
            return null;
        }

        StringBuilder sb = new StringBuilder();

        // 左节点
        if (tree.left != null){
            sb.append(innerTraverse(tree.left));
        }

        // 根结点
        sb.append(tree.val);

        // 右节点
        if (tree.right != null){
            sb.append(innerTraverse(tree.right));
        }

        return sb.toString();
    }

    /**
     * 后序遍历
     *
     * @param tree
     * @return
     */
    public static String postTraverse(TreeNode tree){
        if (tree == null){
            return null;
        }

        StringBuilder sb = new StringBuilder();

        // 左节点
        if (tree.left != null){
            sb.append(postTraverse(tree.left));
        }

        // 右节点
        if (tree.right != null){
            sb.append(postTraverse(tree.right));
        }

        // 根结点
        sb.append(tree.val);

        return sb.toString();
    }

    /**
     * 非递归循环前序遍历
     *
     * @param tree
     * @return
     */
    public static String preTraverseByLoop(TreeNode tree){
        StringBuilder sb = new StringBuilder();
        if (tree != null) {
            LinkedList<TreeNode> stack = new LinkedList<>();
            TreeNode cur = tree;
            while (cur != null || !stack.isEmpty()) {
                if (cur != null) {
                    stack.push(cur);
                    if (sb.length() > 0) {
                        sb.append(", ");
                    }
                    sb.append(cur.val);
                    cur = cur.left;
                } else {
                    cur = stack.pop();
                    cur = cur.right;
                }
            }
        }
        return sb.toString();
    }

    /**
     * 非递归循环中序遍历
     *
     * @param tree
     * @return
     */
    public static String innerTraverseByLoop(TreeNode tree){
        StringBuilder sb = new StringBuilder();
        if (tree != null) {
            LinkedList<TreeNode> stack = new LinkedList<>();
            TreeNode cur = tree;
            while (cur != null || !stack.isEmpty()) {
                if (cur != null) {
                    stack.push(cur);
                    cur = cur.left;
                } else {
                    cur = stack.pop();
                    if (sb.length() > 0) {
                        sb.append(", ");
                    }
                    sb.append(cur.val);
                    cur = cur.right;
                }
            }
        }
        return sb.toString();
    }
}
