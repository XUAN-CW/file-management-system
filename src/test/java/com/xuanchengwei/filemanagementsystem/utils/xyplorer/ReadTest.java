package com.xuanchengwei.filemanagementsystem.utils.xyplorer;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author 禤成伟
 * @date 2023-01-07 - 10:02
 */
@SpringBootTest
public class ReadTest {
    @Value("${xyplorer.tag-dat}")
    String tagDatString;

    private static final Pattern TAG_DATA_PATTERN = Pattern.compile("\\w:\\\\.*\\|\\d\\|(.*\\|){10}");

    @Test
    public void tag() throws IOException {
        File tagDat = new File(tagDatString);
        List<String> tagList = Files.readLines(tagDat, Charsets.UTF_16);
        for (String tag : tagList) {
//            System.out.println(tag);
            if(TAG_DATA_PATTERN.matcher(tag).matches()){
                String[] params = tag.split("\\|");
                String filePath = params[0];
                int grade = Integer.parseInt(params[1]);
                System.out.println(filePath + "\t" + grade);
            }
        }




    }


}
