package com.xuanchengwei.filemanagementsystem.utils.filemanage;

import com.xuanchengwei.filemanagementsystem.entity.FileMetadata;
import com.xuanchengwei.filemanagementsystem.entity.FileTag;
import com.xuanchengwei.filemanagementsystem.entity.FileTagSourceEnum;
import com.xuanchengwei.filemanagementsystem.mapper.FileTagMapper;
import com.xuanchengwei.filemanagementsystem.utils.FileMetadataUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author 禤成伟
 * @date 2023-02-04 - 18:09
 */
@SpringBootTest
public class FileTagTest {

    @Autowired
    FileTagMapper fileTagMapper;

    @Test
    public void addTag() throws IOException {
        String[] tagList = new String[]{"jav"};

        List<FileMetadata> fileMetadataList = FileMetadataUtils.getFileMetadataList(new File("Z:\\迅雷下载2"));
        for (FileMetadata fileMetadata : fileMetadataList) {
            for (String tag : tagList) {
                FileTag fileTag = new FileTag();
                fileTag.setSha512(fileTag.getSha512());
                fileTag.setTag(tag);
                fileTag.setSource(FileTagSourceEnum.manual.name());
                fileTagMapper.insert(fileTag);
            }
        }
    }

}
