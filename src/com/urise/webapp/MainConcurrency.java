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
        for (int i = 0; i < 100000; i++) {
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
    }

    private static synchronized void inc() {
        counter++;
    }
}
