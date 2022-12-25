package com.xuanchengwei.filemanagementsystem.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * @author 禤成伟
 * @date 2022-12-16 - 20:21
 */
@Component
public class TargetDirUtils {

    public static File targetDir;


    @Value("${file-management-system.targetDir}")
    public void setTargetDir(String targetDirString) {
        TargetDirUtils.targetDir = new File(targetDirString);
    }

    public static String getRelativizePath(File file){
        return targetDir.toPath().relativize(file.toPath()).toString();
    }

    public static File getTargetDir() {
        return targetDir;
    }

    public static File getDeletableDir() {
        return new File(targetDir.getAbsolutePath() + ".file-management-system.deletable");
    }
}
