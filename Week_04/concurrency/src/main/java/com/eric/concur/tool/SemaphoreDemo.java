package com.eric.concur.tool;

import com.eric.concur.util.FiboUtil;

import java.util.concurrent.Semaphore;

public class SemaphoreDemo {
    private static final Semaphore subSem = new Semaphore(1);
    private static final Semaphore mainSem = new Semaphore(0);
    private static int result = 0;

    public static void main(String[] args) throws InterruptedException {

        long start=System.currentTimeMillis();
        // 在这里创建一个线程或线程池，
        // 异步执行 下面方法
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    subSem.acquire(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                result = FiboUtil.sum(36);
                mainSem.release(1);
            }
        }).start();


        mainSem.acquire();
        // 确保  拿到result 并输出
        System.out.println("异步计算结果为："+ result);

        System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");

        // 然后退出main线程
    }
}
