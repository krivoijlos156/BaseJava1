package com.topjava.webapp.exception;

public class NotExistStorageException extends StorageException{

    public NotExistStorageException(String massage, String uuid) {
        super("Resume "+ uuid+ " not exist.", uuid);
    }
}