package com.xuanchengwei.filemanagementsystem.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.hash.Hashing;
import com.xuanchengwei.filemanagementsystem.entity.FileMetadata;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
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



    public FileMetadata fastHashing(File file) throws IOException {
        FileMetadata fileMetadata = new FileMetadata(file);
        if(fileMetadata.getMetadataStore().exists()){
            return new ObjectMapper().readValue(fileMetadata.getMetadataStore(),FileMetadata.class);
        }else {
            return fullHashing(file);
        }
    }


    public FileMetadata fullHashing(File file) throws IOException {
        FileMetadata fileMetadata = new FileMetadata(file);
        fileMetadata.setMd5(com.google.common.io.Files.asByteSource(file).hash(Hashing.md5()).toString());
        fileMetadata.setSha1(com.google.common.io.Files.asByteSource(file).hash(Hashing.sha1()).toString());
        fileMetadata.setSha256(com.google.common.io.Files.asByteSource(file).hash(Hashing.sha256()).toString());
        fileMetadata.setSha384(com.google.common.io.Files.asByteSource(file).hash(Hashing.sha384()).toString());
        fileMetadata.setSha512(com.google.common.io.Files.asByteSource(file).hash(Hashing.sha512()).toString());

        ObjectMapper objectMapper = new ObjectMapper();
        if(fileMetadata.getMetadataStore().delete() || !fileMetadata.getMetadataStore().exists()){
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(fileMetadata.getMetadataStore(),fileMetadata);
            fileMetadata.getMetadataStore().setReadOnly();
            if(System.getProperty("os.name").toLowerCase().contains("windows")){
                java.nio.file.Files.setAttribute(fileMetadata.getMetadataStore().toPath(), "dos:hidden", true, LinkOption.NOFOLLOW_LINKS);
            }
        }
        return fileMetadata;
    }
}
