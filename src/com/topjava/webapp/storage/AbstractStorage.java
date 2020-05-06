package com.topjava.webapp.storage;

import com.topjava.webapp.exception.ExistStorageException;
import com.topjava.webapp.exception.NotExistStorageException;
import com.topjava.webapp.model.Resume;

import java.util.Collections;
import java.util.List;


public abstract class AbstractStorage implements Storage {

    protected abstract Object getSearchKey(String uuid);

    protected abstract void doUpdate(Resume resume, Object searchKey);

    protected abstract void doSave(Resume resume, Object searchKey);

    protected abstract Resume doGet(Object searchKey);

    protected abstract void doDelete(Object searchKey);

    protected abstract boolean isExist(Object searchKey);

    protected abstract List<Resume> getList();

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> list = getList();
        Collections.sort(list);
        return list;
    }

    @Override
    public void update(Resume resume) {
        Object searchKey = getExistedKey(resume.getUuid());
        doUpdate(resume, searchKey);
    }

    @Override
    public void save(Resume resume) {
        Object searchKey = getNotExistedKey(resume.getUuid());
        doSave(resume, searchKey);
    }

    @Override
    public Resume get(String uuid) {
        Object searchKey = getExistedKey(uuid);
        return doGet(searchKey);
    }

    @Override
    public void delete(String uuid) {
        Object searchKey = getExistedKey(uuid);
        doDelete(searchKey);
    }

    private Object getExistedKey(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (!isExist(searchKey)) {
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

    private Object getNotExistedKey(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (isExist(searchKey)) {
            throw new ExistStorageException(uuid);
        }
        return searchKey;
    }
}
