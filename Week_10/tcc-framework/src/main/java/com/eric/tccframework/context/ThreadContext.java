package com.eric.tccframework.context;

public class ThreadContext {
    private static final ThreadLocal<String> TCC_TXID = new ThreadLocal<>();

    public static void set(String txid) {
        TCC_TXID.set(txid);
    }

    public static String get() {
        return TCC_TXID.get();
    }

    public static void remove() {
        TCC_TXID.remove();
    }
}
