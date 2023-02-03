package com.xuanchengwei.filemanagementsystem.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xuanchengwei.filemanagementsystem.entity.firefox.FirefoxDownload;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 禤成伟
 * @since 2022-11-19 00:42:10
 */
@Mapper
@DS("firefox_browser")
public interface FirefoxDownloadFirefoxBrowserMapper extends BaseMapper<FirefoxDownload> {

    List<FirefoxDownload> selectDownloadCompleteBydateAdded(@Param("epochMilli")Long epochMilli);
}
