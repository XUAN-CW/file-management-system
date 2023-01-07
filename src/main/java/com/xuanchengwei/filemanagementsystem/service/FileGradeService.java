package com.xuanchengwei.filemanagementsystem.service;

import com.xuanchengwei.filemanagementsystem.entity.FileGrade;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 禤成伟
 * @since 2022-12-08 23:40:40
 */
public interface FileGradeService extends IService<FileGrade> {
    public void insertOrUpdate(List<FileGrade> fileGradeList);
    public void insertOrUpdate(FileGrade fileGrade);
}
