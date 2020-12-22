package io.kimmking.rpcfx.api;

public class RpcfxException extends Exception {
    public RpcfxException() {
        super();
    }

    public RpcfxException(String message) {
        super(message);
    }

    public RpcfxException(Exception e) {
        super(e);
    }

    public RpcfxException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
