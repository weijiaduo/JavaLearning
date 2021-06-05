package com.wjd.sword;

import com.wjd.sword.structure.RandomListNode;

/**
 * 输入一个复杂链表（每个节点中有节点值，以及两个指针，一个指向下一个节点，
 * 另一个特殊指针指向任意一个节点），返回结果为复制后复杂链表的head。
 * （注意，输出结果中请不要返回参数中的节点引用，否则判题程序会直接返回空）
 */
public class RandomListNodeClone {

    public static void main(String[] args) {

    }

    public RandomListNode clone(RandomListNode pHead) {
        if (pHead == null) {
            return null;
        }

        // 复制节点
        RandomListNode p = pHead, q;
        while (p != null) {
            q = p.next;
            RandomListNode t = new RandomListNode(p.label);
            p.next = t;
            t.next = q;
            p = q;
        }

        // 复制随机指向
        p = pHead;
        while (p != null) {
            q = p.next;
            if (p.random != null) {
                q.random = p.random.next;
            }
            p = q.next;
        }

        // 分离复制节点
        p = pHead;
        q = p.next;
        RandomListNode nHead = q;
        while (p != null) {
            p.next = p.next.next;
            p = p.next;
            if (q.next != null) {
                q.next = q.next.next;
                q = q.next;
            }
        }

        return nHead;
    }
}
