package com.bitcamp.benjamin.tracker_lab;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;

/**
 * Created by benjamin on 14/04/15.
 */
public class ServiceRequest {



    public static void request(String url, Callback callback){

        MediaType JSON = MediaType.parse("application/json");

        OkHttpClient client = new OkHttpClient();

        Request.Builder requestBuilder = new Request.Builder();
            requestBuilder.get();

        Request request = requestBuilder
                .url(url)
                .addHeader("Accept", "application/json")
                .build();

        Call call = client.newCall(request);
        call.enqueue(callback);
    }

}
