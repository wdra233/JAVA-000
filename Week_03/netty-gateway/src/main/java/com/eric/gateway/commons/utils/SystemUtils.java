package com.eric.gateway.commons.utils;

public class SystemUtils {
    public static int getCoreNum() {
        return Runtime.getRuntime().availableProcessors() * 2;
    }
}
