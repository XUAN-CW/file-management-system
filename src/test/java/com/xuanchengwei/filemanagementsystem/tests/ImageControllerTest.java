package com.xuanchengwei.filemanagementsystem.tests;

import com.xuanchengwei.filemanagementsystem.entity.FileMetadata;
import com.xuanchengwei.filemanagementsystem.controller.ImageController;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
 * @date 2022-12-04 - 18:04
 */
@SpringBootTest
public class ImageControllerTest {
    @Autowired
    ImageController imageController;
    @Test
//    void contextLoads() throws IOException {
//        for (String image : imageController.getImageList()) {
//            System.out.println(image);
//        }
//    }

    void t1() throws IOException {
        List<File> targetFileList = new ArrayList<>();
        Files.walkFileTree(new File("C:\\core\\java\\my-project\\file-management-system\\src\\main\\resources\\static\\image-viewer\\src\\assets\\image").toPath(), new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                if(!file.toFile().getAbsolutePath().endsWith(FileMetadata.SUFFIX)){
                    targetFileList.add(file.toFile());
                }
                return FileVisitResult.CONTINUE;
            }
        });


        for (File file : targetFileList) {
            System.out.println(file.getAbsolutePath());
            String target = file.getName().subSequence(0,21) + file.getName().substring(file.getName().length() -4);
//            System.out.println(file.getParentFile().getAbsolutePath() + File.separator + target);
            FileUtils.moveFile(file,new File(file.getParentFile().getAbsolutePath() + File.separator + target));
        }

    }
}
