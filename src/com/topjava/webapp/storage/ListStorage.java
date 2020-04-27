package com.topjava.webapp.storage;

import com.topjava.webapp.model.Resume;
import java.util.ArrayList;
import java.util.List;


public class ListStorage extends AbstractStorage {
    private static List<Resume> listStorage = new ArrayList<>();

    @Override
    public void clear() {
        ListStorage.listStorage.clear();
    }

    @Override
    protected void updateToStorage(Resume resume, int index) {
        listStorage.set(index, resume);
    }

    @Override
    protected void saveToStorage(Resume resume) {
        listStorage.add(resume);
    }

    @Override
    protected Resume getResume(int index) {
        return ListStorage.listStorage.get(index);
    }

    @Override
    protected void deleteFromStorage(int index) {
        ListStorage.listStorage.remove(index);
    }

    @Override
    public Resume[] getAll() {
        return listStorage.toArray(new Resume[listStorage.size()]);
    }


    protected int searchIndex(String uuid) {
        for (int i = 0; i < listStorage.size(); i++) {
            if (listStorage.get(i).getUuid().equals(uuid)) return i;
        }
        return -1;
    }
}
