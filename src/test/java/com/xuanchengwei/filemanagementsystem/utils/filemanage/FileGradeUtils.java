package com.xuanchengwei.filemanagementsystem.utils.filemanage;

import com.xuanchengwei.filemanagementsystem.entity.FileGrade;
import com.xuanchengwei.filemanagementsystem.entity.FileMetadata;
import com.xuanchengwei.filemanagementsystem.mapper.FileGradeMapper;
import com.xuanchengwei.filemanagementsystem.utils.FileMetadataUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.UncategorizedSQLException;

import java.io.File;
import java.io.IOException;

/**
 * @author 禤成伟
 * @date 2022-11-20 - 0:52
 */
@SpringBootTest
public class FileGradeUtils {
    @Autowired
    FileGradeMapper fileGradeMapper;


    @Test
    public void garbageFile() throws IOException {
        File targetFile = new File("D:\\可删除\\BoomBadaboom_2021-09_update");



        for (File target : FileMetadataUtils.getFileList(targetFile)) {
            FileMetadata fileMetadata = FileMetadataUtils.safetyHashing(target);
            FileGrade fileGrade = new FileGrade();
            fileGrade.setGrade(-1);
            fileGrade.setSha512(fileMetadata.getSha512());
            try {
                fileGradeMapper.insert(fileGrade);
            }catch (UncategorizedSQLException uncategorizedSQLException){
                System.out.println(fileMetadata);
            }

        }

    }


    @Test
    public void okFile() throws IOException {
        File targetFile = new File("D:\\可删除\\LuminousArt2021Jan");



        for (File target : FileMetadataUtils.getFileList(targetFile)) {
            FileMetadata fileMetadata =  FileMetadataUtils.safetyHashing(target);
            FileGrade fileGrade = new FileGrade();
            fileGrade.setGrade(3);
            fileGrade.setSha512(fileMetadata.getSha512());
            try {
                fileGradeMapper.insert(fileGrade);
            }catch (UncategorizedSQLException uncategorizedSQLException){
                System.out.println(fileMetadata);
            }

        }

    }

}
