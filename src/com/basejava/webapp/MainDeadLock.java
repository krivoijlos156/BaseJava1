package com.basejava.webapp;

public class MainDeadLock {
    final static Object object1 = new Object();
    final static Object object2 = new Object();

    public static void main(String[] args) {
        doLock(object1, object2);
        doLock(object2, object1);
    }

    private static void doLock(Object object2, Object object1) {
        new Thread(() -> {
            synchronized (object2) {
                System.out.println(Thread.currentThread().getName() + " take object1");
                try {
                    Thread.sleep(10);
                } catch (InterruptedException ignored) {
                }
                System.out.println(Thread.currentThread().getName() + " waiting object2...");
                synchronized (object1) {
                    System.out.println(Thread.currentThread().getName() + "  lock 1 & 2...");
                }
            }
        }).start();
    }
}
