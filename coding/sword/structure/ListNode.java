package com.wjd.sword.structure;

public class ListNode {

    public int val;
    public ListNode next = null;

    public ListNode(int val) {
        this.val = val;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        sb.append(val);
        ListNode p = next;
        while (p != null) {
            sb.append(",");
            sb.append(p.val);
            p = p.next;
        }
        sb.append("]");
        return sb.toString();
    }
}
