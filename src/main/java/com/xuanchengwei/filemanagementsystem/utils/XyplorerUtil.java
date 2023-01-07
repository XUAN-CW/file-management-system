package com.xuanchengwei.filemanagementsystem.utils;

import com.google.common.base.Charsets;
import com.google.common.io.CharSink;
import com.google.common.io.FileWriteMode;
import com.google.common.io.Files;
import com.xuanchengwei.filemanagementsystem.entity.FileGrade;
import com.xuanchengwei.filemanagementsystem.entity.FileMetadata;
import com.xuanchengwei.filemanagementsystem.entity.xyplorer.DataInfo;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.*;
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

    private File getTagDat() {
        if(tagDat == null || !tagDat.getAbsolutePath().equals(tagDatString)){
            tagDat = new File(tagDatString);
        }
        return tagDat;
    }

    private File getTargetDir() {
        if(targetDir == null || !targetDir.getAbsolutePath().equals(targetDirString)){
            targetDir = new File(targetDirString);
        }
        return targetDir;
    }

    private static final Pattern TAG_DATA_PATTERN = Pattern.compile("\\w:\\\\.*\\|\\d\\|(.*\\|){10}");

    private boolean isData(String dataString){
        return TAG_DATA_PATTERN.matcher(dataString).matches();
    }

    private DataInfo getDateInfoFromDataString(String dataString) throws IOException {
        String[] data = dataString.split("\\|");
        DataInfo dataInfo = new DataInfo();
        dataInfo.setAbsolutePath(data[0]);
        dataInfo.setGrade(Integer.parseInt(data[1]));
        return dataInfo;
    }

    public List<DataInfo> read() throws IOException {
        List<String> dataStringList = Files.readLines(getTagDat(), Charsets.UTF_16);
        return dataStringList.stream().parallel()
                .filter(this::isData)
                .map(dataString -> {
                    try {
                        return getDateInfoFromDataString(dataString);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return null;
                })
                .filter(dataInfo -> dataInfo != null && dataInfo.getFile().exists() && dataInfo.getFile().isFile())
                .collect(Collectors.toList());
    }

    public void write(List<DataInfo> dataInfoList) throws IOException {
        List<String> notDataStringList = Files.readLines(getTagDat(), Charsets.UTF_16).stream()
                .filter(s -> !isData(s)).toList();
        List<String> dataStringList = dataInfoList.stream().map(DataInfo::toXyplorerData).toList();

        List<String> tagDatList = new ArrayList<>();
        tagDatList.addAll(notDataStringList);
        tagDatList.addAll(dataStringList);
        Collections.sort(tagDatList.subList(notDataStringList.size(),tagDatList.size() - 1));

        Files.asCharSink(getTagDat(),Charsets.UTF_16).writeLines(tagDatList);

    }





}
