package com.topjava.webapp.storage;

import com.topjava.webapp.exception.ExistStorageException;
import com.topjava.webapp.exception.NotExistStorageException;
import com.topjava.webapp.model.Resume;

import java.util.Arrays;


public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMITED = 10000;
    protected Resume[] storage = new Resume[STORAGE_LIMITED];
    protected int size = 0;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void update(Resume resume) {
        int index = searchIndex(resume.getUuid());
        if (index >= 0) {
            storage[index] = resume;
        } else {
            throw new NotExistStorageException("Resume "+ resume.getUuid()+ " not exist.", resume.getUuid());
        }
    }

    public void save(Resume resume) {
        int index = searchIndex(resume.getUuid());
        if (size == storage.length) {
            System.out.println("База переполненна. Сначала удалите резюме.");
        } else if (index < 0) {
            saveToStorage(resume);
            size++;
        } else {
            throw new ExistStorageException("Resume "+ resume.getUuid()+ " already exist.", resume.getUuid());        }
    }

    protected abstract void saveToStorage(Resume resume);

    public Resume get(String uuid) {
        int index = searchIndex(uuid);
        if (index >= 0) {
            return storage[index];
        }
        throw new NotExistStorageException("Resume "+ uuid+ " not exist.", uuid);
    }

    public void delete(String uuid) {
        int index = searchIndex(uuid);
        if (index >= 0) {
            deleteItem(index);
            storage[size - 1] = null;
            size--;
        } else {
            throw new NotExistStorageException("Resume "+ uuid+ " not exist.", uuid);
        }
    }

    protected abstract void deleteItem(int index);

    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    protected abstract int searchIndex(String uuid);

    public int size() {
        return size;
    }
}
