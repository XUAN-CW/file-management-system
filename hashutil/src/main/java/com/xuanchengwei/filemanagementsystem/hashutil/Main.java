package com.xuanchengwei.filemanagementsystem.hashutil;

import com.xuanchengwei.filemanagementsystem.utils.FileMetadataUtils;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        FileMetadataUtils.getFileList(new File("R:\\jav")).stream().parallel().forEach(target -> {
            System.out.println(target.getAbsolutePath());
            try {
                FileMetadataUtils.fastHashing(target);
            } catch (Exception e) {
                System.err.println(target.getAbsolutePath());
                e.printStackTrace();
            }
        });
    }
}
