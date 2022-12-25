package com.xuanchengwei.filemanagementsystem.entity.bt;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
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
 * @since 2022-11-04 19:45:47
 */
@Data
@TableName("torrent_file_info")
public class TorrentFileInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String torrent;

    private String filePathOnTorrent;

    private Long size;

    private Integer fileIndex;

    private String sha1;

    private Long offset;

    private String firstChunkHash;

    private Long chunkSize;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
