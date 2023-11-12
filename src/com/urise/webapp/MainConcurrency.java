package com.urise.webapp;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class MainConcurrency {
    private static final int THREADS_COUNT = 10_000;
    private static int counter;
    private final AtomicInteger atomicCounter = new AtomicInteger();
    //private static final Object LOCK = new Object();
    private static final Lock lock = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
        System.out.println(Thread.currentThread().getName());
        Thread thread0 = new Thread() {
            @Override
            public void run() {
                System.out.println(getName() + ", " + getState());
            }
        };
        thread0.start();

        new Thread(() ->
                System.out.println(Thread.currentThread().getName() + ", " + Thread.currentThread().getState())
        ).start();
        MainConcurrency mainConcurrency = new MainConcurrency();
        System.out.println(thread0.getState());
        CountDownLatch latch = new CountDownLatch(THREADS_COUNT);
        ExecutorService executorService = Executors.newCachedThreadPool();
        //  List<Thread> threadList = new ArrayList<>();
        for (int i = 0; i < THREADS_COUNT; i++) {
            executorService.submit(() -> {
                for (int j = 0; j < 100; j++) {
                    mainConcurrency.inc();
                }
                latch.countDown();
            });
//            Thread thread = new Thread(() -> {
//
//                System.out.println(Thread.currentThread() + ", " + Thread.currentThread().getState());
//            });
//            thread.start();
//            threadList.add(thread);
        }
      /*  threadList.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });*/
        latch.await(10, TimeUnit.SECONDS);
        executorService.shutdown();
        System.out.println(mainConcurrency.atomicCounter.get());
        System.out.println(thread0.getState());
        System.out.println(LazySingleton.getInstance().toString());
        int a = 5;
        int b = 6;
        //new Thread(() -> sum(a, b)).start();
        sum(b, a);


    }

    private void inc() {
        atomicCounter.incrementAndGet();
//        lock.lock();
//        try {
//            counter++;
//        } finally {
//            lock.unlock();
//        }
    }

    private static void sum(Integer a, Integer b) {
        synchronized (a) {
            try {
                Thread.sleep(100L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            int sum = a + b;
            synchronized (b) {
                int sum1 = a - b;
            }
        }
    }
}
