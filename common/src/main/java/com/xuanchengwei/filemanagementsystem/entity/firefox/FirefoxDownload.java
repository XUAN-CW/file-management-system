package com.xuanchengwei.filemanagementsystem.entity.firefox;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.xuanchengwei.filemanagementsystem.constants.DateConstants;
import lombok.Data;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDateTime;

/**
 * @author 禤成伟
 * @date 2022-11-18 - 23:20
 */
@Data
public class FirefoxDownload {
    /** 来自 Firefox */
    String id;
    String mozAnnosId;
    String mozPlacesId;
    Long dateAdded;
    String content;
    String url;
    String title;

    public LocalDateTime getDateAddedLocalDateTime(){
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(dateAdded / 1000), DateConstants.DEFAULT_ZONE_ID);
    }


    public String getContentDecode() throws UnsupportedEncodingException {
        return java.net.URLDecoder.decode(content, StandardCharsets.UTF_8.name());
    }

    public File getFile() throws UnsupportedEncodingException {
        String os = System.getProperty("os.name").toLowerCase();
        if(os.contains("windows")){
            return new File(getContentDecode().replaceFirst("^file:///", ""));
        }
        return null;
    }


    /** 来自 自己添加的附加信息 */
    private String sha512;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
