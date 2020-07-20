package com.basejava.webapp.storage;

import com.basejava.webapp.Config;

public class SQLStorageTest extends AbstractStorageTest {
    public SQLStorageTest() {
        super(new SQLStorage(Config.get().getDbUrl(), Config.get().getDbUser(), Config.get().getDbPassword()));
    }
}