package com.topjava.webapp.storage;

import com.topjava.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class SortedArrayStorage extends AbstractArrayStorage {


    @Override
    public void save(Resume resume) {
        int i = searchIndex(resume.getUuid());
        if (size == storage.length) {
            System.out.println("База переполненна. Сначала удалите резюме.");
        } else if (i < 0) {
            int index = -(i + 1);
            System.arraycopy(storage, index, storage, (index + 1), (size - index));
            storage[index] = resume;
            size++;
        } else {
            System.out.println("Резюме" + resume + " уже присутствует в базе." + " Возможно вы хотели обновить его? Тогда воспользуйтесь соответствующей командой.");
        }
    }

    @Override
    public void delete(String uuid) {
        int index = searchIndex(uuid);
        if (index >= 0) {
            System.arraycopy(storage, index + 1, storage, (index), (size - index - 1));
            storage[size - 1] = null;
            size--;
        } else {
            System.out.printf("Резюме %s не найден в базе%n", uuid);
        }
    }

    @Override
    protected int searchIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }
}
