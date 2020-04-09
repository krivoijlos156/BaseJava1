package com.topjava.webapp.storage;

import com.topjava.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected void saveIf(Resume resume) {
        int index = -(searchIndex(resume.getUuid()) + 1);
        System.arraycopy(storage, index, storage, (index + 1), (size - index));
        storage[index] = resume;
    }

    @Override
    protected void deleteIf(int index) {
        System.arraycopy(storage, index + 1, storage, (index), (size - index - 1));
    }

    @Override
    protected int searchIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }
}
