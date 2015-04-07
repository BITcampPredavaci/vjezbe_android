package com.bitcamp.benjamin.newsapp_prep.models;

import java.util.ArrayList;
import java.util.UUID;

/**
 * A collection of articles, for now
 * it loads a few articles statically
 * this will be changed soon enough
 * Created by benjamin on 06/04/15.
 */
public class Feed {
    private static Feed ourInstance = new Feed();

    public static Feed getInstance() {
        return ourInstance;
    }

    private ArrayList<Article> articles;

    private Feed() {
        articles = new ArrayList<Article>();

        /*for the time being*/
        articles.add(new Article(
            "Some Title",
             "A bit of a longer string with some more content and stuff",
             false
        ));

        for(int i = 0; i < 50; i++){
            articles.add(new Article(
                    "Article title " + (i+1),
                    "Some content for article # " + (i+1),
                    i%2 == 0
            ));

        }

    }

    public ArrayList<Article> getArticles(){
        return articles;
    }

    //TODO implement method to get a particular article from the list
    public Article getArticle(UUID id){
        return null;
    }

    //TODO implement method we call to get articles from the web
    public void loadFeed(String url){

    }


}
