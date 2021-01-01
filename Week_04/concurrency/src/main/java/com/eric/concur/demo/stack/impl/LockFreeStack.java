package com.eric.concur.demo.stack.impl;

import com.eric.concur.demo.model.StackNode;
import com.eric.concur.demo.stack.Stack;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.LockSupport;

/**
 * use AtomicInteger and AtomicReference to implement lock-free stack
 */
public class LockFreeStack<T> implements Stack<T> {
    private AtomicReference<StackNode<T>> headRef = new AtomicReference<>();
    private AtomicInteger noOfOps = new AtomicInteger(0);

    public Integer getOps() {
        return noOfOps.get();
    }

    public T pop() {
        StackNode<T> currentNode = headRef.get();
        while(currentNode != null) {
            StackNode<T> newHeadNode = currentNode.getNext();
            if (headRef.compareAndSet(currentNode, newHeadNode)) break;
            else {
                LockSupport.parkNanos(1);
                currentNode = headRef.get();
            }
        }
        noOfOps.incrementAndGet();
        return currentNode == null ? null : currentNode.getValue();

    }

    public void push(T value) {
        StackNode<T> newStkNode = new StackNode<>(value);
        while(true) {
            StackNode<T> currentNode = headRef.get();
            newStkNode.setNext(currentNode);
            if (headRef.compareAndSet(currentNode, newStkNode)) break;
            else LockSupport.parkNanos(1);
        }
        noOfOps.incrementAndGet();
    }

}
