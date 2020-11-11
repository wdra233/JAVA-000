package com.eric.concur.other;

import com.eric.concur.util.FiboUtil;

public class VolatileDemo {
    private static volatile int result = 0;
    private static volatile boolean finished = false;

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
                result = FiboUtil.sum(36);
                finished = true;
            }
        }).start();

        while(!finished) {
            System.out.println("waiting for result...");
        }
        // 确保拿到result 并输出
        System.out.println("异步计算结果为："+ result);


        System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");

        // 然后退出main线程
    }
}
