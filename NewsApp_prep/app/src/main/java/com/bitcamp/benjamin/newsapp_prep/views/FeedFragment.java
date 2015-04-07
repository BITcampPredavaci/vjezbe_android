package com.bitcamp.benjamin.newsapp_prep.views;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.bitcamp.benjamin.newsapp_prep.R;
import com.bitcamp.benjamin.newsapp_prep.controllers.ArticleActivity;
import com.bitcamp.benjamin.newsapp_prep.models.Article;
import com.bitcamp.benjamin.newsapp_prep.models.Feed;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FeedFragment extends ListFragment {


    private ArrayList<Article> mFeed;
    private ArticleAdapter mAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(R.string.list_name);
        mFeed = Feed.getInstance().getArticles();

        new FetchItemsTask().execute();
        //TODO add your own adapter
       mAdapter = new ArticleAdapter(mFeed);
       setListAdapter(mAdapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        //TODO load the content of the required article
        super.onListItemClick(l, v, position, id);
        Intent goToArticle = new Intent(getActivity(), ArticleActivity.class);
        Article clickedArticle = (Article)getListAdapter().getItem(position);

        goToArticle.putExtra(ArticleFragment.EXTRA_ARTICLE_ID, clickedArticle.getId());

        startActivity(goToArticle);
    }

    //TODO adapter implementation
    private class ArticleAdapter extends ArrayAdapter<Article>{

        public ArticleAdapter(ArrayList<Article> articles) {
            super(getActivity(), 0, articles);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup){

            Article current = getItem(position);
            if(convertView == null){
                convertView = getActivity().getLayoutInflater()
                        .inflate(R.layout.feed_fragment, null);
            }

            TextView articleTitle = (TextView)convertView.findViewById(R.id.article_title);
            articleTitle.setText(current.getTitle());
            if(current.isRead() == false){
                articleTitle.setTextColor(getResources().
                        getColor(R.color.highlighted_text_material_light));
            }

            TextView articleContent = (TextView) convertView.findViewById(R.id.article_content);

            articleContent.setText(current.getPreview());

            return convertView;

        }

    }

    //TODO implement background task
    private class FetchItemsTask extends AsyncTask<Void,Void,Void> {
        @Override
        protected Void doInBackground(Void... params) {
            try {
                Feed.getInstance().loadFeed("http://www.klix.ba/rss/svevijesti");
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
