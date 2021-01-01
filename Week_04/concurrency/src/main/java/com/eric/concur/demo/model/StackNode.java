package com.eric.concur.demo.model;

public class StackNode<T> {
    private T value;
    private StackNode<T> next;
    public StackNode(T value) {
        setValue(value);
    }

    public void setNext(StackNode<T> next) {
        this.next = next;
    }

    public StackNode<T> getNext() {
        return next;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }
}
