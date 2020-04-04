package com.topjava.webapp.storage;

import com.topjava.webapp.exception.NotFoundResumeException;
import com.topjava.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private int size = 0;
    private String not = "Резюме не найдено в базе ";

    public void clear() {
        Arrays.fill(storage,0,size, null);
        size = 0;
    }

    public void update(Resume r) {
        if (searchIndex(r.getUuid()) != -1) {
            storage[searchIndex(r.getUuid())] = r;
        } else {
            System.out.println(not + r);
        }
    }

    public void save(Resume r) {
        if ((searchIndex(r.getUuid())) == -1) {
            storage[size] = r;
            size++;
        } else if (size == 10000) {
            System.out.println("База переполненна. Сначала удалите резюме.");
        } else {
            System.out.println(not +r+ " Возможно вы хотели обновить его? Тогда воспользуйтесь соответствующей командой.");
        }

    }

    public Resume get(String uuid) {
        if (searchIndex(uuid) != -1) {
            return storage[searchIndex(uuid)];
        } else {
            System.out.println(not+uuid);
            return null;
        }
    }

    public void delete(String uuid) {
        if (searchIndex(uuid) != -1) {
            int index = searchIndex(uuid);
            storage[index] = storage[size - 1];
            storage[size - 1] = null;
            size--;
        } else {
            System.out.println(not+uuid);
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
//        try {
//             throw new NotFoundResumeException("ERROR: not found resume");
//        }catch (NotFoundResumeException e){
//            System.out.println("Резюме не найдено в базе");
//        }
        return -1;
    }
}
