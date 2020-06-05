package com.topjava.webapp.storage.strategyIO;

import com.topjava.webapp.exception.StorageException;
import com.topjava.webapp.model.Resume;

import java.io.*;

public class ObjectStreamStorage implements StrategyIO {

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (ObjectInputStream ois = new ObjectInputStream(is)) {
            return (Resume) ois.readObject();
        } catch (ClassNotFoundException e) {
            throw new StorageException("Error read exception", null, e);
        }
    }

    @Override
    public void doWrite(Resume resume, OutputStream os) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(os)) {
            oos.writeObject(resume);
        }
    }
}
