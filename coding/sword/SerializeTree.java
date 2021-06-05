package com.wjd.sword;

import com.wjd.sword.structure.TreeNode;

import java.util.LinkedList;

/**
 * 请实现两个函数，分别用来序列化和反序列化二叉树
 */
public class SerializeTree {

    public static void main(String[] args) {
        String[] s = {"8","7","17","9","2","3","5","#","#","#","#","26","#","#","3","#","#","#","11"};
        TreeNode tree = TreeNode.buildTree(s);

        String serial = serialize(tree);
        System.out.println(serial);
        String deserial = TreeNode.preTraverse(deserialize(serial));
        System.out.println(deserial);
    }

    public static String serialize(TreeNode root) {
        StringBuilder sb = new StringBuilder();

        if (root != null) {
            LinkedList<TreeNode> queue = new LinkedList<>();
            queue.add(root);

            int count = 1;
            while (count > 0 && !queue.isEmpty()) {
                TreeNode cur = queue.poll();
                if (sb.length() != 0) {
                    sb.append(",");
                }
                if (cur != null) {
                    sb.append(cur.val);
                    count--;

                    queue.add(cur.left);
                    if (cur.left != null) {
                        count++;
                    }
                    queue.add(cur.right);
                    if (cur.right != null) {
                        count++;
                    }
                } else {
                    sb.append('#');
                }
            }
        }

        return sb.toString();
    }

    public static TreeNode deserialize(String str) {
        TreeNode root = null;

        if (checkValidSerial(str)) {
            String[] nums = str.split(",");
            root = new TreeNode(Integer.parseInt(nums[0]));
            LinkedList<TreeNode> queue = new LinkedList<>();
            queue.add(root);

            int index = 1;
            while (index < nums.length && !queue.isEmpty()) {
                TreeNode cur = queue.poll();

                // 左节点
                TreeNode left = null;
                if (index < nums.length && !"#".equals(nums[index])) {
                    left = new TreeNode(Integer.parseInt(nums[index]));
                    queue.add(left);
                }
                cur.left = left;
                index++;

                TreeNode right = null;
                if (index < nums.length && !"#".equals(nums[index])) {
                    right = new TreeNode(Integer.parseInt(nums[index]));
                    queue.add(right);
                }
                cur.right = right;
                index++;
            }
        }

        return root;
    }

    /**
     * 验证是否是正确的序列化字符串
     * @param s
     * @return
     */
    private static boolean checkValidSerial(String s) {
        if (s == null || s.length() <= 0) {
            return false;
        }

        String[] str = s.split(",");
        if (str.length <= 0) {
            return false;
        }

        try {
            String root = str[0];
            Integer.parseInt(root);
            for (int i = 1; i < str.length; i++) {
                String node = str[i];
                if (!"#".equals(node)) {
                    Integer.parseInt(node);
                }
            }
        } catch (Exception e) {
            return false;
        }

        return true;
    }
}
