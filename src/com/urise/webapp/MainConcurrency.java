package com.urise.webapp;

import java.util.ArrayList;
import java.util.List;


public class MainConcurrency {
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
        List<Thread> threadList = new ArrayList<>();
        for (int i = 0; i < 10_000; i++) {
            Thread thread = new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    inc();
                }
                System.out.println(Thread.currentThread() + ", " + Thread.currentThread().getState());
            });
            thread.start();
            threadList.add(thread);
        }
        threadList.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
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
