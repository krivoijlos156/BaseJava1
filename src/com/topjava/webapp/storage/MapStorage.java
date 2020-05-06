package com.topjava.webapp.storage;

import com.topjava.webapp.model.Resume;

import java.util.*;

public class MapStorage extends AbstractStorage {
    private final Map<String, Resume> mapStorage = new HashMap<>();

    @Override
    protected Object getSearchKey(String uuid) {
        for (Map.Entry<String, Resume> pair : mapStorage.entrySet()) {
            if (pair.getKey().equals(uuid)) {
                return pair.getValue();
            }
        }
        return uuid;
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return searchKey.getClass().equals(Resume.class);
    }

    @Override
    protected void doUpdate(Resume resume, Object searchKey) {
        mapStorage.put(((Resume) searchKey).getUuid(), resume);
    }

    @Override
    protected void doSave(Resume resume, Object searchKey) {
        mapStorage.put((String) searchKey, resume);
    }

    @Override
    protected Resume doGet(Object searchKey) {
        return (Resume) searchKey;
    }

    @Override
    protected void doDelete(Object searchKey) {
        mapStorage.remove(((Resume) searchKey).getUuid());
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
