package com.basejava.webapp.storage.strategyIO;

import com.basejava.webapp.model.Resume;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface StrategyIO {
    Resume doRead(InputStream is) throws IOException;// throws IOException

    void doWrite(Resume resume, OutputStream os) throws IOException;// throws IOException
}