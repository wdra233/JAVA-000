package com.eric.concur.future;

import com.eric.concur.util.FiboUtil;

import java.util.concurrent.*;

public class FutureTaskDemo {
    public static void main(String[] args) {
        long start=System.currentTimeMillis();
        // 在这里创建一个线程或线程池，
        // 异步执行 下面方法
        FutureTask<Integer> task = new FutureTask<>(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                Thread.sleep(3000);
                System.out.println("Second task");
                return FiboUtil.sum(36);
            }
        });
        Thread taskThread = new Thread(task);
        taskThread.start();
        try {
            Thread.sleep(1000);
            System.out.println("First task");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //这是得到的返回值
        // 确保  拿到result 并输出
        try {
            taskThread.join();
            System.out.println("异步计算结果为："+ task.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");

        // 然后退出main线程
    }
}
