package com.xuanchengwei.filemanagementsystem.utils;

import com.xuanchengwei.filemanagementsystem.entity.FileMetadata;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 禤成伟
 * @date 2022-12-16 - 20:21
 */
@Component
public class TargetDirUtils {

    public static File targetDir;


    @Value("${file-management-system.targetDir}")
    public void setTargetDir(String targetDirString) {
        TargetDirUtils.targetDir = new File(targetDirString);
    }

    public static String getRelativizePath(File file){
        return targetDir.toPath().relativize(file.toPath()).toString();
    }

    public static File getTargetDir() {
        return targetDir;
    }

    public static File getDeletableDir() {
        return new File(targetDir.getAbsolutePath() + ".file-management-system.deletable");
    }

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

    public static List<File> getFileList() throws IOException {
        List<File> targetFileList = new ArrayList<>();
        if(targetDir.isFile()){
            targetFileList.add(targetDir);
        }else {
            Files.walkFileTree(targetDir.toPath(), new SimpleFileVisitor<Path>() {
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
}
