package com.xuanchengwei.filemanagementsystem.utils.filemanage;

import com.xuanchengwei.filemanagementsystem.entity.FileMetadata;
import com.xuanchengwei.filemanagementsystem.mapper.FileMetadataMapper;
import com.xuanchengwei.filemanagementsystem.utils.FileMetadataUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author 禤成伟
 * @date 2022-11-20 - 0:52
 */
@SpringBootTest
public class FileMetadataTest {
    @Autowired
    FileMetadataMapper fileMetadataMapper;


    @Test
    public void fileMetadataUtil() throws IOException {
        List<FileMetadata> fileMetadataList = Collections.synchronizedList(new ArrayList<>(10000));

        List<File> fileList = FileMetadataUtils.getFileList(new File("R:\\筛选中\\115\\3D"));
        fileList.stream().parallel().forEach(target -> {
            System.out.println(target.getAbsolutePath());
            try {
                FileMetadata fileMetadata =  FileMetadataUtils.fastHashing(target);
                fileMetadata.saveToDisk();
                fileMetadataList.add(fileMetadata);
            } catch (IOException e) {
                System.err.println(target.getAbsolutePath());
                e.printStackTrace();
            }
        });

        fileMetadataList.forEach(fileMetadata -> fileMetadataMapper.insert(fileMetadata));

    }

}
