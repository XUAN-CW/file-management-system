package com.xuanchengwei.filemanagementsystem.tests;

import org.junit.jupiter.api.Test;

import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.StringJoiner;
import java.util.stream.Collectors;


/**
 * @author 禤成伟
 * @date 2022-12-23 - 1:43
 */
public class UrlTest {
    String testUrl = "f95zone.to/新建文件夹/Arhoangel Collection [2020-08-14]/League of Legends/Ahri and Akali (K_DA Skin) In Steamy Kiss Animation (By Arhoangel) [League of Legends].mp4";
    String okUrl = "http://localhost:8080/static/f95zone.to/%E6%96%B0%E5%BB%BA%E6%96%87%E4%BB%B6%E5%A4%B9/Arhoangel%20Collection%20%5B2020-08-14%5D/League%20of%20Legends/Ahri%20and%20Akali%20(K_DA%20Skin)%20In%20Steamy%20Kiss%20Animation%20(By%20Arhoangel)%20%5BLeague%20of%20Legends%5D.mp4";
    @Test
    public void t1() throws URISyntaxException {
        String newURL = "http://localhost:8080/static/";

        String result =  "http://localhost:8080/static/" + Arrays.stream(testUrl.split("/")).map(s -> URLEncoder.encode(s,StandardCharsets.UTF_8)).collect(Collectors.joining("/"));
        result = result.replaceAll("\\+","%20");
        System.out.println(result);
        System.out.println(okUrl);
    }
}
