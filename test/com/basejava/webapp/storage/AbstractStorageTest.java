package com.basejava.webapp.storage;

import com.basejava.webapp.Config;
import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.model.Resume;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.List;

import static com.basejava.webapp.ResumeTestData.fill;
import static org.junit.Assert.assertEquals;

public abstract class AbstractStorageTest {

    protected final Storage storage;

    protected static final File STORAGE_DIR = Config.get().getStorageDir();

    protected AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        for (int i = 1; i <= 4; i++) {
            storage.save(fill(new Resume("uuid" + i, "Name" + i)));
        }
    }

    @Test
    public void clear() {
        storage.clear();
        assertStorageSize(0);
    }

    @Test
    public void update() {
        Resume resume = new Resume("uuid2", "Name9");
        storage.update(resume);
        assertEquals(resume, storage.get("uuid2"));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        Resume resume = new Resume("uuid5", "Gosha");
        storage.update(resume);
    }

    @Test
    public void save() {
        Resume resume = new Resume("uuid5", "Name9");
        storage.save(resume);
        assertEquals(resume, storage.get("uuid5"));
        assertStorageSize(5);
    }

    @Test(expected = ExistStorageException.class)
    public void saveAlreadyExist() {
        Resume resume = new Resume("uuid2", "Name9");
        storage.save(resume);
    }

    @Test
    public void get() {
        Resume expected = fill(new Resume("uuid1", "Name1"));
        Resume actual = storage.get("uuid1");
        assertEquals(expected, actual);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get("Pop");
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        storage.delete("uuid1");
        assertStorageSize(3);
        storage.get("uuid1");
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storage.delete("uuid5");
    }

    @Test
    public void getAllSorted() {
        Resume resume = new Resume("uuid5", "Name0");
        storage.save(resume);
        assertStorageSize(5);
        List<Resume> list = storage.getAllSorted();
        assertEquals(resume.getFullName(), list.get(0).getFullName());
    }

    @Test
    public void size() {
        assertStorageSize(4);
    }

    private void assertStorageSize(int i) {
        assertEquals(i, storage.size());
    }
}