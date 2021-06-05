package com.wjd.sword;


import com.wjd.sword.structure.ListNode;

import java.util.ArrayList;
import java.util.Collections;

public class PrintListFromTailToHead {

    public static void main(String[] args) {

    }

    public static ArrayList<Integer> printListFromTailToHead(ListNode listNode) {
        ArrayList<Integer> res = new ArrayList<>();

        ListNode p = listNode;
        while (p != null) {
            res.add(p.val);
            p = p.next;
        }

        if (res.size() > 0) {
            Collections.reverse(res);
        }

        return res;
    }
}
