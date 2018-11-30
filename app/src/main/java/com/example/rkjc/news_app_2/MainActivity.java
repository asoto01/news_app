package com.example.rkjc.news_app_2;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;


import com.example.rkjc.news_app_2.models.NewsItem;
import com.example.rkjc.news_app_2.models.NewsItemViewModel;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NewsRecyclerViewAdapter.ListItemClickListener {

    private Context mContext;
    private ArrayList<NewsItem> mNews;
    private NewsRecyclerViewAdapter mNewsAdapter;
    private RecyclerView mRecyclerView;
    private NewsItemViewModel mNewsItemViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.news_recyclerview);
        mContext = this;
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mNewsItemViewModel = ViewModelProviders.of(this).get(NewsItemViewModel.class);
        mNewsItemViewModel.getAllNewsItems().observe(this, new Observer<List<NewsItem>>() {
            @Override
            public void onChanged(@Nullable final List<NewsItem> newsItems) {
                mNewsAdapter = new NewsRecyclerViewAdapter(mContext, new ArrayList<NewsItem>(newsItems));
                mRecyclerView.setAdapter(mNewsAdapter);
                mRecyclerView.setLayoutManager(layoutManager);
            }
        });
        mRecyclerView.setHasFixedSize(true);

        makeNewsSearchQuery();

//        ScheduleUtilities.scheduleRefresh(this);



    }

    private void makeNewsSearchQuery(){
        URL newsSearchUrl = NetworkUtils.buildUrl("");
        new NewsQueryTask().execute(newsSearchUrl);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemThatWasClickedId = item.getItemId();
        if (itemThatWasClickedId == R.id.action_search) {
//            makeNewsSearchQuery();
            mNewsItemViewModel.syncNews();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {

        Uri webpage = Uri.parse(mNews.get(clickedItemIndex).getUrl());
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);

        if(intent.resolveActivity(getPackageManager()) != null){
            startActivity(intent);
        }
    }

    public class NewsQueryTask extends AsyncTask<URL, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(URL... urls) {
            URL searchUrl = urls[0];
            String newsSearchResults = null;
            try {
                newsSearchResults = NetworkUtils.getResponseFromHttpUrl(searchUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return newsSearchResults;
        }

        @Override
        protected void onPostExecute(String newsSearchResults) {
            super.onPostExecute(newsSearchResults);
            mNews = JsonUtils.parseNews(newsSearchResults);

            mNewsAdapter = new NewsRecyclerViewAdapter( mNews, MainActivity.this);
            mRecyclerView.setAdapter(mNewsAdapter);

        }
    }



}
