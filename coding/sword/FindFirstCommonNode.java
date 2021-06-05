package com.wjd.sword;

import com.wjd.sword.structure.ListNode;

import java.util.ArrayList;

/**
 * 输入两个链表，找出它们的第一个公共结点。
 *
 */
public class FindFirstCommonNode {

    public static void main(String[] args) {
        ListNode p1 = new ListNode(1);
        ListNode p2 = new ListNode(2);
        ListNode com = new ListNode(0);
        com.next = new ListNode(3);
        com.next.next = new ListNode(4);
        p1.next = com;
        p2.next = com;
        System.out.println(findFirstCommonNode(p1, p2));
        System.out.println(findFirstCommonNode1(p1, p2));
    }

    public static ListNode findFirstCommonNode(ListNode pHead1, ListNode pHead2) {
        if (pHead1 != null && pHead2 != null) {
            ArrayList<ListNode> stack1 = new ArrayList<>();
            ArrayList<ListNode> stack2 = new ArrayList<>();
            ListNode p = pHead1, q = pHead2;
            while (p != null) {
                stack1.add(p);
                p = p.next;
            }
            while (q != null) {
                stack2.add(q);
                q = q.next;
            }

            // 从尾到头寻找第一个不同节点
            int p1 = stack1.size() - 1, p2 = stack2.size() - 1;
            while (p1 >= 0 && p2 >= 0) {
                if (stack1.get(p1) != stack2.get(p2)) {
                    break;
                }
                p1--;
                p2--;
            }

            // 验证是否有公共节点
            if (p1 < stack1.size() - 1 && p2 < stack2.size() - 1) {
                if (stack1.get(p1 + 1) == stack2.get(p2 + 1)) {
                    return stack1.get(p1 + 1);
                }
            }
        }

        return null;
    }

    public static ListNode findFirstCommonNode1(ListNode pHead1, ListNode pHead2) {
        if (pHead1 != null && pHead2 != null) {
            ListNode p1 = pHead1, p2 = pHead2;
            int k1 = 0, k2 = 0;
            while (p1 != null || p2 != null) {
                if (p1 != null) {
                    k1++;
                    p1 = p1.next;
                }
                if (p2 != null) {
                    k2++;
                    p2 = p2.next;
                }
            }

            // 长链表先走
            p1 = pHead1;
            p2 = pHead2;
            if (k1 > k2) {
                while (k1 > k2) {
                    k1--;
                    p1 = p1.next;
                }
            } else {
                while (k2 > k1) {
                    k2--;
                    p2 = p2.next;
                }
            }

            // 寻找第一个公共节点
            while (p1 != null && p2 != null && p1 != p2){
                p1 = p1.next;
                p2 = p2.next;
            }

            // 验证是否有公共节点
            if (p1 != null && p2 != null) {
                return p1;
            }
        }

        return null;
    }
}
