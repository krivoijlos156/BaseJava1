package com.topjava.webapp.storage;

import com.topjava.webapp.exception.StorageException;
import com.topjava.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Test;


public abstract class AbstractArrayStorageTest extends AbstractStorageTest {

    protected AbstractArrayStorageTest(Storage storage) {
        super(storage);
    }


    @Test(expected = StorageException.class)
    public void overflow() {
        for (int i = 5; i <= 10_000; i++) {
            storage.save(new Resume("Name" + i));
        }
        Resume re = new Resume("Overflow");
        storage.save(re);
        Assert.fail("More then limited");
    }
}
