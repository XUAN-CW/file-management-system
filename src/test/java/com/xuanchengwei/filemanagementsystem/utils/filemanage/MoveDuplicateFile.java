package com.xuanchengwei.filemanagementsystem.utils.filemanage;

import com.google.common.io.Files;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author 禤成伟
 * @date 2023-02-02 - 0:13
 */
public class MoveDuplicateFile {

    public static void main(String[] args) throws IOException {
        File duplicateFileTxt = new File("metadata/duplicate_file.txt");
        for (String line : Files.readLines(duplicateFileTxt, StandardCharsets.UTF_8)) {
            File duplicateFile = new File(line);
            if(duplicateFile.exists()){
                System.out.println(duplicateFile.getAbsolutePath());
            }
        }

    }
}
