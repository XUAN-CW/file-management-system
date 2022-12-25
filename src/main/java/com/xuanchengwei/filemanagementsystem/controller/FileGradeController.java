package com.xuanchengwei.filemanagementsystem.controller;

import com.xuanchengwei.filemanagementsystem.entity.FileGrade;
import com.xuanchengwei.filemanagementsystem.mapper.FileGradeMapper;
import com.xuanchengwei.filemanagementsystem.service.FileGradeService;
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
 * @since 2022-12-08 23:40:40
 */
@RestController
@RequestMapping("/filemetadata/fileGrade")
public class FileGradeController {
    @Autowired
    FileGradeService fileGradeService;

    @RequestMapping("insertOrUpdate")
    public void insertOrUpdate(@RequestBody List<FileGrade> fileGradeList){
        fileGradeService.insertOrUpdate(fileGradeList);
    }
}
