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
import java.util.List;

/**
 * @author 禤成伟
 * @date 2022-11-20 - 0:52
 */
@SpringBootTest
public class FileDistinctTargetDirTest {
    @Autowired
    FileMetadataMapper fileMetadataMapper;

    @Value("${file-management-system.targetDir}")
    String targetDirString;

    @Test
    public void FileDistinctUtil() throws IOException {
        File targetDir = new File(targetDirString);
        List<FileMetadata> needDistinctFileMetadataList = new ArrayList<>(100);

        FileMetadataUtils.getFileList(targetDir).stream().forEach(target -> {
            System.out.println(target.getAbsolutePath());
            try {
                FileMetadata fileMetadata = new FileMetadata(target).fastHashing();
                needDistinctFileMetadataList.add(fileMetadata);
                fileMetadataMapper.insert(fileMetadata);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });


        File deletableDir = TargetDirUtils.getDeletableDir();
        deletableDir.mkdirs();
        for (FileMetadata needDistinctFile : needDistinctFileMetadataList) {
            QueryWrapper<FileMetadata> wrapper = new QueryWrapper<>();
            wrapper.eq("sha512",needDistinctFile.getSha512());
            List<FileMetadata> existsFileMetadataList = fileMetadataMapper.selectList(wrapper);
            for (FileMetadata existsFileMetadata : existsFileMetadataList) {
                if (existsFileMetadata.getFile().exists()
                        && existsFileMetadata.getAbsolutePath().startsWith(targetDir.getAbsolutePath())
                        && !needDistinctFile.getFile().getAbsolutePath().equals(existsFileMetadata.getFile().getAbsolutePath())
                        ) {
                    File deletable = new File(deletableDir.getAbsolutePath() + File.separator
                            + targetDir.toPath().relativize(needDistinctFile.getFile().toPath()));

                    System.out.println(existsFileMetadata.getAbsolutePath() +"已存在\t"+needDistinctFile.getFile()+"-->"+deletable.getAbsolutePath());
                    FileUtils.moveFile(needDistinctFile.getFile(), deletable);
                    break;
                }
            }

        }
    }

}
