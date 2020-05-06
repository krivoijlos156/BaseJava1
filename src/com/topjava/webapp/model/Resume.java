package com.topjava.webapp.model;

import java.util.Objects;
import java.util.UUID;

public class Resume implements Comparable<Resume> {

    private final String uuid;
    private String fullName;

    public Resume(String fullName) {
        this.uuid=UUID.randomUUID().toString();
        this.fullName=fullName;

    }

    public Resume(String uuid,String fullName) {
        this.uuid = uuid;
        this.fullName=fullName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resume resume = (Resume) o;
        return Objects.equals(uuid, resume.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }

    public String getUuid() {
        return uuid;
    }

    public String getFullName() {
        return fullName;
    }

    @Override
    public String toString() {
        return uuid;
    }

    @Override
    public int compareTo(Resume o) {
        int result=fullName.compareTo(o.fullName);
        if (result==0 || o.fullName.equals("null")){
            return uuid.compareTo(o.uuid);
        }
        return result;

    }
}
