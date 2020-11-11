package com.eric.concur.other;

import com.eric.concur.util.FiboUtil;

public class SynchronizeDemo {
    private static int result = 0;
    private static final Object lock = new Object();

    public static void main(String[] args) throws InterruptedException {

        long start=System.currentTimeMillis();
        // 在这里创建一个线程或线程池，
        // 异步执行 下面方法
        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (lock) {
                    result = FiboUtil.sum(36);
                    lock.notify();
                }
            }
        }).start();
        // 确保子线程先获得锁
        Thread.sleep(1000);
        synchronized (lock) {
            // 确保  拿到result 并输出
            System.out.println("异步计算结果为："+ result);
        }

        System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");

        // 然后退出main线程
    }
}
