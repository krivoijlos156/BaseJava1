package com.topjava.webapp.storage;

import com.topjava.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {


    public void save(Resume resume) {
        if (size == storage.length) {
            System.out.println("База переполненна. Сначала удалите резюме.");
        } else if ((searchIndex(resume.getUuid())) == -1) {
            storage[size] = resume;
            size++;
        } else {
            System.out.println("Резюме" + resume + " уже присутствует в базе." + " Возможно вы хотели обновить его? Тогда воспользуйтесь соответствующей командой.");
        }
    }

    public void delete(String uuid) {
        int index = searchIndex(uuid);
        if (index != -1) {
            storage[index] = storage[size - 1];
            storage[size - 1] = null;
            size--;
        } else {
            System.out.printf("Резюме %s не найден в базе%n", uuid);
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */

    protected int searchIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
