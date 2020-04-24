package com.topjava.webapp.storage;

import com.topjava.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;


public class ListStorage extends AbstractStorage {
    protected static List<Resume> collection = new ArrayList<>();

    @Override
    public void clear() {
        ListStorage.collection.clear();
    }

    @Override
    protected void updateToStorage(Resume resume, int index) {
        collection.set(index, resume);
    }

    @Override
    protected  void saveToCollection(Resume resume) {
            collection.add(resume);
    }

    @Override
    protected Resume getResumeTo(int index) {
        return ListStorage.collection.get(index);
    }

    @Override
    protected void deleteFromStorage(int index) {
        ListStorage.collection.remove(index);
    }

    @Override
    public Resume[] getAll() {
        return collection.toArray(new Resume[collection.size()]);
    }


    protected int searchIndex(String uuid) {
        for (int i = 0; i < collection.size(); i++) {
            if (collection.get(i).getUuid().equals(uuid)) return i;
        }
        return -1;
    }
}
