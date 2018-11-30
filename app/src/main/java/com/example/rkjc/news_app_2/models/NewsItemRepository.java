package com.example.rkjc.news_app_2.models;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.util.Log;

import com.example.rkjc.news_app_2.JsonUtils;
import com.example.rkjc.news_app_2.NetworkUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.example.rkjc.news_app_2.NetworkUtils.buildUrl;

public class NewsItemRepository {
    private static NewsItemDao mNewsItemDao;
    private LiveData<List<NewsItem>> mAllNewsItems;

    public NewsItemRepository(Application application) {
        NewsItemDatabase db = NewsItemDatabase.getDatabase(application.getApplicationContext());
        mNewsItemDao = db.NewsItemDao();
        mAllNewsItems = mNewsItemDao.loadAllNewsItems();
    }

    LiveData<List<NewsItem>> getAllNewsItems() { return mAllNewsItems; }

    public static void syncNews() { new SyncNewsTask(mNewsItemDao).execute(buildUrl("")); }

    public static class SyncNewsTask extends AsyncTask<URL, Void, String> {
        private NewsItemDao mAsyncTaskDao;

        SyncNewsTask(NewsItemDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mAsyncTaskDao.clearAll();
        }

        @Override
        protected String doInBackground(URL... urls) {
            String newsSearchResults = "";
            try {
                newsSearchResults = NetworkUtils.getResponseFromHttpUrl(urls[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return newsSearchResults;
        }

        @Override
        protected void onPostExecute(String s) {
            Log.d("mycode", s);
            super.onPostExecute(s);
            ArrayList<NewsItem> news = JsonUtils.parseNews(s);
            mAsyncTaskDao.insert(news);
        }
    }


}
