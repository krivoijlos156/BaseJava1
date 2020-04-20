package com.topjava.webapp.storage;

import com.topjava.webapp.exception.ExistStorageException;
import com.topjava.webapp.exception.NotExistStorageException;
import com.topjava.webapp.exception.StorageException;
import com.topjava.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


import static org.junit.Assert.*;

public abstract class AbstractArrayStorageTest {

    private Storage storage;

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() throws Exception {
        storage.clear();
        for (int i = 1; i <= 4; i++) {
            storage.save(new Resume("uuid" + i));
        }
    }

    @Test
    public void clear() {
        storage.clear();
        assertEquals(0, storage.size());
    }

    @Test
    public void update() {
        Resume resume = new Resume("uuid2");
        storage.update(resume);
        assertTrue(resume == storage.get("uuid2"));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        Resume resume = new Resume("Gosha");
        storage.update(resume);
    }

    @Test
    public void save() {
        Resume resume = new Resume("uuid5");
        storage.save(resume);
        assertEquals(resume, storage.get("uuid5"));
        assertEquals(5, storage.size());
    }

    @Test(expected = ExistStorageException.class)
    public void saveAlreadyExist() {
        Resume resume = new Resume("uuid2");
        storage.save(resume);
    }

    @Test
    public void get() {
        Resume resume = new Resume("uuid1");
        assertEquals(resume, storage.get("uuid1"));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() throws Exception {
        storage.get("Pop");
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        storage.delete("uuid1");
        assertEquals(3, storage.size());
        storage.get("uuid1");
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storage.delete("uuid5");
    }

    @Test
    public void getAll() {
        Resume[] resume = {new Resume("uuid1"), new Resume("uuid2"), new Resume("uuid3"), new Resume("uuid4")};
        assertArrayEquals(resume, storage.getAll());
    }

    @Test
    public void size() {
        assertEquals(4, storage.size());
    }

    @Test(expected = StorageException.class)
    public void overflow() {
        for (int i = 5; i <= 10_000; i++) {
            storage.save(new Resume("uuid" + i));
        }
        storage.save(new Resume("uuid10_001"));
        Assert.fail("More then limited");
    }
}
