package com.topjava.webapp.storage;

import com.topjava.webapp.exception.NotFoundResumeException;
import com.topjava.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10_000];
    private int size = 0;
    private String notFound = "Резюме не найдено в базе ";

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void update(Resume resume) {
        int index = searchIndex(resume.getUuid());
        if (index != -1) {
            storage[index] = resume;
        } else {
            System.out.println(notFound + resume);
        }
    }

    public void save(Resume resume) {
        if (size == storage.length) {
            System.out.println("База переполненна. Сначала удалите резюме.");
        } else if ((searchIndex(resume.getUuid())) == -1) {
            storage[size] = resume;
            size++;
        } else {
            System.out.println("Резюме с uuid: " + resume + " уже присутствует в базе." + " Возможно вы хотели обновить его? Тогда воспользуйтесь соответствующей командой.");
        }
    }

    public Resume get(String uuid) {
        int index = searchIndex(uuid);
        if (index != -1) {
            return storage[index];
        } else {
            System.out.println(notFound + uuid);
            return null;
        }
    }

    public void delete(String uuid) {
        int index = searchIndex(uuid);
        if (index != -1) {
            storage[index] = storage[size - 1];
            storage[size - 1] = null;
            size--;
        } else {
            System.out.println(notFound + uuid);
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    public int size() {
        return size;
    }

    private int searchIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
