package com.xuanchengwei.filemanagementsystem.entity.xyplorer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xuanchengwei.filemanagementsystem.entity.FileGrade;
import com.xuanchengwei.filemanagementsystem.entity.FileMetadata;
import com.xuanchengwei.filemanagementsystem.utils.FileMetadataUtils;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * @author 禤成伟
 * @date 2023-01-07 - 10:55
 */
@Data
@NoArgsConstructor
public class DataInfo {

    private String absolutePath;
    private Integer grade;
    private List<String> tagList;




    public static DataInfo parseDataRecord(String dataRecord){
        if(!isData(dataRecord)) {
            return null;
        }
        DataInfo dataInfo = new DataInfo();
        String[] data = dataRecord.split("\\|",Integer.MAX_VALUE);
        dataInfo.absolutePath = data[0];
        dataInfo.grade = Objects.equals(data[1], "") || data[1] == null ? null : Integer.valueOf(data[1]);
        dataInfo.tagList = Arrays.stream(data[2].split(",")).toList();

        return dataInfo;
    }

    private static final Pattern TAG_DATA_PATTERN = Pattern.compile("\\w:\\\\.*\\|\\d\\|(.*\\|){10}");
    public static boolean isData(String dataString){
        return TAG_DATA_PATTERN.matcher(dataString).matches();
    }



    public File getFile(){
        return new File(absolutePath);
    }

    public FileGrade getFileGrade() throws IOException {
        FileGrade fileGrade = new FileGrade();
        fileGrade.setSha512(FileMetadataUtils.fastHashing(getFile()).getSha512());
        fileGrade.setGrade(grade);
        return fileGrade;
    }

    public String toXyplorerData(){
        return  getFile().getAbsolutePath() + "|"+ (this.grade == null ? "" : this.grade) +"|"+String.join(",",tagList)+"|||||||||||||||||";
    }

}
