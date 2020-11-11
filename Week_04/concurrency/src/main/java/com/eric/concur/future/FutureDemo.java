package com.eric.concur.future;

import com.eric.concur.util.FiboUtil;

import java.util.concurrent.*;

public class FutureDemo {


    public static void main(String[] args) {

        long start=System.currentTimeMillis();
        // 在这里创建一个线程或线程池，
        // 异步执行 下面方法
        ExecutorService executor = Executors.newFixedThreadPool(1);
        Future<Integer> result = executor.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                Thread.sleep(3000);
                System.out.println("Second task");
                return FiboUtil.sum(36);
            }
        });
        executor.shutdown();
        try {
            Thread.sleep(1000);
            System.out.println("First task");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //这是得到的返回值
        // 确保拿到result 并输出
        try {
            System.out.println("异步计算结果为："+result.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");

        // 然后退出main线程
    }
}
