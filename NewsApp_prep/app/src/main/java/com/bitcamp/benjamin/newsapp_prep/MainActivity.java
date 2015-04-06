package com.bitcamp.benjamin.newsapp_prep;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.bitcamp.benjamin.newsapp_prep.controllers.FeedActivity;


/**
 * This is the app's entry point
 * Take a not on the parent class!!!
 */
public class MainActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent feedIntent = new Intent(this, FeedActivity.class);
        startActivity(feedIntent);
    }
}
