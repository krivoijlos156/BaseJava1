package com.basejava.webapp.storage;

import com.basejava.webapp.storage.IOStrategy.XmlStreamSerializer;

public class JsonPathStorageTest extends AbstractStorageTest {
    public JsonPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.toString(), new XmlStreamSerializer()));
    }
}