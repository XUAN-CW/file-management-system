package com.xuanchengwei.filemanagementsystem.entity;

import com.xuanchengwei.filemanagementsystem.utils.TargetDirUtils;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HexFormat;
import java.util.stream.Collectors;

/**
 * @author 禤成伟
 * @date 2022-12-07 - 22:15
 */
@Getter
@Setter
@NoArgsConstructor
public class VideoInfo {

    private FileMetadata fileMetadata;

    private FileGrade fileGrade;

    public VideoInfo(File imageAbsolutePath) throws IOException {
        fileMetadata = new FileMetadata(imageAbsolutePath);

    }

    public String getAbsolutePathHexBinary(){
        return HexFormat.of().formatHex(fileMetadata.getAbsolutePath().getBytes(StandardCharsets.UTF_8));
    }

    public String getParentAbsolutePathHexBinary(){
        return HexFormat.of().formatHex(new File(fileMetadata.getAbsolutePath()).getParent().getBytes(StandardCharsets.UTF_8));
    }

    public String getVideoHttpAbsolutePathHexBinaryUrl(){
        return "http://localhost:8081/static-resources/absolutePathHexBinary/" + HexFormat.of().formatHex(fileMetadata.getAbsolutePath().getBytes(StandardCharsets.UTF_8));
    }

    public String getVideoHttpUrl(){

        String result =  "http://localhost:8081/static/"
                + Arrays.stream(TargetDirUtils.getRelativizePath(fileMetadata.getFile())
                .split("\\\\"))
                .map(s -> URLEncoder.encode(s,StandardCharsets.UTF_8))
                .collect(Collectors.joining("/"));
        result = result.replaceAll("\\+","%20");
        return result;
    }

}
