package com.xuanchengwei.filemanagementsystem.utils.filemanage;

import com.google.common.io.Files;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author 禤成伟
 * @date 2023-02-02 - 0:13
 */
public class MoveFile {
    static final String duplicateFileDir = "duplicate_file";

    @Test
    public void moveDuplicateFile() throws IOException {
        File duplicateFileTxt = new File("metadata/duplicate_file.txt");
        List<File> duplicateFileList = Files.readLines(duplicateFileTxt, StandardCharsets.UTF_8)
                .stream().map(File::new).filter(File::exists).toList();
        for (File file : duplicateFileList) {
            File duplicateFileMoveTo = new File(new StringBuffer(file.getAbsolutePath())
                    .insert(3,duplicateFileDir+File.separator).toString());
            System.out.println(duplicateFileMoveTo);
            FileUtils.moveFile(file,duplicateFileMoveTo);
        }
    }
}
