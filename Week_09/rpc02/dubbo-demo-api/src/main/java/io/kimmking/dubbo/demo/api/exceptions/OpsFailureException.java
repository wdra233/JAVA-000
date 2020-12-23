package io.kimmking.dubbo.demo.api.exceptions;

public class OpsFailureException extends RuntimeException {
    public OpsFailureException(Exception e) {
        super(e);
    }

    public OpsFailureException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public OpsFailureException(String message) {
        super(message);
    }
}
