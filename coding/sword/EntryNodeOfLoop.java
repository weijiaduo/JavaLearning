package com.wjd.sword;

import com.wjd.sword.structure.ListNode;

/**
 * 给一个链表，若其中包含环，请找出该链表的环的入口结点，否则，输出null。
 */
public class EntryNodeOfLoop {

    public static void main(String[] args) {
        ListNode pHead = new ListNode(1);
        System.out.println(entryNodeOfLoop(pHead));
    }

    public static ListNode entryNodeOfLoop(ListNode pHead) {
        if (pHead != null) {
            boolean existLoop = false;
            ListNode p = pHead, q = pHead;
            while (q != null) {
                p = p.next;
                if (q.next != null) {
                    q = q.next;
                }
                q = q.next;
                if (p != null && q != null && (q == p || q.next == p)) {
                    existLoop = true;
                    break;
                }
            }

            if (existLoop) {
                q = p;
                int loopLen = 1;
                while (p.next != q) {
                    p = p.next;
                    loopLen++;
                }

                p = q = pHead;
                for (int i = 0; i < loopLen; i++) {
                    q = q.next;
                }

                while (p != q) {
                    p = p.next;
                    q = q.next;
                }

                return p;
            }
        }

        return null;
    }
}
