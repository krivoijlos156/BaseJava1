package com.basejava.webapp;

import com.basejava.webapp.storage.SQLStorage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    private static final File PROPS = new File(getRoot() + "\\config\\resumes.properties");

    private static final Config INSTANCE = new Config();

    private final File storageDir;
    private final SQLStorage sqlStorage;

    public static Config get() {
        return INSTANCE;
    }

    public Config() {
        try (InputStream is = new FileInputStream(PROPS)) {
            Properties props = new Properties();
            props.load(is);
            storageDir = new File(props.getProperty("storage.dir"));
            sqlStorage = new SQLStorage(props.getProperty("db.url"), props.getProperty("db.user"), props.getProperty("db.password"));
        } catch (IOException e) {
            throw new IllegalStateException("Invalid config file " + PROPS.getAbsolutePath());
        }
    }

    private static String getRoot() {
        String root = System.getProperty("rootHome");
        return root == null ? "." : root;
    }

    public File getStorageDir() {
        return storageDir;
    }

    public SQLStorage getSqlStorage() {
        return sqlStorage;
    }
}
