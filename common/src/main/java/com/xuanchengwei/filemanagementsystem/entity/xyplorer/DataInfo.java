package com.xuanchengwei.filemanagementsystem.entity.xyplorer;

import com.xuanchengwei.filemanagementsystem.entity.FileGrade;
import com.xuanchengwei.filemanagementsystem.entity.FileMetadata;
import com.xuanchengwei.filemanagementsystem.utils.FileMetadataUtils;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;
import java.io.IOException;
import java.util.List;
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

    private String dataRecord;

    public DataInfo(String dataRecord) {
        this.dataRecord = dataRecord;
    }

    public static final Pattern TAG_DATA_PATTERN = Pattern.compile("\\w:\\\\.*\\|\\d\\|(.*\\|){10}");
    public static boolean isData(String dataString){
        return TAG_DATA_PATTERN.matcher(dataString).matches();
    }

    private String[] getTagDatDetail(){
        return this.dataRecord.split("\\|");
    }



    public File getFile(){
        return new File(absolutePath);
    }

    public FileMetadata getFileMetadata() throws IOException {
        return FileMetadataUtils.fastHashing(getFile());
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
