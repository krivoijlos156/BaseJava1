package com.topjava.webapp.storage;

import com.topjava.webapp.exception.ExistStorageException;
import com.topjava.webapp.exception.NotExistStorageException;
import com.topjava.webapp.model.Resume;


public abstract class AbstractStorage implements Storage {


    @Override
    public void update(Resume resume) {
        int index = searchIndex(resume.getUuid());
        if (index >= 0) {
            updateToStorage(resume, index);
        } else {
            throw new NotExistStorageException(resume.getUuid());
        }
    }

    @Override
    public void save(Resume resume) {
        int index = searchIndex(resume.getUuid());
        if (index < 0) {
            saveToStorage(resume);
        } else {
            throw new ExistStorageException(resume.getUuid());
        }
    }

    @Override
    public Resume get(String uuid) {
        int index = searchIndex(uuid);
        if (index >= 0) {
            return getResume(index);
        }
        throw new NotExistStorageException(uuid);
    }

    @Override
    public void delete(String uuid) {
        int index = searchIndex(uuid);
        if (index >= 0) {
            deleteFromStorage(index);
        } else {
            throw new NotExistStorageException(uuid);
        }
    }


    protected abstract int searchIndex(String uuid);

    protected abstract void updateToStorage(Resume resume, int index);

    protected abstract void saveToStorage(Resume resume);

    protected abstract Resume getResume(int index);

    protected abstract void deleteFromStorage(int index);
}
