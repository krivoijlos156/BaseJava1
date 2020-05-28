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
        String f = "./src/com/topjava/webapp";
        File dir = new File(f);
        String[] list = dir.list();
        if (list != null) {
            for (String name : list) {
                String f1 = f + "/" + name;
                File dir1 = new File(f1);
                String[] list1 = dir1.list();
                if (dir1.isDirectory() && list1 != null) {
                    for (String name1 : list1) {
                        System.out.println(name1);
                    }
                } else {
                    System.out.println(name);
                }
            }
        }
//second method read all directory
        new ReadThread(new File(".")).start();
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
