package com.xuanchengwei.filemanagementsystem.utils.filemanage;

import com.xuanchengwei.filemanagementsystem.entity.FileGrade;
import com.xuanchengwei.filemanagementsystem.mapper.FileGradeMapper;
import com.xuanchengwei.filemanagementsystem.utils.FileMetadataUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author 禤成伟
 * @date 2022-11-20 - 0:52
 */
@SpringBootTest
public class FindDeletableFile {




    @Autowired
    FileGradeMapper fileGradeMapper;



    @Test
    public void findDeletableFile() throws IOException {
        List<File> fileList = FileMetadataUtils.getFileList(new File("Z:\\迅雷下载2\\迅雷下载2"));
        fileList.stream().parallel().forEach(file -> {
            try {
                FileGrade fileGrade = fileGradeMapper.selectById(FileMetadataUtils.fastHashing(file).getSha512());
                if(fileGrade.getGrade() == 1){
                    System.out.println(file.getAbsolutePath());
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

}
