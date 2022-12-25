package com.xuanchengwei.filemanagementsystem.utils.bt;


import bt.metainfo.MetadataService;
import bt.metainfo.Torrent;
import bt.metainfo.TorrentFile;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

/**
 * @author 禤成伟
 * @date 2022-10-01 - 22:59
 */
public class ParseBt {

    MetadataService metadataService = new MetadataService();

    File[] btFileList = new File("W:\\迅雷下载_ok-20221122_bt").listFiles();

    @Test
    public void parseBt() {
        for (File btFile : btFileList) {
            try {
                InputStream inputStream = Files.newInputStream(btFile.toPath());
                Torrent torrent = metadataService.fromInputStream(inputStream);
                System.out.println(torrent.getTorrentId() +" " + torrent.getName());
            }catch (Exception e){
                e.printStackTrace();
                System.out.println(btFile.getName());
            }
        }
    }
}
