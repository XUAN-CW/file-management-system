package com.xuanchengwei.filemanagementsystem.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author 禤成伟
 * @since 2022-12-07 21:45:54
 */
@Getter
@Setter
@TableName("file_status")
public class FileStatus implements Serializable {

    private String sha512;

    private String fileStatus;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    public enum FileStatusEnum {
        /**
         * 已删除
         */
        DELETED,
        /**
         * 可删除
         */
        DELETABLE;
    }


}
