package com.urise.webapp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MainFile {
    public static void main(String[] args) throws IOException {
        String path = ".\\.gitignore";
        File file = new File(path);
        System.out.println(file.getCanonicalPath());
        File dir = new File("./src/com/urise/webapp");
        System.out.println(dir.isDirectory());
        File[] list = dir.listFiles();
        if (list != null) {
            for (File name : list) {
                System.out.println(name.getName());
            }
        }
        try (FileInputStream fis = new FileInputStream(path)) {
            System.out.println(fis.read());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        System.out.println("***Recursive call start***");
        readDirectory(dir);
        System.out.println("***Recursive call end***");
    }

    private static void readDirectory(File directory) {
        if (directory.isDirectory()) {
            File[] list = directory.listFiles();
            if (list != null) {
                System.out.println("List of Directory: " + directory.getName());
                for (File file : list) {
                    if (file.isFile()) {
                        System.out.println(file.getName());
                    } else {
                        System.out.println("Directory: " + file.getName());
                        readDirectory(file);
                    }
                }
            }
        }
    }
}
