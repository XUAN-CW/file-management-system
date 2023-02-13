package com.xuanchengwei.filemanagementsystem.utils.xyplorer;

import com.xuanchengwei.filemanagementsystem.entity.FileGrade;
import com.xuanchengwei.filemanagementsystem.entity.FileMetadata;
import com.xuanchengwei.filemanagementsystem.entity.xyplorer.DataInfo;
import com.xuanchengwei.filemanagementsystem.mapper.FileGradeMapper;
import com.xuanchengwei.filemanagementsystem.utils.FileMetadataUtils;
import com.xuanchengwei.filemanagementsystem.utils.XyplorerUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 禤成伟
 * @date 2023-01-07 - 10:02
 */
@SpringBootTest
public class XyplorerTest {


    XyplorerUtil xyplorerUtil = new XyplorerUtil(new File("C:\\portable\\XYplorer_H\\Data\\tag.dat"));


    @Autowired
    FileGradeMapper fileGradeMapper;



    public void readTagDat() throws IOException {
        List<DataInfo> dataInfoList = xyplorerUtil.read();
        for (DataInfo dataInfo : dataInfoList) {
            FileGrade fileGrade = dataInfo.getFileGrade();
            if(fileGradeMapper.selectById(fileGrade.getSha512()) == null){
                fileGradeMapper.insert(fileGrade);
            }else {
                fileGradeMapper.updateById(fileGrade);
            }
        }
    }

    @Test
    public void writeTest() throws IOException {
        readTagDat();
        List<FileMetadata> fileMetadataList = FileMetadataUtils
                .getFileMetadataList(new File("R:\\筛选中\\4T"));
        List<DataInfo> dataInfoList =  new ArrayList<>();
        for (FileMetadata fileMetadata : fileMetadataList) {
            System.out.println(fileMetadata.getAbsolutePath());
            FileGrade fileGrade = fileGradeMapper.selectById(fileMetadata.getSha512());
            if(fileGrade != null){
                DataInfo dataInfo = new DataInfo();
                dataInfo.setAbsolutePath(fileMetadata.getAbsolutePath());
                dataInfo.setGrade(fileGrade.getGrade());
                dataInfoList.add(dataInfo);
            }
        }
        for (DataInfo dataInfo : dataInfoList) {
            System.out.println(dataInfo);
        }
        xyplorerUtil.write(dataInfoList);
    }

}
