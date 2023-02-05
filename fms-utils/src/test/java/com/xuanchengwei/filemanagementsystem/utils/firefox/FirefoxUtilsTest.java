package com.xuanchengwei.filemanagementsystem.utils.firefox;

import com.xuanchengwei.filemanagementsystem.constants.DateConstants;
import com.xuanchengwei.filemanagementsystem.entity.FileMetadata;
import com.xuanchengwei.filemanagementsystem.entity.firefox.FirefoxDownload;
import com.xuanchengwei.filemanagementsystem.mapper.FileMetadataMapper;
import com.xuanchengwei.filemanagementsystem.mapper.FirefoxDownloadMapper;
import com.xuanchengwei.filemanagementsystem.mapper.FirefoxDownloadFirefoxBrowserMapper;
import com.xuanchengwei.filemanagementsystem.utils.FileMetadataUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * @author 禤成伟
 * @date 2022-11-19 - 1:25
 */
@SpringBootTest
public class FirefoxUtilsTest {

    @Autowired
    FirefoxDownloadFirefoxBrowserMapper firefoxDownloadMapper;


    @Autowired
    FirefoxDownloadMapper firefoxDownloadManageMapper;
    @Autowired
    FileMetadataMapper fileMetadataMapper;


    @Test
    public void hashingDownloadFileIn1Day() throws IOException {
        Long queryAfter = LocalDateTime.now().minusHours(1).atZone(DateConstants.DEFAULT_ZONE_ID).toInstant().toEpochMilli();
        for (FirefoxDownload firefoxDownload : firefoxDownloadMapper.selectDownloadCompleteBydateAdded(queryAfter)) {
            if(firefoxDownload.getFile().exists()){
                FileMetadata fileMetadata = FileMetadataUtils.safetyHashing(firefoxDownload.getFile());
                firefoxDownload.setSha512(fileMetadata.getSha512());
                fileMetadataMapper.insert(fileMetadata);
                firefoxDownloadManageMapper.insert(firefoxDownload);
            }
        }
    }

}
