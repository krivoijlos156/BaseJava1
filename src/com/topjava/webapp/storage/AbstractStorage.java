package com.topjava.webapp.storage;

import com.topjava.webapp.exception.ExistStorageException;
import com.topjava.webapp.exception.NotExistStorageException;
import com.topjava.webapp.exception.StorageException;
import com.topjava.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;


public abstract class AbstractStorage implements Storage {
    protected List<Resume> collection = new ArrayList<>();

    @Override
    public void clear() {
        collection.clear();
    }

    @Override
    public void update(Resume resume) {
        int index = searchIndex(resume.getUuid());
        if (index != -1) {
            updateToCollection(resume, index);
        } else {
            throw new NotExistStorageException(resume.getUuid());
        }
    }

    @Override
    public void save(Resume resume) {
        if (!isSaveToCollection(resume)) {
            throw new ExistStorageException(resume.getUuid());
        }
    }

    @Override
    public Resume get(String uuid) {
        int index = searchIndex(uuid);
        if (index != -1) {
            return collection.get(index);
        }
        throw new NotExistStorageException(uuid);
    }

    @Override
    public void delete(String uuid) {
        int index = searchIndex(uuid);
        if (index != -1) {
            collection.remove(index);
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    @Override
    public int size() {
        throw new StorageException("Collection unlimited", "-");
    }

    protected abstract int searchIndex(String uuid);

    protected abstract void updateToCollection(Resume resume, int index);

    protected abstract boolean isSaveToCollection(Resume resume);
}
