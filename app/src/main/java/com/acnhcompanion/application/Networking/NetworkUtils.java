package com.acnhcompanion.application.Networking;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class NetworkUtils {
    private static final OkHttpClient mHTTPClient = new OkHttpClient();

    public static String doHttpGet(String url) throws IOException{
        Request request = new Request.Builder().url(url).build();
        Response response = mHTTPClient.newCall(request).execute();
        try{
            return response.body().string();
        } finally {
            response.close();
        }
    }

    public static String doHttpPost(String url, RequestBody requestBody) throws IOException{
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        Response response = mHTTPClient.newCall(request).execute();
        try{
            return response.body().string();
        } finally {
            response.close();
        }
    }
}
