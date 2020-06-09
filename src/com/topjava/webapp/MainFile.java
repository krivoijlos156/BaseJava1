package com.topjava.webapp;


import java.io.File;
import java.io.IOException;

public class MainFile {
    public static void main(String[] args) {

        String filePath = ".\\.gitignore";

        File file = new File(filePath);
        try {
            System.out.println(file.getCanonicalPath());
        } catch (IOException e) {
            throw new RuntimeException("Error", e);
        }

        String indent = "";
        printDirectoryDeeply(new File("./src"), indent);
    }

    public static void printDirectoryDeeply(File directory, String indent) {
        File[] list = directory.listFiles();
        if (list != null) {
            for (File name : list) {
                if (name.isFile()) {
                    System.out.println(indent + "File:" + name.getName());
                } else if (name.isDirectory()) {
                    System.out.println(indent + "Directory:" + name.getName());
                    printDirectoryDeeply(name, indent + " ");
                }
            }
        }
    }
}