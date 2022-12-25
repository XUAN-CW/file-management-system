package com.xuanchengwei.filemanagementsystem.utils.bt;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.xuanchengwei.filemanagementsystem.entity.bt.qbit.MainData;
import com.xuanchengwei.filemanagementsystem.entity.bt.qbit.QbitTorrent;
import okhttp3.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * @author 禤成伟
 * @date 2022-11-12 - 23:05
 */
public class QbitMoveSavePath {

    static String host = "ts464c:8300";
    static String username = "admin";
    static String password = "adminadmin";
    static String cookie;

    @BeforeAll
    @Test
    public static void login() throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        RequestBody body = new FormBody.Builder()
                .add("username", username)
                .add("password", password)
                .build();

        Request request = new Request.Builder()
                .url("http://" + host +"/api/v2/auth/login")
                .method("POST", body)
                .build();
        Response response = client.newCall(request).execute();
        cookie = response.header("set-cookie");
    }


    public MainData getMaindata() throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        Request maindataRequest = new Request.Builder()
                .url("http://" + host +"/api/v2/sync/maindata")
                .header("Cookie",cookie)
                .build();

        Response maindataResponse = client.newCall(maindataRequest).execute();
        String maindataString = maindataResponse.body().string();


        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(maindataString, MainData.class);
    }

    @Test
    public void setLocation() throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        Map<String, QbitTorrent> stringTorrentMap = getMaindata().getTorrents();
        Map<String, QbitTorrent> complete  = stringTorrentMap.entrySet()
                .stream().filter(stringTorrentEntry -> stringTorrentEntry.getValue().getProgress() == 1.0)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        LocalDateTime now = LocalDateTime.now();
        String nowString = now.getYear() + String.valueOf(now.getDayOfMonth())
                + now.getDayOfMonth() + now.getHour() + now.getMinute() + now.getSecond();
        for (Map.Entry<String, QbitTorrent> entry : complete.entrySet()) {
            String newLocation =entry.getValue().getSave_path() + "/ok-"+nowString+"/"
                    + (entry.getValue().getName().length() > 60 ?"":entry.getValue().getName())
                    + "_" + entry.getKey();
            RequestBody body = new FormBody.Builder()
                    .add("hashes", entry.getKey())
                    .add("location", newLocation)
                    .build();
            Request setLocationRequest = new Request.Builder()
                    .url("http://" + host +"/api/v2/torrents/setLocation")
                    .method("POST", body)
                    .header("Cookie",cookie)
                    .build();
            if(!entry.getValue().getSave_path().endsWith(entry.getKey())){
                Response setLocationResponse = client.newCall(setLocationRequest).execute();
                System.out.println("名字 ： "+entry.getValue().getName());
                System.out.println("新位置： "+newLocation);
                System.out.println("状态码： "+setLocationResponse.code());
                System.out.println("响应体： "+setLocationResponse.body().string()+"\n");
            }
        }


    }

}
