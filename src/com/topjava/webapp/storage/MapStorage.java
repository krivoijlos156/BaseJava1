package com.topjava.webapp.storage;

import com.topjava.webapp.model.Resume;

import java.util.*;

public class MapStorage extends AbstractStorage {
    private final Map<Integer, Resume> mapStorage = new HashMap<>();
    private static int count = 0;

    @Override
    protected Object getSearchKey(String uuid) {
        for (Map.Entry<Integer, Resume> pair : mapStorage.entrySet()) {
            if (pair.getValue().getUuid().equals(uuid)) {
                return pair.getKey();
            }
        }
        return -1;
    }

    @Override
    protected boolean isExist(Object index) {
        return (int) index >= 0;
    }

    @Override
    protected void doUpdate(Resume resume, Object index) {
        mapStorage.put((int) index, resume);
    }

    @Override
    protected void doSave(Resume resume, Object index) {
        mapStorage.put(count, resume);
        count++;
    }

    @Override
    protected Resume doGet(Object index) {
        return mapStorage.get((int) index);
    }

    @Override
    protected void doDelete(Object index) {
        mapStorage.remove((int) index);
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
