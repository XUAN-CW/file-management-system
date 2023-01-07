package com.xuanchengwei.filemanagementsystem.service.impl;

import com.xuanchengwei.filemanagementsystem.entity.FileGrade;
import com.xuanchengwei.filemanagementsystem.mapper.FileGradeMapper;
import com.xuanchengwei.filemanagementsystem.service.FileGradeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 禤成伟
 * @since 2022-12-08 23:40:40
 */
@Service
public class FileGradeServiceImpl extends ServiceImpl<FileGradeMapper, FileGrade> implements FileGradeService {
    @Autowired
    FileGradeMapper fileGradeMapper;
    public void insertOrUpdate(List<FileGrade> fileGradeList){
        for (FileGrade fileGrade : fileGradeList) {
            insertOrUpdate(fileGrade);
        }
    }
    
    public void insertOrUpdate(FileGrade fileGrade){
        System.out.println(fileGrade);
        if(fileGradeMapper.selectById(fileGrade.getSha512())==null){
            fileGradeMapper.insert(fileGrade);
        }else {
            fileGradeMapper.updateById(fileGrade);
        }
    }
}
