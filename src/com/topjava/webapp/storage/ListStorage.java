package com.topjava.webapp.storage;

import com.topjava.webapp.model.Resume;


public class ListStorage extends AbstractStorage {

    @Override
    protected void updateToCollection(Resume resume, int index) {
        collection.set(index, resume);
    }

    @Override
    protected boolean isSaveToCollection(Resume resume) {
        if (!collection.contains(resume)) {
            collection.add(resume);
            return true;
        }
        return false;
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
