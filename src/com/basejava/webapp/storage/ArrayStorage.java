package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;


public class ArrayStorage extends AbstractArrayStorage {


    @Override
    protected void saveItem(Resume resume, int index) {
        storage[size] = resume;
    }

    @Override
    protected void deleteItem(int index) {
        storage[index] = storage[size - 1];
    }

    @Override
    protected Integer getSearchKey(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}