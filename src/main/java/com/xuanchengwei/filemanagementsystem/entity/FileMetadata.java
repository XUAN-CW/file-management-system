package com.xuanchengwei.filemanagementsystem.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.LinkOption;
import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.hash.Hashing;
import com.google.common.io.Files;
import com.xuanchengwei.filemanagementsystem.constants.BinaryConstants;
import lombok.*;

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

    public FileMetadata(File targetFile) throws IOException {
        setFileMetadataAbsolutePath(targetFile.getAbsolutePath());
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

    public FileMetadata setFileMetadataAbsolutePath(String absolutePath) {
        this.file = new File(absolutePath);
        this.absolutePath = file.getAbsolutePath();
        this.metadataStore = new File(file.getAbsolutePath() + SUFFIX);
        this.fileName = file.getName();
        this.fileLength = file.length();
        return this;
    }

    public FileMetadata fastHashing() throws IOException {
        if(metadataStore.exists()){
            ObjectMapper objectMapper = new ObjectMapper();
            FileMetadata fileMetadata = objectMapper.readValue(metadataStore,FileMetadata.class);
            if(fileMetadata != null){
                this.everySegmentTakePieceSha512 = fileMetadata.getEverySegmentTakePieceSha512();
                this.md5 = fileMetadata.getMd5();
                this.sha1 = fileMetadata.getSha1();
                this.sha256 = fileMetadata.getSha256();
                this.sha384 = fileMetadata.getSha384();
                this.sha512 = fileMetadata.getSha512();
            }
        }else {
            fullHashing();
        }

        return this;
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

    private String calculateEverySegmentTakePieceSha512() throws IOException {
        final int minNumberOfSegment = 10;
        final int maxNumberOfSegment = 100;
        final int piece = (int) BinaryConstants.K;
        final long segment = Math.max(Math.floorDiv(file.length() + (maxNumberOfSegment * BinaryConstants.M - 1),maxNumberOfSegment * BinaryConstants.M) ,1) * BinaryConstants.M;
        if(file.length() < segment * minNumberOfSegment){
            this.sha512 = Files.asByteSource(file).hash(Hashing.sha512()).toString();
            return this.sha512;
        }else {
            try(FileInputStream fileInputStream = new FileInputStream(file)) {
                byte[] bytes = new byte[(int) Math.floorDiv(file.length(), segment) * piece];
                for (int i = 0; (long) (i + 1) * segment < file.length(); i++) {
                    fileInputStream.getChannel().position(segment * i);
                    if (fileInputStream.read(bytes, i * piece, piece) != piece) {
                        throw new IOException("第" + i + "段读取失败");
                    }
                }
                return Hashing.sha512().hashBytes(bytes).toString();
            }
        }
    }
    public FileMetadata safetyHashing() throws IOException {
        this.everySegmentTakePieceSha512 = calculateEverySegmentTakePieceSha512();
        if(metadataStore.exists()){
            ObjectMapper objectMapper = new ObjectMapper();
            FileMetadata fileMetadata = null;
            try {
                fileMetadata = objectMapper.readValue(metadataStore, FileMetadata.class);
            } catch (IOException e) {
                fullHashing();
                return this;
            }
            if(this.everySegmentTakePieceSha512.equals(fileMetadata.getEverySegmentTakePieceSha512())){
                this.md5 = fileMetadata.getMd5();
                this.sha1 = fileMetadata.getSha1();
                this.sha256 = fileMetadata.getSha256();
                this.sha384 = fileMetadata.getSha384();
                this.sha512 = fileMetadata.getSha512();
            }else {
                fullHashing();
            }
        }else {
            fullHashing();
        }
        return this;
    }

    public FileMetadata fullHashing() throws IOException {
        this.everySegmentTakePieceSha512 = calculateEverySegmentTakePieceSha512();
        this.md5 = Files.asByteSource(file).hash(Hashing.md5()).toString();
        this.sha1 = Files.asByteSource(file).hash(Hashing.sha1()).toString();
        this.sha256 = Files.asByteSource(file).hash(Hashing.sha256()).toString();
        this.sha384 = Files.asByteSource(file).hash(Hashing.sha384()).toString();
        this.sha512 = Files.asByteSource(file).hash(Hashing.sha512()).toString();

        ObjectMapper objectMapper = new ObjectMapper();
        if(metadataStore.delete() || !metadataStore.exists()){
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(metadataStore,this);
            metadataStore.setReadOnly();
            if(System.getProperty("os.name").toLowerCase().contains("windows")){
                java.nio.file.Files.setAttribute(metadataStore.toPath(), "dos:hidden", true, LinkOption.NOFOLLOW_LINKS);
            }
        }
        return this;
    }

}
