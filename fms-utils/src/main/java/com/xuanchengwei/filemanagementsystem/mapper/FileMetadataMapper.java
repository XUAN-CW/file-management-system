package com.xuanchengwei.filemanagementsystem.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xuanchengwei.filemanagementsystem.entity.FileMetadata;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 禤成伟
 * @since 2022-10-06 03:32:25
 */
@Mapper
public interface FileMetadataMapper extends BaseMapper<FileMetadata> {

    List<FileMetadata> selectByCreateTimeAfter(@Param("time")LocalDateTime localDateTime);
}
