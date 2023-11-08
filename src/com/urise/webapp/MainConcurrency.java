package com.urise.webapp;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;


public class MainConcurrency {
    private static final int THREADS_COUNT = 10_000;
    private static int counter;
    private static final Object LOCK = new Object();

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

        System.out.println(thread0.getState());
        CountDownLatch latch = new CountDownLatch(THREADS_COUNT);
      //  List<Thread> threadList = new ArrayList<>();
        for (int i = 0; i < THREADS_COUNT; i++) {
            Thread thread = new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    inc();
                }
                latch.countDown();
                System.out.println(Thread.currentThread() + ", " + Thread.currentThread().getState());
            });
            thread.start();
           // threadList.add(thread);
        }
      /*  threadList.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });*/
        latch.await(10, TimeUnit.SECONDS);
        System.out.println(counter);
        System.out.println(thread0.getState());
        System.out.println(LazySingleton.getInstance().toString());
        int a = 5;
        int b = 6;
        new Thread(() -> sum(a, b)).start();
        sum(b, a);

    }

    private static void inc() {
        synchronized (LOCK) {
            counter++;
        }
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
