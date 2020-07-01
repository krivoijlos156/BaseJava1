package com.basejava.webapp.storage;

import com.basejava.webapp.ResumeTestData;
import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.model.Resume;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public abstract class AbstractStorageTest {
    protected final Storage storage;
    protected static final File STORAGE_DIR = new File("C:\\Users\\krivo\\IdeaProjects\\BaseJava1\\Storage");

    protected AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() throws Exception {
        storage.clear();
        for (int i = 1; i <= 4; i++) {
            storage.save(ResumeTestData.fillResume("uuid" + i, "Name" + i));
        }
    }

    @Test
    public void clear() {
        storage.clear();
        assertEquals(0, storage.size());
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
        assertEquals(5, storage.size());
    }

    @Test(expected = ExistStorageException.class)
    public void saveAlreadyExist() {
        Resume resume = new Resume("uuid2", "Name9");
        storage.save(resume);
    }

    @Test
    public void get() {
        Resume expected = ResumeTestData.fillResume("uuid1", "Name1");
        Resume actual = storage.get("uuid1");
        assertEquals(expected, actual);
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
    public void getAllSorted() {
        Resume resume = ResumeTestData.fillResume("uuid0", "Name0");
        storage.save(resume);
        List<Resume> listNew = new ArrayList<>();
        for (int i = 0; i <= 4; i++) {
            listNew.add(ResumeTestData.fillResume("uuid" + i, "Name" + i));
        }
        List<Resume> list = storage.getAllSorted();
        assertEquals(listNew, list);
    }

    @Test
    public void size() {
        assertEquals(4, storage.size());
    }
}