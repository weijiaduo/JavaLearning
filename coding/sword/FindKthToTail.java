package com.wjd.sword;

import com.wjd.sword.structure.ListNode;

/**
 * 输入一个链表，输出该链表中倒数第k个结点。
 *
 */
public class FindKthToTail {

    public static void main(String[] args) {
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);

        System.out.println(findKthToTail(head, 1).val);
    }

    public static ListNode findKthToTail (ListNode head, int k) {
        if (head == null || k <= 0) {
            return null;
        }

        ListNode p, q;
        p = q = head;
        int i = 0;
        for (; p != null; i++) {
            if (i >= k)
                q = q.next;
            p = p.next;
        }

        return i < k ? null : q;
    }
}
