package com.xuanchengwei.filemanagementsystem.controller;


import com.xuanchengwei.filemanagementsystem.mapper.FileGradeMapper;
import com.xuanchengwei.filemanagementsystem.entity.ImageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.HexFormat;
import java.util.List;

/**
 * @author 禤成伟
 * @date 2022-12-04 - 16:39
 */
@RestController
@RequestMapping("/image/")
public class ImageController {

    @Value("${file-management-system.targetDir}")
    String imageDirString;


    @Autowired
    FileGradeMapper fileGradeMapper;


    @GetMapping("getImageList")
    public List<ImageInfo> getImageList() throws IOException {

        List<ImageInfo> targetFileList = new ArrayList<>();
        Files.walkFileTree(new File(imageDirString).toPath(), new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) throws IOException {
                if("".equals(path.toFile().getAbsolutePath().toLowerCase().replaceAll(".*.(jpg|jpeg|png|gif|bmp|tiff|tif|svg|webp)",""))){
                    ImageInfo imageInfo = new ImageInfo(path.toFile());
                    imageInfo.getFileMetadata().fastHashing();
                    imageInfo.setFileGrade(fileGradeMapper.selectById(imageInfo.getFileMetadata().getSha512()));
                    targetFileList.add(imageInfo);
                }
                return FileVisitResult.CONTINUE;
            }
        });
        return targetFileList;
    }

//    @GetMapping(value = "/static-resources/{absolutePathHexBinary}",produces = MediaType.MULTIPART_FORM_DATA_VALUE)
//    @ResponseBody
//    public ResponseEntity<InputStreamResource> staticResources(@PathVariable("absolutePathHexBinary")String absolutePathHexBinary, HttpServletResponse response) throws Exception {
//        File file = new File(new String(HexFormat.of().parseHex(absolutePathHexBinary)));
//        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
//        return ResponseEntity.ok()
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName())
//                .contentLength(file.length())
//                .contentType(MediaType.MULTIPART_FORM_DATA)
//                .body(resource);
//    }
}
