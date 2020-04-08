package com.topjava.webapp.storage;

import com.topjava.webapp.model.Resume;


public class ArrayStorage extends AbstractArrayStorage {


    @Override
    protected void saveIf(Resume resume) {
        storage[size] = resume;
    }

    @Override
    protected void deleteIf(String uuid) {
        storage[searchIndex(uuid)] = storage[size - 1];
        storage[size - 1] = null;
        size--;
    }

    @Override
    protected int searchIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
