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
    protected Resume getResume(Object uuid) {
        Resume resume=new Resume();
        return mapStorage.getOrDefault((String) uuid, resume);
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
    public List<Resume> getAllSorted() {
        List<Resume> list = new ArrayList<>(mapStorage.values());
        Collections.sort(list);
        return list;
    }

    @Override
    public int size() {
        return mapStorage.size();
    }
}
