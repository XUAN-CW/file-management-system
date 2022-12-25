package com.xuanchengwei.filemanagementsystem.controller;

import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.HexFormat;

/**
 * @author 禤成伟
 * @date 2022-12-04 - 16:39
 */
@RestController
@RequestMapping("/static-resources/")
public class StaticResourcesController {
    @GetMapping(value = "absolutePathHexBinary/{absolutePathHexBinary}",produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Resource> staticResources(@PathVariable("absolutePathHexBinary")String absolutePathHexBinary) throws IOException {
        File file = new File(new String(HexFormat.of().parseHex(absolutePathHexBinary)));
        InputStream fileInputStream = new FileInputStream(file);
        Resource resource = new InputStreamResource(fileInputStream);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", Files.probeContentType(file.toPath()));
        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }
}
