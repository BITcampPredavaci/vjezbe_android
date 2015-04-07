package com.bitcamp.benjamin.newsapp_prep.views;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bitcamp.benjamin.newsapp_prep.R;
import com.bitcamp.benjamin.newsapp_prep.models.Article;

/**
 * This is the "view" or fragment we are going to use
 * to display one single article
 * Created by benjamin on 06/04/15.
 */
public class ArticleFragment extends Fragment {

    private Article mArticle;

    //the view components
    private TextView mArticleTitle;
    private TextView mArticleContent;

    /*you have seen this before*/
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        //TODO the user picked article
        //just to have something to work with
        mArticle = new Article("Test Title", "Some test content to be testy and stuff", false);
        for(int i = 0; i < 5; i++){
            mArticle.setContent(mArticle.getContent() + mArticle.getContent());
        }

    }


    /*The fun part, here our view is actually created
    first we inflate the layout, then we set the values and in case we need it
    there is a savedInstanceState to hold some data
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.article_fragment, parent, false);

        mArticleTitle = (TextView) v.findViewById(R.id.article_title);
        mArticleTitle.setText(mArticle.getTitle());

        mArticleContent = (TextView) v.findViewById(R.id.article_content);
        mArticleContent.setText(mArticle.getContent());

        return v;

    }

}
