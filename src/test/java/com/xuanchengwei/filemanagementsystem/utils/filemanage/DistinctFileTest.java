package com.xuanchengwei.filemanagementsystem.utils.filemanage;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xuanchengwei.filemanagementsystem.entity.FileMetadata;
import com.xuanchengwei.filemanagementsystem.mapper.FileMetadataMapper;
import com.xuanchengwei.filemanagementsystem.utils.FileMetadataUtils;
import com.xuanchengwei.filemanagementsystem.utils.TargetDirUtils;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 禤成伟
 * @date 2022-11-20 - 0:52
 */
@SpringBootTest
public class DistinctFileTest {
    @Autowired
    FileMetadataMapper fileMetadataMapper;

    @Test
    public void FileDistinctUtil() throws IOException {
        for (FileMetadata needDistinctFile : FileMetadataUtils.getFileMetadataList(new File("f"))) {
            QueryWrapper<FileMetadata> wrapper = new QueryWrapper<>();
            wrapper.eq("sha512",needDistinctFile.getSha512());
            List<FileMetadata> existsFileMetadataList = fileMetadataMapper.selectList(wrapper)
                    .stream().filter(fileMetadata -> fileMetadata.getFile().exists()).toList();
            for (FileMetadata existsFileMetadata : existsFileMetadataList) {
                if (needDistinctFile.getFile().getAbsolutePath().equals(existsFileMetadata.getFile().getAbsolutePath())) {
                    System.out.println(existsFileMetadata.getAbsolutePath() +"已存在\t" + needDistinctFile.getFile());
                }
            }
        }
    }

}
