package com.xuanchengwei.filemanagementsystem.utils.filemanage;

import java.io.File;

public class DeleteEmptyDirectories {

    public static void deleteEmptyDirectories(File directory) {
        if (directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    deleteEmptyDirectories(file);
                }
            }
            if (directory.list().length == 0) {
                System.out.println(directory.getAbsolutePath());
                directory.delete();
            }
        }
    }

    public static void main(String[] args) {
        File rootDirectory = new File("D:\\qbit_download_complete\\qbit_download_check_5");
        deleteEmptyDirectories(rootDirectory);
    }
}
