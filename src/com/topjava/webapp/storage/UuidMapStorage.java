package com.topjava.webapp.storage;

import com.topjava.webapp.model.Resume;

import java.util.*;

public class UuidMapStorage extends AbstractStorage {

    private final Map<String, Resume> mapStorage = new HashMap<>();

    @Override
    protected Object getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    protected void doUpdate(Resume resume, Object uuid) {
        mapStorage.put((String) uuid, resume);
    }

    @Override
    protected void doSave(Resume resume, Object uuid) {
        mapStorage.put((String) uuid, resume);
    }

    @Override
    protected Resume doGet(Object uuid) {
        return mapStorage.get((String) uuid);
    }

    @Override
    protected void doDelete(Object uuid) {
        mapStorage.remove(uuid);
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return mapStorage.containsKey(searchKey);
    }

    @Override
    public void clear() {
        mapStorage.clear();
    }

    @Override
    protected List<Resume> getList() {
        return new ArrayList<>(mapStorage.values());
    }

    @Override
    public int size() {
        return mapStorage.size();
    }
}
