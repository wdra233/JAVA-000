package com.eric.concur.demo;

import com.eric.concur.demo.stack.Stack;
import com.eric.concur.demo.stack.impl.ClassicStack;
import com.eric.concur.demo.stack.impl.LockFreeStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StackTest {
    public static void main(String[] args) throws InterruptedException {
//        Stack<Integer> stk = new LockFreeStack<>();
        Stack<Integer> stk = new ClassicStack<>();
        Random rdnGen = new Random();

        // init stk
        for(int i = 0; i < 100; i++) {
            stk.push(rdnGen.nextInt(100));
        }

        List<Thread> threads = new ArrayList<>();
        for(int i = 0; i < 2; i++) {
            Thread pushThread = new Thread(() -> {
                System.out.println("Start to push value to stk");
                while(true) {
                    stk.push(rdnGen.nextInt(1000));
                }
            });
//            pushThread.setDaemon(true);
            threads.add(pushThread);
        }

        for(int i = 0; i < 2; i++) {
            Thread popThread = new Thread(() -> {
                System.out.println("Start to pop value from stk");
                while (true) {
                    stk.pop();
                }
            });
//            popThread.setDaemon(true);
            threads.add(popThread);
        }

        for(Thread thread : threads) {
            thread.start();
        }
        Thread.sleep(5000);
        System.out.println("Number of ops in 5s is: " + stk.getOps());
    }
}
