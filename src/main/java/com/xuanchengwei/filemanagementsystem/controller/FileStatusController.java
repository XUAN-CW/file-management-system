package com.xuanchengwei.filemanagementsystem.controller;

import com.xuanchengwei.filemanagementsystem.entity.FileStatus;
import com.xuanchengwei.filemanagementsystem.mapper.FileStatusMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 禤成伟
 * @since 2022-12-07 21:45:54
 */
@RestController
@RequestMapping("/filemetadata/fileStatus")
public class FileStatusController {

    @Autowired
    FileStatusMapper fileStatusMapper;

    @RequestMapping("insert")
    public void insert(@RequestBody List<FileStatus> fileStatusList){
        for (FileStatus fileStatus : fileStatusList) {
            fileStatusMapper.insert(fileStatus);
        }
    }

}
