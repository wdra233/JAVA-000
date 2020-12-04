package com.eric.rw.route;

import com.eric.rw.consts.AppConsts;

/**
 * 采用ThreadLocal保证线程安全，由DataSourceHolder完成
 */
public class DataSourceHolder {
    private static final ThreadLocal<String> dsHolder = new ThreadLocal<>();

    public static void set(String type) {
        dsHolder.set(type);
    }

    public static String get() {
        return dsHolder.get();
    }

    public static void master() {
        set(AppConsts.MASTER);
    }

    public static void slave() {
        set(AppConsts.SLAVE);
    }
}
