package com.basejava.webapp.storage;

import com.basejava.webapp.Config;

public class SQLStorageTest extends AbstractStorageTest {
    public SQLStorageTest() {
        super(Config.get().getSqlStorage());
    }
}