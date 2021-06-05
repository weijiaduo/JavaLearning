package com.wjd.sword;

import java.util.LinkedList;

/**
 * 定义栈的数据结构，请在该类型中实现一个能够得到栈中所含最小元素的min函数（时间复杂度应为O（1））。
 *
 */
public class MinStack {

    public static void main(String[] args) {
        MinStack stack = new MinStack();
        for (int i = 2; i < 5; i++) {
            stack.push(i);
        }

        System.out.println(stack.min());
    }

    LinkedList<Integer> stack = new LinkedList<>();
    LinkedList<Integer> minStack = new LinkedList<>();

    public void push(int node){
        stack.push(node);

        if (minStack.isEmpty() || node <= min()){
            minStack.push(node);
        }
    }

    public void pop(){
        if (stack.isEmpty()){
            throw new RuntimeException("Empty Stack!");
        }

        int res = stack.pop();
        if (res == min()){
            minStack.pop();
        }
    }

    public int top(){
        if (stack.isEmpty()){
            throw new RuntimeException("Empty Stack!");
        }

        return stack.peek();
    }

    public int min() {
        if (minStack.isEmpty()){
            throw new RuntimeException("Empty Stack!");
        }

        return minStack.peek();
    }
}
