package com.topjava.webapp;

import com.topjava.webapp.model.Resume;
import com.topjava.webapp.storage.MapStorage;
import com.topjava.webapp.storage.Storage;


public class MainCollection {
    private static final Storage COLLECTION = new MapStorage();
    private static final Resume r1 = new Resume("uuid1", "Name1");
    private static final Resume r2 = new Resume("uuid2", "Name2");
    private static final Resume r3 = new Resume("uuid3", "Name3");
    private static final Resume r4 = new Resume("uuid1", "Name4");

    public static void main(String[] args) {

        COLLECTION.save(r1);
        COLLECTION.save(r2);
        COLLECTION.save(r3);

        System.out.println("Get r1: " + COLLECTION.get(r1.getUuid()));

        System.out.println("Get dummy: " + COLLECTION.get("dummy"));

        printAll();
        COLLECTION.update(r4);
        printAll();
        COLLECTION.delete(r2.getUuid());
        printAll();
        COLLECTION.clear();
        printAll();
    }

    static void printAll() {
        System.out.println("\nGet All");
        for (Resume r : COLLECTION.getAllSorted()) {
            System.out.println(r);
        }
    }
}