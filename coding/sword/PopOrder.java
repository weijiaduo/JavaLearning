package com.wjd.sword;

import java.util.LinkedList;

/**
 * 输入两个整数序列，第一个序列表示栈的压入顺序，请判断第二个序列是否可能为该栈的弹出顺序。
 * 假设压入栈的所有数字均不相等。例如序列1,2,3,4,5是某栈的压入顺序，
 * 序列4,5,3,2,1是该压栈序列对应的一个弹出序列，但4,3,5,1,2就不可能是该压栈序列的弹出序列。
 * （注意：这两个序列的长度是相等的）
 *
 */
public class PopOrder {

    public static void main(String[] args) {
        int[] push = {1,2,3,4,5};
        int[] pop = {4,5,3,2,1};
        System.out.println(isPopOrder(push, pop));
    }

    public static boolean isPopOrder(int[] pushA, int[] popA) {
        if (pushA == null || popA == null || pushA.length != popA.length) {
            return false;
        }

        if (pushA.length > 0) {
            LinkedList<Integer> stack = new LinkedList<>();
            int pushIndex = 0, popIndex = 0;
            while (pushIndex < pushA.length) {
                stack.push(pushA[pushIndex++]);
                while (popIndex < popA.length && stack.peek() == popA[popIndex]) {
                    stack.pop();
                    popIndex++;
                }
            }

            if (stack.isEmpty()) {
                return true;
            }
        }

        return false;
    }
}
