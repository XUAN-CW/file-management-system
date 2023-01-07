package com.xuanchengwei.filemanagementsystem.utils;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.xuanchengwei.filemanagementsystem.entity.FileGrade;
import com.xuanchengwei.filemanagementsystem.entity.FileMetadata;
import com.xuanchengwei.filemanagementsystem.entity.xyplorer.DataInfo;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author 禤成伟
 * @date 2023-01-07 - 10:45
 */
@Component
@Data
public class XyplorerUtil {

    @Value("${xyplorer.tag-dat}")
    private String tagDatString;

    @Value("${file-management-system.targetDir}")
    private String targetDirString;

    private File tagDat;

    private File targetDir;

    public File getTagDat() {
        if(tagDat == null || !tagDat.getAbsolutePath().equals(tagDatString)){
            tagDat = new File(tagDatString);
        }
        return tagDat;
    }

    public File getTargetDir() {
        if(targetDir == null || targetDir.getAbsolutePath().equals(targetDirString)){
            targetDir = new File(targetDirString);
        }
        return targetDir;
    }

    private static final Pattern TAG_DATA_PATTERN = Pattern.compile("\\w:\\\\.*\\|\\d\\|(.*\\|){10}");

    private boolean isData(String dataString){
        return TAG_DATA_PATTERN.matcher(dataString).matches();
    }

    public DataInfo getDateInfoFromDataString(String dataString) throws IOException {
        String[] data = dataString.split("\\|");
        File file = new File(data[0]);
        if(!file.exists() || !file.isFile()){ return null; }
        Integer grade = Integer.parseInt(data[1]);
        DataInfo dataInfo = new DataInfo();
        FileMetadata fileMetadata = new FileMetadata(file).fastHashing();
        dataInfo.setFileMetadata(fileMetadata);
        FileGrade fileGrade = new FileGrade();
        fileGrade.setSha512(fileMetadata.getSha512());
        fileGrade.setGrade(grade);
        dataInfo.setFileGrade(fileGrade);
        return dataInfo;
    }

    public List<DataInfo> read() throws IOException {
        List<String> dataList = Files.readLines(getTagDat(), Charsets.UTF_16);
        return dataList.stream().parallel().filter(this::isData).map(s -> {
            try {
                return getDateInfoFromDataString(s);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }).collect(Collectors.toList());
    }





}
