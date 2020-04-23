package com.topjava.webapp.storage;

import com.topjava.webapp.exception.ExistStorageException;
import com.topjava.webapp.exception.NotExistStorageException;
import com.topjava.webapp.exception.StorageException;
import com.topjava.webapp.model.Resume;

import java.util.Arrays;


public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMITED = 10000;
    protected Resume[] storage = new Resume[STORAGE_LIMITED];
    protected int size = 0;

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    public void update(Resume resume) {
        int index = searchIndex(resume.getUuid());
        if (index >= 0) {
            storage[index] = resume;
        } else {
            throw new NotExistStorageException(resume.getUuid());
        }
    }

    @Override
    public void save(Resume resume) {
        int index = searchIndex(resume.getUuid());
        if (size == storage.length) {
            throw new StorageException("Resume " + resume.getUuid() + "limit is exceeded.", resume.getUuid());
        } else if (index < 0) {
            saveToStorage(resume);
            size++;
        } else {
            throw new ExistStorageException(resume.getUuid());
        }
    }

    @Override
    public Resume get(String uuid) {
        int index = searchIndex(uuid);
        if (index >= 0) {
            return storage[index];
        }
        throw new NotExistStorageException(uuid);
    }

    @Override
    public void delete(String uuid) {
        int index = searchIndex(uuid);
        if (index >= 0) {
            deleteItem(index);
            storage[size - 1] = null;
            size--;
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    @Override
    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }


    @Override
    public int size() {
        return size;
    }

    protected abstract void deleteItem(int index);

    protected abstract void saveToStorage(Resume resume);

    protected abstract int searchIndex(String uuid);


    @Override
    protected void updateToCollection(Resume resume, int index) {
        throw new StorageException("It`s storage", "-");
    }

    @Override
    protected boolean isSaveToCollection(Resume resume) {
        throw new StorageException("It`s storage", "-");
    }
}
