package com.basejava.webapp;

public class MainDeadLock {

    public static void main(String[] args) {
        new Thread(MainDeadLock::mathodA).start();
        new Thread(MainDeadLock::mathodB).start();
    }

    private static synchronized int mathodA() {
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return +mathodB();
    }

    private static synchronized int mathodB() {
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return +mathodA();
    }
}
