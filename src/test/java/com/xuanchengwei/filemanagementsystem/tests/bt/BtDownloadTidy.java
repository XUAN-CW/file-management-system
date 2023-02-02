package com.xuanchengwei.filemanagementsystem.tests.bt;

import bt.metainfo.MetadataService;
import bt.metainfo.Torrent;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

/**
 * @author 禤成伟
 * @date 2022-11-14 - 21:06
 */
public class BtDownloadTidy {

    File btFileDir = new File("W:\\迅雷下载_ok-20221122_bt");
    File downloadDir = new File("W:\\迅雷下载_ok-20221122");


    @Test
    public void move() {
        File[] btFiles = btFileDir.listFiles(file -> file.getName().endsWith(".torrent"));
        for (File btFile : btFiles) {
            MetadataService metadataService = new MetadataService();
            try {
                InputStream inputStream = Files.newInputStream(btFile.toPath());
                Torrent torrent = metadataService.fromInputStream(inputStream);
                File btDownload = new File(downloadDir.getAbsolutePath() +File.separator+torrent.getName());
                if(!btDownload.exists()){
                    continue;
                }
                File moveTo = new File(btDownload.getAbsolutePath() +
                        "_"+torrent.getTorrentId().toString()+File.separator+torrent.getName());
                moveTo.getParentFile().mkdirs();
                if(btDownload.isDirectory()){
                    FileUtils.moveDirectory(btDownload, moveTo);
                }else {
                    FileUtils.moveFile(btDownload,moveTo);
                }
            }catch (Exception e){
                e.printStackTrace();
                System.out.println(btFile.getName() + " 移动失败");
            }
        }
    }

}
