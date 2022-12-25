package com.xuanchengwei.filemanagementsystem.utils;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import org.apache.ibatis.annotations.Mapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;

/**
 * @author 禤成伟
 * @date 2022-10-03 - 0:10
 */
@SpringBootTest
public class CodeGenerator {

    @Value("${spring.datasource.dynamic.datasource.master.url}")
    String url;
    @Value("${spring.datasource.dynamic.datasource.master.driver-class-name}")
    String driverName;
    @Value("${spring.datasource.dynamic.datasource.master.username}")
    String userName;
    @Value("${spring.datasource.dynamic.datasource.master.password}")
    String password;

    @Test
    void codeGenerator() {

        FastAutoGenerator.create(url, userName, password)
                .globalConfig(builder -> {
                    builder.author("禤成伟") // 设置作者
                            .commentDate("yyyy-MM-dd HH:mm:ss")
                            .disableOpenDir()
                            .outputDir("src/main/java"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.xuanchengwei.filemanagementsystem") // 设置父包名
                            .moduleName("filemetadata") // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.xml, "src/main/resources/mapper/filemetadata")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> builder.addInclude("file_grade"))
                .strategyConfig(builder -> builder.entityBuilder().enableLombok())
                .strategyConfig(builder -> builder.mapperBuilder().mapperAnnotation(Mapper.class))
                .strategyConfig(builder -> builder.serviceBuilder().formatServiceFileName("%sService"))
                .strategyConfig(builder -> builder.controllerBuilder().enableRestStyle())
                .execute();
    }

}
