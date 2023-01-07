package com.xuanchengwei.filemanagementsystem.entity.xyplorer;

import com.xuanchengwei.filemanagementsystem.entity.FileGrade;
import com.xuanchengwei.filemanagementsystem.entity.FileMetadata;
import lombok.Data;

import java.io.File;
import java.io.IOException;

/**
 * @author 禤成伟
 * @date 2023-01-07 - 10:55
 */
@Data
public class DataInfo {

    private String absolutePath;
    private Integer grade;


    public File getFile(){
        return new File(absolutePath);
    }

    public FileMetadata getFileMetadata() throws IOException {
        return new FileMetadata(getFile()).fastHashing();
    }

    public FileGrade getFileGrade() throws IOException {
        FileGrade fileGrade = new FileGrade();
        fileGrade.setSha512(getFileMetadata().getSha512());
        fileGrade.setGrade(grade);
        return fileGrade;
    }

    public String toXyplorerData(){
        return  getFile().getAbsolutePath() + "|"+ getGrade() +"||||||||||||||||||";
    }

}
