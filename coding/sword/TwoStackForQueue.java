package com.wjd.sword;

import java.util.Stack;

public class TwoStackForQueue {

    public static void main(String[] args) {
        TwoStackForQueue queue = new TwoStackForQueue();
        for (int i = 0; i < 5; i++) {
            queue.push(i);
        }

        for (int i = 0; i < 2; i++) {
            System.out.println(queue.pop());
        }

        for (int i = 0; i < 5; i++) {
            queue.push(i*3);
        }

        for (int i = 0; i < 8; i++) {
            System.out.println(queue.pop());
        }
    }

    Stack<Integer> stack1 = new Stack<Integer>();
    Stack<Integer> stack2 = new Stack<Integer>();

    public void push(int node) {
        stack1.push(node);
    }

    public int pop() {
        if (stack1.empty() && stack2.empty()){
            throw new RuntimeException("Queue is empty!");
        }
        if (stack2.empty()){
            while (!stack1.empty()){
                stack2.push(stack1.pop());
            }
        }
        return stack2.pop();
    }
}
