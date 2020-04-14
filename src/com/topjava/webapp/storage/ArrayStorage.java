package com.topjava.webapp.storage;

import com.topjava.webapp.model.Resume;


public class ArrayStorage extends AbstractArrayStorage {


    @Override
    protected void saveToStorage(Resume resume) {
        storage[size] = resume;
    }

    @Override
    protected void deleteItem(int index) {
        storage[index] = storage[size - 1];
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
