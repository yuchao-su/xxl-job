package com.xxl.job.executor.util;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class MsgUtil {

    public static void sendBarkMsg(String title, String text) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        Request request = new Request.Builder()
                .url("https://api.day.app/NjqoqfctzkPkYqpLFERdrT/" + title +"/" + text)
                .method("GET", null)
                .build();
        Response response = client.newCall(request).execute();
    }
}
