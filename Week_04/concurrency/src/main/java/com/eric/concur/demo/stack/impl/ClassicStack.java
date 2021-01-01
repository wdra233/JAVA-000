package com.eric.concur.demo.stack.impl;

import com.eric.concur.demo.model.StackNode;
import com.eric.concur.demo.stack.Stack;

public class ClassicStack<T> implements Stack<T> {
    private int noOfOps = 0;
    private StackNode<T> headNode;

    public synchronized Integer getOps() {
        return noOfOps;
    }

    public synchronized void push(T value) {
        StackNode<T> newNode = new StackNode<>(value);
        newNode.setNext(headNode);
        headNode = newNode;
        noOfOps++;
    }

    public synchronized T pop() {
        if(headNode != null) {
            T value = headNode.getValue();
            headNode = headNode.getNext();
            noOfOps++;
            return value;
        }
        return null;
    }
}
