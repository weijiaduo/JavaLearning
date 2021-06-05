package com.wjd.sword;

import com.wjd.sword.structure.ListNode;

public class MergeSortedList {

    public static void main(String[] args) {
        ListNode list1 = new ListNode(1);
        list1.next = new ListNode(3);
        list1.next.next = new ListNode(5);

        ListNode list2 = new ListNode(2);
        list2.next = new ListNode(2);
        list2.next.next = new ListNode(3);

        System.out.println(merge(list1, list2));
    }

    public static ListNode merge(ListNode list1, ListNode list2) {
        ListNode head = new ListNode(0);
        ListNode p = list1, q = list2, tail = head;
        while (p != null && q != null) {
            if (p.val <= q.val) {
                tail.next = p;
                p = p.next;
            } else {
                tail.next = q;
                q = q.next;
            }

            tail = tail.next;
        }

        if (p != null) {
            tail.next = p;
        }

        if (q != null) {
            tail.next = q;
        }

        return head.next;
    }
}
