package com.xuanchengwei.filemanagementsystem.utils.filemanage;

import com.xuanchengwei.filemanagementsystem.utils.FileMetadataUtils;
import java.io.File;
import java.io.IOException;

public class DeleteEmptyDirectories {

    public static void deleteEmptyDirectories(File directory) throws IOException {
        if (directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    deleteEmptyDirectories(file);
                }
            }
            System.out.println(directory.list().length + "  " +directory.getAbsolutePath());
            if (FileMetadataUtils.getFileList(directory).size() == 0) {
                directory.delete();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        File rootDirectory = new File("Y:\\qbit\\qbit_download");
        deleteEmptyDirectories(rootDirectory);
    }
}
