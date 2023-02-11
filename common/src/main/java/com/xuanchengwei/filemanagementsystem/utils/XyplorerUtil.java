package com.xuanchengwei.filemanagementsystem.utils;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.xuanchengwei.filemanagementsystem.entity.xyplorer.DataInfo;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author 禤成伟
 * @date 2023-01-07 - 10:45
 */
public class XyplorerUtil {


    public XyplorerUtil(File tagDat) {
        this.tagDat = tagDat;
    }

    private final File tagDat;

    public File getTagDat() {
        return tagDat;
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
        // 处理不是 data 的部分
        String[] notDataStringArray = new String[1024 * 1024];
        int notDataStringIndex = 0;
        for (String line : Files.readLines(getTagDat(), Charsets.UTF_16)) {
            if(!isData(line)){
                notDataStringArray[notDataStringIndex++] = line;
            }
        }
        // 处理 data 的部分
        String[] dataStringArray = dataInfoList.stream().map(DataInfo::toXyplorerData).toArray(String[]::new);
        Arrays.sort(dataStringArray);
        // 合并
        String[] tagDatArray = new String[notDataStringIndex + dataStringArray.length];
        System.arraycopy(notDataStringArray, 0, tagDatArray, 0, notDataStringIndex + 1);
        System.arraycopy(dataStringArray, 0, tagDatArray, notDataStringIndex + 1, dataStringArray.length);
        // 写入
        Files.asCharSink(getTagDat(),Charsets.UTF_16).writeLines(Arrays.asList(tagDatArray));
    }





}
