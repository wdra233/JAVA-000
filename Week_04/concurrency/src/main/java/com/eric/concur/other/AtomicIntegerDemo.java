package com.eric.concur.other;

import com.eric.concur.util.FiboUtil;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicIntegerDemo {
    private static final AtomicInteger atomicInteger = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {

        long start=System.currentTimeMillis();
        // 在这里创建一个线程或线程池，
        // 异步执行 下面方法
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                atomicInteger.set(FiboUtil.sum(36));
            }
        }).start();

        while(atomicInteger.get() == 0) {
            System.out.println("waiting for result...");
        }
        // 确保拿到result 并输出
        System.out.println("异步计算结果为："+ atomicInteger.get());


        System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");

        // 然后退出main线程
    }
}
