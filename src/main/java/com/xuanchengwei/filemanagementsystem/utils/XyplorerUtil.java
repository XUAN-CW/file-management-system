package com.xuanchengwei.filemanagementsystem.utils;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
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







}
