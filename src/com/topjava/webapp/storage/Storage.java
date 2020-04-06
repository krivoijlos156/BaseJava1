package com.topjava.webapp.storage;

import com.topjava.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public interface Storage {

     void clear();

     void update(Resume resume);

     void save(Resume resume);

     Resume get(String uuid);

     void delete(String uuid);

    /**
     * @return array, contains only Resumes in storage (without null)
     */
     Resume[] getAll();

     int size();
}
