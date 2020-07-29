package com.basejava.webapp.storage;

import com.basejava.webapp.Config;
import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.model.Resume;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static com.basejava.webapp.ResumeTestData.*;
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

    private static final Resume R1 = fillB(new Resume(UUID_1, "Name1"));
    private static final Resume R2 = fill(new Resume(UUID_2, "Name2"));
    private static final Resume R3 = new Resume(UUID_3, "Name3");
    private static final Resume R4 = fill(new Resume(UUID_4, "Name0"));
    private static final Resume R2_NEW = fill(new Resume(UUID_2, "NEW"));

    @Before
    public void setUp() {
        storage.clear();
        storage.save(R2);
        storage.save(R1);
        storage.save(R3);
    }

    @Test
    public void clear() {
        storage.clear();
        assertStorageSize(0);
    }

    @Test
    public void update() {
        storage.update(R2_NEW);
        assertEquals(R2_NEW, storage.get(UUID_2));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        storage.update(R4);
    }

    @Test
    public void save() {
        storage.save(R4);
        assertGet(R4);
        assertStorageSize(4);
    }

    @Test(expected = ExistStorageException.class)
    public void saveAlreadyExist() {
        storage.save(R2_NEW);
    }

    @Test
    public void get() {
        assertGet(R1);
        assertGet(R2);
        assertGet(R3);
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
        List<Resume> expectedList = Arrays.asList(R1, R2, R3);
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

    private void assertGet(Resume r) {
        assertEquals(r, storage.get(r.getUuid()));
    }
}