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

        //first method read directory
        printDirectoryDeeply(new File("./src"));

        //second method read directory
        new ReadThread(new File("./src")).start();
    }

    public static void printDirectoryDeeply(File directory) {
        File[] list = directory.listFiles();
        if (list != null) {
            for (File name : list) {
                if (name.isFile()) {
                    System.out.println(searchSlashCount(name) + "File:" + name.getName());
                } else if (name.isDirectory()) {
                    System.out.println(searchSlashCount(name) + "Directory:" + name.getName());
                    printDirectoryDeeply(name);
                }
            }
        }
    }

    private static String searchSlashCount(File name) {
        String result = "";
        try {
            String s = name.getCanonicalPath();
            for (char letter : s.toCharArray()) {
                if (letter == '\\') {
                    result = result + "  ";
                }
            }
        } catch (IOException e) {
            System.out.println(e);
        }
        return result;
    }



    public static class ReadThread extends Thread {
        File dirM;
        String f;

        public ReadThread(File nameFile) {
            this.dirM = nameFile;
        }

        public void run() {
            String[] listF = dirM.list();
            try {
                f = dirM.getCanonicalPath();
                if (listF != null) {
                    for (String name : listF) {
                        String f1 = f + "/" + name;
                        File dir1 = new File(f1);
                        if (dir1.isDirectory()) {
                            new ReadThread(dir1).start();
                        } else {
                            System.out.println(name);
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
