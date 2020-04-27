package com.topjava.webapp.storage;


import com.topjava.webapp.exception.StorageException;
import com.topjava.webapp.model.Resume;
import java.util.Arrays;


public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMITED = 10000;
    protected Resume[] storage = new Resume[STORAGE_LIMITED];
    protected int size = 0;

    public int size() {
        return size;
    }

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }


    @Override
    protected void updateToStorage(Resume resume, int index) {
        storage[index] = resume;
    }

    @Override
    protected Resume getResume(int index) {
        return storage[index];
    }

    @Override
    protected void deleteFromStorage(int index) {
        deleteItem(index);
        storage[size - 1] = null;
        size--;
    }

    @Override
    protected void saveToStorage(Resume resume) {
        if (size == storage.length) {
            throw new StorageException("Resume " + resume.getUuid() + "limit is exceeded.", resume.getUuid());
        }
        saveAs(resume);
        size++;
    }

    protected abstract void deleteItem(int index);

    protected abstract void saveAs(Resume resume);

    protected abstract int searchIndex(String uuid);
}
