package com.basejava.webapp.storage;


import com.basejava.webapp.storage.strategyIO.ObjectStreamStorage;

public class PathStorageTest extends AbstractStorageTest {

    public PathStorageTest() {
        super(new PathStorage(STORAGE_DIR.toString(),new ObjectStreamStorage()));
    }
}