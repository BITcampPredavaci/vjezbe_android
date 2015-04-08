package com.bitcamp.benjamin.newsapp_prep.models;


import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
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
    }

    public ArrayList<Article> getArticles(){
        return articles;
    }


    public Article getArticle(UUID id){
        for(Article a : articles){
            if(a.getId().equals(id))
                return a;
        }
        return null;
    }

    public void setRead(UUID id) {
        for (Article a : articles) {
            if (a.getId().equals(id)) {
                a.setRead(true);

            }
        }
    }

    //TODO implement method we call to get articles from the web
    public void loadFeed(String url) throws XmlPullParserException, IOException {
        String xmlString = getXml(url);

        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        XmlPullParser parser = factory.newPullParser();
        parser.setInput(new StringReader(xmlString));
        parseItems(parser);
    }

    private String getXml(String url) {
        URL requestUrl;
        try {
            requestUrl = new URL(url);
        } catch (MalformedURLException e) {

            e.printStackTrace();
            return null;
        }

        HttpURLConnection connection;
        try {
            connection = (HttpURLConnection) requestUrl.openConnection();
        } catch (IOException e) {
            e.printStackTrace();

            return "";
        }

        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return null;
            }
            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while ((bytesRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, bytesRead);
            }
            out.close();
            return new String(out.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            connection.disconnect();
        }

        return "";
    }



    void parseItems(XmlPullParser parser)
            throws XmlPullParserException, IOException {
        int eventType = parser.next();
        while (parser.next() != XmlPullParser.END_DOCUMENT) {
            if (parser.next() == XmlPullParser.START_TAG &&
                    "item".equals(parser.getName())) {

                String title = null, content = null, imageUrl = null, preview = null;

                while (parser.next() != XmlPullParser.END_TAG) {
                    if (parser.getEventType() != XmlPullParser.START_TAG) {
                        continue;
                    }
                    String name = parser.getName();

                    if (name.equals("title")) {
                        title = readField(parser, "title");
                    } else if (name.equals("clanak")) {
                        content = readField(parser, "clanak");
                    } else if(name.equals("image")) {
                        imageUrl = readField(parser, "image").replace("t1_", "");
                    } else if(name.equals("uvod")){
                        preview = readField(parser, "uvod");
                    } else{
                        skip(parser);
                    }
                }
                articles.add(new Article(title, content, false, preview, imageUrl));
            }

        }
    }

    private String readField(XmlPullParser parser, String field) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, null, field);
        String title = readText(parser);
        parser.require(XmlPullParser.END_TAG, null, field);
        return title;
    }

    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }

    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }


}
