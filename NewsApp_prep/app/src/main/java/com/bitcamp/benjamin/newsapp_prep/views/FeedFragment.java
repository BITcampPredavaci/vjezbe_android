package com.bitcamp.benjamin.newsapp_prep.views;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.bitcamp.benjamin.newsapp_prep.R;
import com.bitcamp.benjamin.newsapp_prep.controllers.ArticleActivity;
import com.bitcamp.benjamin.newsapp_prep.models.Article;
import com.bitcamp.benjamin.newsapp_prep.models.Feed;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FeedFragment extends ListFragment {


    private ArrayList<Article> feed;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(R.string.list_name);
        feed = Feed.getInstance().getArticles();

        //TODO add your own adapter
        ArrayAdapter<Article> adapter =
                new ArrayAdapter<Article>(getActivity(), android.R.layout.simple_expandable_list_item_1,
                        feed);
       setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        //TODO load the content of the required article
        super.onListItemClick(l, v, position, id);
        Intent goToArticle = new Intent(getActivity(), ArticleActivity.class);
        startActivity(goToArticle);
    }
}
