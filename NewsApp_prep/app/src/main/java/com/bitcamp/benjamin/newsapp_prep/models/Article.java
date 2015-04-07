package com.bitcamp.benjamin.newsapp_prep.models;

import java.util.UUID;

/**
 * Our article model
 * Created by benjamin on 06/04/15.
 */
public class Article {

    private UUID mId;
    private String mTitle;
    private String mContent;
    private String mPreview;
    private boolean mRead;

    public Article() {
        mId = UUID.randomUUID();
        mTitle = "";
        mContent = "";
        mRead = false;
    }

    public Article(String title, String content, boolean read) {
        mId = UUID.randomUUID();
        mTitle = title;
        mContent = content;
        mRead = read;
        int endIndex = mContent.length() > 50 ? 50 : mContent.length();
        mPreview = mContent.substring(0, endIndex);
    }

    public String getPreview() {
        return mPreview;
    }

    public void setPreview(String preview) {
        mPreview = preview;
    }

    @Override
    public String toString(){
        return mTitle + "\n" +mContent;
    }

    public UUID getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
    }

    public boolean isRead() {
        return mRead;
    }

    public void setRead(boolean read) {
        mRead = read;
    }
}
