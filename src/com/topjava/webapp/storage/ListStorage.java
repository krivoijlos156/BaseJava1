package com.topjava.webapp.storage;

import com.topjava.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;


public class ListStorage extends AbstractStorage {
    private List<Resume> listStorage = new ArrayList<>();

    @Override
    public void clear() {
        listStorage.clear();
    }

    @Override
    protected void doUpdate(Resume resume, Object index) {
        listStorage.set((int) index, resume);
    }

    @Override
    protected void doSave(Resume resume, Object index) {
        listStorage.add(resume);
    }

    @Override
    protected Resume getResume(Object index) {
        return listStorage.get((int)index);
    }

    @Override
    protected void doDelete(Object index) {
        listStorage.remove((int)index);
    }

    @Override
    protected boolean isExist(Object index) {
        return (int) index >= 0;
    }

    @Override
    public Resume[] getAll() {
        return listStorage.toArray(new Resume[listStorage.size()]);
    }


    @Override
    public List<Resume> getAllSorted() {
        return listStorage;
    }



    protected Object getSearchKey(String uuid) {
        for (int i = 0; i < listStorage.size(); i++) {
            if (listStorage.get(i).getUuid().equals(uuid)) return i;
        }
        return -1;
    }

    @Override
    public int size() {
        return listStorage.size();
    }
}
