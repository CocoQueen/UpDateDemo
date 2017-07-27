package com.example.coco.updatedemo;

import com.squareup.okhttp.OkHttpClient;

import java.util.concurrent.TimeUnit;

/**
 * Created by coco on 2017/7/21.
 */

public class OkHttpUtils {
    private static  OkHttpUtils okUtils = new OkHttpUtils();
    private static OkHttpClient client;

    private OkHttpUtils(){
        client=new OkHttpClient();
        client.setConnectTimeout(5, TimeUnit.SECONDS);
    }
    public static OkHttpClient getInstance(){
        return client;
    }
}
