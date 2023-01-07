package com.xuanchengwei.filemanagementsystem.utils;

import com.xuanchengwei.filemanagementsystem.entity.FileGrade;
import com.xuanchengwei.filemanagementsystem.entity.FileMetadata;
import com.xuanchengwei.filemanagementsystem.entity.xyplorer.DataInfo;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.regex.Pattern;

/**
 * @author 禤成伟
 * @date 2023-01-07 - 10:45
 */
@Component
@Data
public class XyplorerUtil {

    @Value("${xyplorer.tag-dat}")
    private String tagDatString;

    private File tagDat;

    public File getTagDat() {
        if(tagDat == null){
            tagDat = new File(tagDatString);
        }
        return tagDat;
    }

    private static final Pattern TAG_DATA_PATTERN = Pattern.compile("\\w:\\\\.*\\|\\d\\|(.*\\|){10}");

    public boolean isData(String dataString){
        return TAG_DATA_PATTERN.matcher(dataString).matches();
    }

    public DataInfo getDateInfoFromDataString(String dataString) throws IOException {
        String[] data = dataString.split("\\|");
        String absolutePath = data[0];
        Integer grade = Integer.parseInt(data[1]);
        DataInfo dataInfo = new DataInfo();
        FileMetadata fileMetadata = new FileMetadata(new File(absolutePath)).fullHashing();
        dataInfo.setFileMetadata(fileMetadata);
        FileGrade fileGrade = new FileGrade();
        fileGrade.setSha512(fileMetadata.getSha512());
        fileGrade.setGrade(grade);
        dataInfo.setFileGrade(fileGrade);
        return dataInfo;
    }





}
