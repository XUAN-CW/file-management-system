package com.xuanchengwei.filemanagementsystem.utils.filemanage;

import com.xuanchengwei.filemanagementsystem.entity.FileMetadata;
import com.xuanchengwei.filemanagementsystem.entity.FileTag;
import com.xuanchengwei.filemanagementsystem.mapper.FileMetadataMapper;
import com.xuanchengwei.filemanagementsystem.mapper.FileTagMapper;
import com.xuanchengwei.filemanagementsystem.utils.FileMetadataUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;

/**
 * @author 禤成伟
 * @date 2022-11-19 - 23:22
 */
@SpringBootTest
public class FileTagTest {

    @Autowired
    FileTagMapper fileTagMapper;
    @Autowired
    FileMetadataMapper fileMetadataMapper;


    File targetFile = new File("E:\\未整理\\jav");

    String[] tagArray = new String[]{"jav"};

    @Test
    public void addTagTest() throws IOException {
        for (File target : FileMetadataUtils.getFileList(targetFile)) {
            System.out.println(target.getAbsolutePath());
            FileMetadata fileMetadata =  FileMetadataUtils.safetyHashing(target);
            fileMetadataMapper.insert(fileMetadata);
            for (String tag : tagArray) {
                FileTag fileTag = new FileTag();
                fileTag.setTag(tag);
                fileTag.setSha512(fileMetadata.getSha512());
                fileTagMapper.insert(fileTag);
            }
        }

    }



}
