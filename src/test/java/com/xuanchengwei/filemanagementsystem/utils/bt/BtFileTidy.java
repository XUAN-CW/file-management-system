package com.xuanchengwei.filemanagementsystem.utils.bt;

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
public class BtFileTidy {

    File btFileDir = new File("metadata/btFile");


    @Test
    public void move() throws IOException {
        File[] btFiles = btFileDir.listFiles(file -> file.getName().endsWith(".torrent"));
        for (File btFile : btFiles) {
            System.out.println(btFile.getAbsolutePath());
            MetadataService metadataService = new MetadataService();
            InputStream inputStream = Files.newInputStream(btFile.toPath());
            Torrent torrent = metadataService.fromInputStream(inputStream);
            File moveTo = new File(btFileDir.getAbsolutePath()+File.separator +
                    torrent.getTorrentId().toString().charAt(0) + File.separator +torrent.getTorrentId().toString()+".torrent");
            System.out.println(moveTo.getAbsolutePath());
            if(!moveTo.exists()){
                FileUtils.moveFile(btFile,moveTo);
            }
        }
    }

}
