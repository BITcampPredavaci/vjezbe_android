package com.bitcamp.benjamin.newsapp_prep.views;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bitcamp.benjamin.newsapp_prep.R;
import com.bitcamp.benjamin.newsapp_prep.controllers.ArticleActivity;
import com.bitcamp.benjamin.newsapp_prep.models.Article;
import com.bitcamp.benjamin.newsapp_prep.models.Feed;
import com.squareup.picasso.Picasso;

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
        if (mFeed.size() <= 0) {

            new FetchItemsTask().execute();
        }

        mAdapter = new ArticleAdapter(mFeed);
        setListAdapter(mAdapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {

        super.onListItemClick(l, v, position, id);

        Intent goToArticle = new Intent(getActivity(), ArticleActivity.class);
        Article clickedArticle = (Article) getListAdapter().getItem(position);
        clickedArticle.setRead(true);

        goToArticle.putExtra(ArticleFragment.EXTRA_ARTICLE_ID, clickedArticle.getId());
        startActivity(goToArticle);
    }

    @Override
    public void onResume() {
        super.onResume();
        mAdapter.notifyDataSetChanged();
    }

    //TODO adapter implementation
    private class ArticleAdapter extends ArrayAdapter<Article> {

        public ArticleAdapter(ArrayList<Article> articles) {
            super(getActivity(), 0, articles);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {

            Article current = getItem(position);
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater()
                        .inflate(R.layout.feed_fragment, null);
            }


            TextView articleTitle = (TextView) convertView.findViewById(R.id.article_title);
            articleTitle.setText(current.getTitle());
            if (current.isRead() == false) {
                articleTitle.setTextColor(getResources().
                        getColor(R.color.highlighted_text_material_light));
            }

            TextView articleContent = (TextView) convertView.findViewById(R.id.article_content);

            articleContent.setText(current.getPreview());

            ImageView articleImage = (ImageView) convertView.findViewById(R.id.article_image);


            Picasso.with(getContext()).load(current.getImageURL()).into(articleImage);


            return convertView;

        }

    }

    //TODO implement background task
    private class FetchItemsTask extends AsyncTask<Void, Void, Void> {
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

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mAdapter.notifyDataSetChanged();
        }
    }
}
