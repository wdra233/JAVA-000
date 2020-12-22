package io.kimmking.rpcfx.server.invoker;

public abstract class Invoker {
    public abstract Object invoke(Object service, String method, Object[] params);
}
