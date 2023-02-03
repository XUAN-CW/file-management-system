package com.xuanchengwei.filemanagementsystem.hashutil;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        FileMetadataUtils.getFileList(new File("/hash/path")).stream().parallel().forEach(target -> {
            System.out.println(target.getAbsolutePath());
            try {
                FileMetadataUtils.safetyHashing(target);
            } catch (Exception e) {
                System.err.println(target.getAbsolutePath());
                e.printStackTrace();
            }
        });
    }
}
