package com.topjava.webapp.storage;


import com.topjava.webapp.exception.StorageException;
import com.topjava.webapp.model.Resume;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMITED = 10000;
    protected Resume[] storage = new Resume[STORAGE_LIMITED];
    protected int size = 0;

    public int size() {
        return size;
    }

    @Override
    protected boolean isExist(Object index) {
        return (int) index >= 0;
    }

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    protected List<Resume> getList() {
        return Arrays.asList(Arrays.copyOfRange(storage, 0, size));
    }

    @Override
    protected void doUpdate(Resume resume, Object index) {
        storage[(int) index] = resume;
    }

    @Override
    protected Resume doGet(Object index) {
        return storage[(int) index];
    }

    @Override
    protected void doDelete(Object index) {
        deleteItem((int) index);
        storage[size - 1] = null;
        size--;
    }

    @Override
    protected void doSave(Resume resume, Object index) {
        if (size == storage.length) {
            throw new StorageException("Resume " + resume.getUuid() + "limit is exceeded.", resume.getUuid());
        }
        saveItem(resume, (int) index);
        size++;
    }

    protected abstract void deleteItem(int index);

    protected abstract void saveItem(Resume resume, int index);

    protected abstract Integer getSearchKey(String uuid);
}
