package io.kimmking.dubbo.demo.api.exceptions;

public class InsufficientBalanceException extends RuntimeException {
    public InsufficientBalanceException(Exception e) {
        super(e);
    }

    public InsufficientBalanceException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public InsufficientBalanceException(String message) {
        super(message);
    }
}
