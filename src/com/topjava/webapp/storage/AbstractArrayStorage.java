package com.topjava.webapp.storage;

import com.topjava.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORADE_LIMITED = 10000;
    protected Resume[] storage = new Resume[STORADE_LIMITED];
    protected int size = 0;

    public int size() {
        return size;
    }

    public Resume get(String uuid) {
        int index = searchIndex(uuid);
        if (index != -1) {
            return storage[index];
        }
        System.out.printf("Резюме %s не найден в базе%n", uuid);
        return null;
    }

    protected abstract int searchIndex(String uuid);

}
