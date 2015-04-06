package com.bitcamp.benjamin.newsapp_prep.controllers;

import android.support.v4.app.Fragment;

import com.bitcamp.benjamin.newsapp_prep.helpers.SingleFragmentActivity;
import com.bitcamp.benjamin.newsapp_prep.views.ArticleFragment;

/**
 * In charge of showing a single Article
 * Created by benjamin on 06/04/15.
 */
public class ArticleActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new ArticleFragment();
    }


}
