package com.eric.concur.tool;

import com.eric.concur.util.FiboUtil;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierDemo {
    private static int result = 0;

    public static void main(String[] args) {
        CyclicBarrier cyc = new CyclicBarrier(1, new Runnable() {
            @Override
            public void run() {
                // 确保  拿到result 并输出
                System.out.println("异步计算结果为："+ result);
            }
        });
        long start=System.currentTimeMillis();
        // 在这里创建一个线程或线程池，
        // 异步执行 下面方法
        new Thread(new Runnable() {
            @Override
            public void run() {
                result = FiboUtil.sum(36);
                try {
                    cyc.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");

        // 然后退出main线程
    }
}
