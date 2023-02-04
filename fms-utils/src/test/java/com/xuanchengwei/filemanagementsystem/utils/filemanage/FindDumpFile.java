package com.xuanchengwei.filemanagementsystem.utils.filemanage;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xuanchengwei.filemanagementsystem.entity.FileMetadata;
import com.xuanchengwei.filemanagementsystem.mapper.FileMetadataMapper;
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
public class FindDumpFile {
    @Autowired
    FileMetadataMapper fileMetadataMapper;

    @Test
    public void FileDistinctUtil() throws IOException {
        for (FileMetadata needDistinctFile : FileMetadataUtils.getFileMetadataList(new File("dir"))) {
            QueryWrapper<FileMetadata> sameFileQueryWrapper = new QueryWrapper<>();
            sameFileQueryWrapper.eq("sha512",needDistinctFile.getSha512());
            List<FileMetadata> sameFileMetadataList = fileMetadataMapper.selectList(sameFileQueryWrapper).stream()
                    .filter(fileMetadata -> fileMetadata.getFile().exists()
                            && !needDistinctFile.getFile().getAbsolutePath().equals(fileMetadata.getFile().getAbsolutePath()))
                    .toList();
            if(sameFileMetadataList.size() != 0){
                // 过滤完了还不等于零，说明已经存在相同文件
                System.out.println(needDistinctFile.getAbsolutePath());
            }else {
                System.out.println();
            }
        }
    }

}
