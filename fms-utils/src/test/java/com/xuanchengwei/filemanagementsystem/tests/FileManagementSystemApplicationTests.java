package com.xuanchengwei.filemanagementsystem.tests;

import com.xuanchengwei.filemanagementsystem.entity.FileMetadata;
import com.xuanchengwei.filemanagementsystem.mapper.FileMetadataMapper;
//import com.xuanchengwei.filemanagementsystem.filemetadata.utils.FileMetadataUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class FileManagementSystemApplicationTests {

    @Autowired
    FileMetadataMapper fileMetadataMapper;
    @Test
    void contextLoads() throws IOException {
        for (FileMetadata fileMetadata : fileMetadataMapper.selectList(null)) {
            System.out.println(fileMetadata);
        }
    }

}
