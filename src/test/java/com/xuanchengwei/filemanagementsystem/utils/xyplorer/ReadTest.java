package com.xuanchengwei.filemanagementsystem.utils.xyplorer;

import com.xuanchengwei.filemanagementsystem.entity.xyplorer.DataInfo;
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

    @Test
    public void readTest() throws IOException {
        for (DataInfo dataInfo : xyplorerUtil.read()) {
            System.out.println(dataInfo);
        }
    }

    @Test
    public void writeTest() throws IOException {
        List<DataInfo> dataInfoList = xyplorerUtil.read();
        xyplorerUtil.write(dataInfoList.subList(1,3));
    }

}
