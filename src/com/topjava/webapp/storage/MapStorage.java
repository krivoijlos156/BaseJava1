package com.topjava.webapp.storage;

import com.topjava.webapp.model.Resume;

import java.util.*;

public class MapStorage extends AbstractStorage<Resume> {
    private final Map<String, Resume> mapStorage = new HashMap<>();

    @Override
    protected Resume getSearchKey(String uuid) {
        return mapStorage.get(uuid);
    }

    @Override
    protected boolean isExist(Resume searchKey) {
        return searchKey != null;
    }

    @Override
    protected void doUpdate(Resume resume, Resume searchKey) {
        mapStorage.put((searchKey).getUuid(), resume);
    }

    @Override
    protected void doSave(Resume resume, Resume searchKey) {
        mapStorage.put(resume.getUuid(), resume);
    }

    @Override
    protected Resume doGet(Resume searchKey) {
        return searchKey;
    }

    @Override
    protected void doDelete(Resume searchKey) {
        mapStorage.remove((searchKey).getUuid());
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