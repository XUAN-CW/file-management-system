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
import java.util.ArrayList;
import java.util.List;

/**
 * @author 禤成伟
 * @date 2022-11-20 - 0:52
 */
@SpringBootTest
public class FileMetadataTest {
    @Autowired
    FileMetadataMapper fileMetadataMapper;

    @Value("${file-management-system.targetDir}")
    String targetDirString;

    @Test
    public void fileMetadataUtil() throws IOException {
        List<FileMetadata> fileMetadataList = Collections.synchronizedList( new ArrayList<>(100));

        FileMetadataUtils.getFileList(new File("D:\\可删除2")).stream().parallel().forEach(target -> {
//            System.out.println(target.getAbsolutePath());
            try {
                FileMetadata fileMetadata = new FileMetadata(target).safetyHashing();
                fileMetadataList.add(fileMetadata);
            } catch (IOException e) {
                System.out.println(target.getAbsolutePath());
                e.printStackTrace();
            }
        });

        fileMetadataList.forEach(fileMetadata -> fileMetadataMapper.insert(fileMetadata));

    }

}
