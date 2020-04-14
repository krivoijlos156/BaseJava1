package com.topjava.webapp.exception;

public class ExistStorageException extends StorageException {

    public ExistStorageException(String massage, String uuid) {
        super("Resume "+ uuid+ " already exist.", uuid);
    }
}
