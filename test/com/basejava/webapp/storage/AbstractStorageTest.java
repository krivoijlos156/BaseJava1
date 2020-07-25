package com.basejava.webapp.storage;

import com.basejava.webapp.Config;
import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.model.ContactType;
import com.basejava.webapp.model.Resume;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static com.basejava.webapp.ResumeTestData.fill;
import static org.junit.Assert.assertEquals;

public abstract class AbstractStorageTest {

    protected final Storage storage;

    protected static final File STORAGE_DIR = Config.get().getStorageDir();

    protected AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }


    private static final String UUID_1 = UUID.randomUUID().toString();
    private static final String UUID_2 = UUID.randomUUID().toString();
    private static final String UUID_3 = UUID.randomUUID().toString();
    private static final String UUID_4 = UUID.randomUUID().toString();

    private static final Resume R1 = new Resume(UUID_1, "Name1");
    private static final Resume R2 = new Resume(UUID_2, "Name2");
    private static final Resume R3 = new Resume(UUID_3, "Name3");
    private static final Resume R4 = new Resume(UUID_4, "Name4");

    @Before
    public void setUp() {
        storage.clear();
        storage.save(fill(R1));
        storage.save(fill(R2));
        storage.save(fill(R3));
    }

    @Test
    public void clear() {
        storage.clear();
        assertStorageSize(0);
    }

    @Test
    public void update() {
        Resume expected = fill(new Resume(UUID_2, "Name9"));
        storage.update(expected);
        assertEquals(expected, storage.get(UUID_2));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        Resume resume = new Resume("uuid5", "Gosha");
        storage.update(resume);
    }

    @Test
    public void save() {
        Resume expected = fill(new Resume(UUID_4, "Name9"));
        storage.save(expected);
        assertEquals(expected, storage.get(UUID_4));
        assertStorageSize(4);
    }

    @Test(expected = ExistStorageException.class)
    public void saveAlreadyExist() {
        Resume resume = fill(new Resume(UUID_2, "Name9"));
        storage.save(resume);
    }

    @Test
    public void get() {
        Resume expected = fill(new Resume(UUID_1, "Name1"));
        Resume actual = storage.get(UUID_1);
        assertEquals(expected, actual);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get("Pop");
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        storage.delete(UUID_1);
        assertStorageSize(2);
        storage.get(UUID_1);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storage.delete(UUID_4);
    }

    @Test
    public void getAllSorted() {
        List<Resume> expectedList = Arrays.asList(
                fill(new Resume(UUID_4, "Name0")),
                fill(new Resume(UUID_1, "Name1")),
                fill(new Resume(UUID_2, "Name2")),
                fill(new Resume(UUID_3, "Name3")));
        storage.save(fill(new Resume(UUID_4, "Name0")));
        assertStorageSize(4);
        List<Resume> actualList = storage.getAllSorted();
        assertEquals(expectedList, actualList);
    }

    @Test
    public void size() {
        assertStorageSize(3);
    }

    private void assertStorageSize(int i) {
        assertEquals(i, storage.size());
    }
}