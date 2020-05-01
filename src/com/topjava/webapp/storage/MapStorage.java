package com.topjava.webapp.storage;

import com.topjava.webapp.model.Resume;

import java.util.*;

public class MapStorage extends AbstractStorage {
    private Map<Integer, Resume> mapStorage = new HashMap<>();
    private static int count = 0;

    @Override
    protected int searchIndex(String uuid) {
        for (Map.Entry<Integer, Resume> pair : mapStorage.entrySet()) {
            if (pair.getValue().getUuid().equals(uuid)) {
                return pair.getKey();
            }
        }
        return -1;
    }

    @Override
    protected void updateToStorage(Resume resume, int index) {
        mapStorage.put(index, resume);
    }

    @Override
    protected void saveToStorage(Resume resume) {
        mapStorage.put(count, resume);
        count++;
    }

    @Override
    protected Resume getResume(int index) {
        return mapStorage.get(index);
    }

    @Override
    protected void deleteFromStorage(int index) {
        mapStorage.remove(index);
    }

    @Override
    public void clear() {
        mapStorage.clear();
    }

    @Override
    public Resume[] getAll() {
        Collection<Resume> list = mapStorage.values();
        return list.toArray(new Resume[list.size()]);
    }

    @Override
    public int size() {
        return mapStorage.size();
    }
}
