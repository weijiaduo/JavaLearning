package com.wjd.sword;

import com.wjd.sword.structure.ListNode;

/**
 * 在一个排序的链表中，存在重复的结点，请删除该链表中重复的结点，重复的结点不保留，返回链表头指针。
 * 例如，链表1->2->3->3->4->4->5 处理后为 1->2->5
 */
public class DeleteDuplicationListNode {

    public static void main(String[] args) {
        ListNode list = new ListNode(1);
        list.next = new ListNode(3);
        list.next.next = new ListNode(3);
        list.next.next.next = new ListNode(3);
        list.next.next.next.next = new ListNode(4);
        list.next.next.next.next.next = new ListNode(4);
        list.next.next.next.next.next.next = new ListNode(5);
        System.out.println(deleteDuplication(list));
    }

    public static ListNode deleteDuplication(ListNode pHead) {
        if (pHead != null) {
            ListNode newHead = new ListNode(0), tail = newHead;
            ListNode p = pHead, q;
            while (p != null) {
                if (p.next != null && p.val == p.next.val) {
                    q = p;
                    while (p != null && p.val == q.val) {
                        p = p.next;
                    }
                } else {
                    tail.next = p;
                    tail = p;
                    p = p.next;
                    tail.next = null;
                }
            }
            pHead = newHead.next;
        }
        return pHead;
    }
}
