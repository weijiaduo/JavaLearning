package com.wjd.sword;

import com.wjd.sword.structure.TreeLinkNode;

/**
 * 给定一个二叉树和其中的一个结点，请找出中序遍历顺序的下一个结点并且返回。
 * 注意，树中的结点不仅包含左右子结点，同时包含指向父结点的指针。
 */
public class NextTreeLinkNode {

    public static void main(String[] args) {

    }

    public TreeLinkNode getNext(TreeLinkNode pNode) {
        if (pNode == null) {
            return null;
        }

        TreeLinkNode p = pNode;
        if (p.right != null) {
            p = p.right;
            while (p.left != null) {
                p = p.left;
            }
            return p;
        } else {
            TreeLinkNode parent = p.next;
            while (parent != null && parent.right == p) {
                p = parent;
                parent = parent.next;
            }
            return parent;
        }
    }

}
