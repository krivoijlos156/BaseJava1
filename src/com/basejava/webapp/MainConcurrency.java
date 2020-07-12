package com.basejava.webapp;

import java.util.ArrayList;
import java.util.List;

public class MainConcurrency {
    public static final int THREADS_NUMBER = 10000;
    private int counter;
    private static final Object LOCK = new Object();

    public static void main(String[] args) throws InterruptedException {
        final MainConcurrency mainConcurrency = new MainConcurrency();
//        System.out.println(Thread.currentThread().getName());

        Thread thread0 = new Thread() {
            @Override
            public void run() {
                mainConcurrency.mathodA();
                System.out.println(getName() + ", " + getState());
            }
        };
        thread0.start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                mainConcurrency.mathodB();
                System.out.println(Thread.currentThread().getName() + ", " + Thread.currentThread().getState());
            }
        }).start();
//        System.out.println(thread0.getState());
    }

    private synchronized int mathodA() {
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return +mathodB();
    }

    private synchronized int mathodB() {
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return +mathodA();
    }
//        List<Thread> threads = new ArrayList<>(THREADS_NUMBER);
//
//        for (int i = 0; i < THREADS_NUMBER; i++) {
//            Thread thread = new Thread(() -> {
//                for (int j = 0; j < 100; j++) {
//                    mainConcurrency.inc();
//                }
//            });
//            thread.start();
//            threads.add(thread);
//        }
//
//        threads.forEach(t -> {
//            try {
//                t.join();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        });
//        System.out.println(mainConcurrency.counter);

//    }
    //    private synchronized void inc() {
//        counter++;
//    }
}
