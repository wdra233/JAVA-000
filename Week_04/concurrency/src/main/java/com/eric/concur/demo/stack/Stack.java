package com.eric.concur.demo.stack;

public interface Stack<T> {
    Integer getOps();
    T pop();
    void push(T value);
}
