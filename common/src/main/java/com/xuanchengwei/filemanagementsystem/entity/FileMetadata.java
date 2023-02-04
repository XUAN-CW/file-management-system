package com.xuanchengwei.filemanagementsystem.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author 禤成伟
 * @since 2022-10-06 03:32:25
 */
@Data
@NoArgsConstructor
@TableName("file_metadata")
public class FileMetadata implements Serializable {

    public FileMetadata(File file) throws IOException {
        this.file = file;
        this.absolutePath = file.getAbsolutePath();
        this.fileName = file.getName();
        this.fileLength = file.length();
    }

    public static final String SUFFIX = ".file-management-system.FileMetadata.json";

    @Setter(AccessLevel.NONE)
    private transient File file;

    @Setter(AccessLevel.NONE)
    private transient File metadataStore;


    private String absolutePath;
    private String fileName;
    private Long fileLength;

    private String md5;
    private String sha1;
    private String sha256;
    private String sha384;
    private String sha512;

    public File getFile() {
        return file == null ? new File(absolutePath) : file;
    }


    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /**
     * 大文件的 SHA-512 计算起来太耗时，因此将大文件分割为 N 段，每 segment 字节为一段，
     * 每段取 piece 字节，N 个 piece 组成一个 byte 数组，再算出此 byte 数组的 SHA-512
     */
    private String everySegmentTakePieceSha512;

    public FileMetadata copyHashFromFileMetadata(FileMetadata fileMetadata){
        this.md5 = fileMetadata.getMd5();
        this.sha1 = fileMetadata.getSha1();
        this.sha256 = fileMetadata.getSha256();
        this.sha384 = fileMetadata.getSha384();
        this.sha512 = fileMetadata.getSha512();
        this.everySegmentTakePieceSha512 = fileMetadata.getEverySegmentTakePieceSha512();
        return this;
    }

    public File getMetadataStore() {
        return new File(file.getAbsolutePath() + SUFFIX);
    }

    public FileMetadata readHashFromMetadataStore() throws IOException {
        return new ObjectMapper().readValue(getMetadataStore(),FileMetadata.class);
    }

    public static void saveToDisk(FileMetadata fileMetadata) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        if(fileMetadata.getMetadataStore().delete() || !fileMetadata.getMetadataStore().exists()){
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(fileMetadata.getMetadataStore(),fileMetadata);
            fileMetadata.getMetadataStore().setReadOnly();
            if(System.getProperty("os.name").toLowerCase().contains("windows")){
                Files.setAttribute(fileMetadata.getMetadataStore().toPath(), "dos:hidden", true, LinkOption.NOFOLLOW_LINKS);
            }
        }
    }

}
