package com.basejava.webapp.storage;


import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;

import java.util.Arrays;
import java.util.List;


public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {
    protected static final int STORAGE_LIMITED = 10000;
    protected Resume[] storage = new Resume[STORAGE_LIMITED];
    protected int size = 0;

    public int size() {
        return size;
    }

    @Override
    protected boolean isExist(Integer index) {
        return index >= 0;
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
    protected void doUpdate(Resume resume, Integer index) {
        storage[index] = resume;
    }

    @Override
    protected Resume doGet(Integer index) {
        return storage[index];
    }

    @Override
    protected void doDelete(Integer index) {
        deleteItem(index);
        storage[size - 1] = null;
        size--;
    }

    @Override
    protected void doSave(Resume resume, Integer index) {
        if (size == storage.length) {
            throw new StorageException("Resume " + resume.getUuid() + "limit is exceeded.", resume.getUuid());
        }
        saveItem(resume, index);
        size++;
    }

    protected abstract void deleteItem(int index);

    protected abstract void saveItem(Resume resume, int index);

    protected abstract Integer getSearchKey(String uuid);
}