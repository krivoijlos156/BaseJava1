package com.topjava.webapp.storage;

import com.topjava.webapp.exception.StorageException;
import com.topjava.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Test;


public abstract class AbstractArrayStorageTest extends AbstractStorageTest {
    private Storage storage;

    protected AbstractArrayStorageTest(Storage storage) {
        super(storage);
        this.storage = storage;
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
