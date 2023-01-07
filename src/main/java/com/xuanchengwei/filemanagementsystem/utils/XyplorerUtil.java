package com.xuanchengwei.filemanagementsystem.utils;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;

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
}
