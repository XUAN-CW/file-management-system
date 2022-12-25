package com.xuanchengwei.filemanagementsystem.tests;

import com.xuanchengwei.filemanagementsystem.entity.FileStatus;
import com.xuanchengwei.filemanagementsystem.mapper.FileStatusMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

/**
 * @author 禤成伟
 * @date 2022-12-07 - 21:50
 */
@SpringBootTest
public class FileStatusTest {
    @Autowired
    FileStatusMapper fileStatusMapper;
    @Test
    void contextLoads() throws IOException {
        FileStatus fileStatus = new FileStatus();
        fileStatus.setFileStatus(FileStatus.FileStatusEnum.DELETED.toString());
        fileStatusMapper.insert(fileStatus);
    }





}
