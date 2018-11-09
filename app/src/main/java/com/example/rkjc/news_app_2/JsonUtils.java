package com.example.rkjc.news_app_2;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;
import java.util.ArrayList;

public class JsonUtils {

    // Parses JSON data.
    public static ArrayList<NewsItem> parseNews(String JSONString){

        ArrayList<NewsItem> newsItems = new ArrayList<>();

        try {
            JSONObject results = new JSONObject(JSONString);
            JSONArray articles = results.getJSONArray("articles");

            for(int i = 0; i < articles.length(); i++){
                JSONObject article = articles.getJSONObject(i);

                String author = article.getString("author");
                String title = article.getString("title");
                String description = article.getString("description");
                String url = article.getString("url");
                String urlImage = article.getString("urlToImage");
                String publishedAt = article.getString("publishedAt");

                newsItems.add(new NewsItem(author, title, description, url, urlImage, publishedAt));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return newsItems;
    }


}


