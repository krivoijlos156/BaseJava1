package com.topjava.webapp;

import com.topjava.webapp.model.Element;
import com.topjava.webapp.model.Resume;
import com.topjava.webapp.model.SectionType;
import com.topjava.webapp.storage.AbstractArrayStorage;
import com.topjava.webapp.storage.SortedArrayStorage;

import java.util.ArrayList;
import java.util.List;

public class MainTestArrayStorage {
    static final AbstractArrayStorage ARRAY_STORAGE = new SortedArrayStorage();

    public static void main(String[] args) {
        Resume r1 = new Resume("uuid1", "Name1");
        Resume r2 = new Resume("uuid2", "Name2");
        Resume r3 = new Resume("uuid3", "Name3");
        Resume r4 = new Resume("uuid1", "Name4");
        List<Element> list = new ArrayList<>();
        list.add(new Element("ad"));
        list.add(new Element("adw"));
        list.add(new Element("ad2"));

        r1.setSection(SectionType.PERSONAL, list);
        List<Element> section = r1.getSection(SectionType.PERSONAL);


        ARRAY_STORAGE.save(r1);
        ARRAY_STORAGE.save(r2);
        ARRAY_STORAGE.save(r3);
        ARRAY_STORAGE.save(r3);

        System.out.println("Get r1: " + ARRAY_STORAGE.get(r1.getUuid()));
        System.out.println("Size: " + ARRAY_STORAGE.size());

        System.out.println("Get dummy: " + ARRAY_STORAGE.get("dummy"));

        printAll();
        ARRAY_STORAGE.update(r4);
        printAll();
        ARRAY_STORAGE.delete(r2.getUuid());
        printAll();
        ARRAY_STORAGE.clear();
        printAll();

        int t = 0;
        do {
            t++;
        } while (t <= 10000);

        System.out.println("Size: " + ARRAY_STORAGE.size());
    }

    static void printAll() {
        System.out.println("\nGet All");
        for (Resume r : ARRAY_STORAGE.getAllSorted()) {
            System.out.println(r);
        }
    }
}