package com.xuanchengwei.filemanagementsystem.utils.xyplorer;

import com.xuanchengwei.filemanagementsystem.entity.xyplorer.DataInfo;
import com.xuanchengwei.filemanagementsystem.mapper.FileGradeMapper;
import com.xuanchengwei.filemanagementsystem.utils.XyplorerUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;

/**
 * @author 禤成伟
 * @date 2023-01-07 - 10:02
 */
@SpringBootTest
public class ReadTest {

    @Autowired
    XyplorerUtil xyplorerUtil;

    @Autowired
    FileGradeMapper fileGradeMapper;

    @Test
    public void readTest() throws IOException {
        for (DataInfo dataInfo : xyplorerUtil.read()) {
            fileGradeMapper.insert(dataInfo.getFileGrade());
        }
    }

    @Test
    public void writeTest() throws IOException {
        List<DataInfo> dataInfoList = xyplorerUtil.read();
        xyplorerUtil.write(dataInfoList.subList(1,3));
    }

}
