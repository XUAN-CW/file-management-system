package com.xuanchengwei.filemanagementsystem.entity;

import com.xuanchengwei.filemanagementsystem.utils.TargetDirUtils;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HexFormat;

/**
 * @author 禤成伟
 * @date 2022-12-07 - 22:15
 */
@Getter
@Setter
@NoArgsConstructor
public class ImageInfo {

    private FileMetadata fileMetadata;

    private FileGrade fileGrade;

    public ImageInfo(File imageAbsolutePath) throws IOException {
        fileMetadata = new FileMetadata(imageAbsolutePath);

    }

    public String getAbsolutePathHexBinary(){
        return HexFormat.of().formatHex(fileMetadata.getAbsolutePath().getBytes(StandardCharsets.UTF_8));
    }

    public String getImageHttpUrl(){
        return "http://localhost:8080/static/" + TargetDirUtils.getRelativizePath(fileMetadata.getFile()).replaceAll("\\\\","/");
    }

}
