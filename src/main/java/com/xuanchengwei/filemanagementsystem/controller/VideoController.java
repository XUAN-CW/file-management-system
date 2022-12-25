package com.xuanchengwei.filemanagementsystem.controller;


import com.xuanchengwei.filemanagementsystem.mapper.FileGradeMapper;
import com.xuanchengwei.filemanagementsystem.entity.VideoInfo;
import com.xuanchengwei.filemanagementsystem.utils.TargetDirUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author 禤成伟
 * @date 2022-12-04 - 16:39
 */
@RestController
@RequestMapping("/video/")
public class VideoController {


    @Autowired
    FileGradeMapper fileGradeMapper;

    public static final Pattern VIDEO_FORMAT = Pattern.compile(".*.(avi|mp4|webm|ogg|flv|3gp|mkv|mov|m4v|mp3|wav|flac)");

    @GetMapping("getVideoList")
    public List<VideoInfo> getVideoList() throws IOException {
        List<VideoInfo> targetFileList = new ArrayList<>();
        Files.walkFileTree(TargetDirUtils.getTargetDir().toPath(), new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) throws IOException {
                if(VIDEO_FORMAT.matcher(path.toFile().getAbsolutePath().toLowerCase()).matches()){
                    VideoInfo videoInfo = new VideoInfo(path.toFile());
                    targetFileList.add(videoInfo);
                }
                return FileVisitResult.CONTINUE;
            }
        });
        targetFileList.stream().parallel().forEach(videoInfo -> {
            try {
                videoInfo.getFileMetadata().fastHashing();
                videoInfo.setFileGrade(fileGradeMapper.selectById(videoInfo.getFileMetadata().getSha512()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        return targetFileList;
    }

}
