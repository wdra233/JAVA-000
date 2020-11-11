package com.eric.concur.future;

import com.eric.concur.util.FiboUtil;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class CompletableFutureDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        long start=System.currentTimeMillis();
        // 在这里创建一个线程或线程池，
        // 异步执行 下面方法
        // completable future如果用join() 则会一直等到结果才接着往下进行
        CompletableFuture<Integer> result = CompletableFuture
                .supplyAsync(() -> FiboUtil.sum(36))
                .thenApplyAsync(res -> {
                    try {
                        Thread.sleep(3000);
                        System.out.println("Second task");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        // rethrow exception
                        throw new RuntimeException("exception test!!");
                    }
                    return res;
                })
                .exceptionally(e->{
                    System.out.println(e.getMessage());
                    return 0;
                });
        try {
            Thread.sleep(1000);
            System.out.println("First Task");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("异步计算结果为："+ result.get());
        System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");

        // 然后退出main线程
    }
}
