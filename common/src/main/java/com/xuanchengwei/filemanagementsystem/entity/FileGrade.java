package com.xuanchengwei.filemanagementsystem.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author 禤成伟
 * @since 2022-12-08 23:40:40
 */
@Data
@TableName("file_grade")
public class FileGrade implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("sha512")
    private String sha512;

    private Integer grade;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
