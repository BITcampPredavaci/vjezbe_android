package com.bitcamp.benjamin.services_lab.singletons;

import android.util.Log;

import com.bitcamp.benjamin.services_lab.Post;
import com.bitcamp.benjamin.services_lab.service.ServiceRequest;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by benjamin on 14/04/15.
 */
public class PostFeed {
    private static PostFeed ourInstance = new PostFeed();

    public static PostFeed getInstance() {
        return ourInstance;
    }

    private List<Post> mFeed;

    private PostFeed() {
        mFeed = new ArrayList<Post>();
    }

    public void getFeed(String url){
        ServiceRequest.get(url, parseResponse());
    }

    public List<Post> getFeed(){
        return mFeed;
    }

    private Callback parseResponse(){
        return new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Log.e("RESPONSE", e.getMessage());
            }

            @Override
            public void onResponse(Response response) throws IOException {
                try {
                    JSONArray array = new JSONArray(response.body().string());
                    for(int i = 0; i < array.length(); i++){
                        JSONObject postObj = array.getJSONObject(i);
                        int id = postObj.getInt("id");
                        String title = postObj.getString("title");
                        String content = postObj.getString("content");
                        mFeed.add(new Post(id, title, content));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };
    }
}
