package com.xuanchengwei.filemanagementsystem.utils;

import com.xuanchengwei.filemanagementsystem.entity.FileMetadata;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author 禤成伟
 * @date 2022-11-19 - 23:57
 */
public class FileMetadataUtils {

    public static List<File> getFileList(File targetFile) throws IOException {
        List<File> targetFileList = new ArrayList<>();
        if(targetFile.isFile()){
            targetFileList.add(targetFile);
        }else {
            Files.walkFileTree(targetFile.toPath(), new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                    if(!file.toFile().getAbsolutePath().endsWith(FileMetadata.SUFFIX)){
                        targetFileList.add(file.toFile());
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
        }
        return targetFileList;
    }

    public static List<FileMetadata> getFileMetadataList(File targetDir) throws IOException {

        List<FileMetadata> fileMetadataList = Collections.synchronizedList(new ArrayList<>(100));
        getFileList(targetDir).forEach(target -> {
            try {
                FileMetadata fileMetadata = new FileMetadata(target).fastHashing();
                fileMetadataList.add(fileMetadata);
            } catch (IOException e) {
                System.out.println(target.getAbsolutePath());
                e.printStackTrace();
            }
        });

        return fileMetadataList;
    }
}
