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
    private String not = "Резюме не найдено в базе";

    public int searchI(String uuid) {
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
        return 11111;
    }

    public void clear() {

        Arrays.fill(storage, null);
        size = 0;
    }

    public void update(Resume r) {
        if (searchI(r.getUuid()) != 11111) {
            storage[searchI(r.getUuid())] = r;
        } else {
            System.out.println(not);
        }
    }

    public void save(Resume r) {
        if ((searchI(r.getUuid())) == 11111) {
            storage[size] = r;
            size++;
        } else if (size == 10000) {
            System.out.println("База переполненна. Сначала удалите резюме.");
        } else {
            System.out.println(not + " Возможно вы хотели обновить его? Тогда воспользуйтесь соответствующей комендой.");
        }

    }

    public Resume get(String uuid) {
        if (searchI(uuid) != 11111) {
            return storage[searchI(uuid)];
        } else {
            System.out.println(not);
            return null;
        }
    }

    public void delete(String uuid) {
        if (searchI(uuid) != 11111) {
            int i = searchI(uuid);
            storage[i] = storage[size - 1];
            storage[size - 1] = null;
            size--;
        } else {
            System.out.println(not);
        }

    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        Resume[] resumes = Arrays.copyOfRange(storage, 0, size);
        return resumes;
    }

    public int size() {
        return size;
    }
}
