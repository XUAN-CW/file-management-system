package com.xuanchengwei.filemanagementsystem.utils.filemanage;

import com.google.common.io.Files;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 禤成伟
 * @date 2023-02-02 - 0:13
 */
public class MoveDuplicateFile {
    static final String duplicateFileDir = "duplicate_file_dir";

    public static void main(String[] args) throws IOException {
        File duplicateFileTxt = new File("metadata/duplicate_file.txt");
        List<File> duplicateFileList = Files.readLines(duplicateFileTxt, StandardCharsets.UTF_8)
                .stream().map(File::new).filter(File::exists).toList();
        for (File file : duplicateFileList) {
            StringBuffer stringBuffer = new StringBuffer(file.getAbsolutePath());
            stringBuffer.insert(3,duplicateFileDir+File.separator);
            System.out.println(stringBuffer);
        }


    }
}
