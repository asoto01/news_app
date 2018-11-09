package com.example.rkjc.news_app_2;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {

    // Full URL: https://newsapi.org/v1/articles?source=the-next-web&sortBy=latest&apiKey=0f2e5c1c3ad84c37bbc9e891fa551e42
    final static String BASE_URL = "https://newsapi.org/v1/articles";
    final static String PARAM_SOURCE = "source";
    final static String SOURCE = "the-next-web";
    final static String PARAM_SORT = "SortBy";
    final static String SORT = "latest";
    final static String PARAM_API = "apiKey";
    final static String API = "0f2e5c1c3ad84c37bbc9e891fa551e42";


    /**
     * Builds the URL used to query newsapi .
     *
     * @param newsSearchQuery The keyword that will be queried for.
     * @return The URL to use to query the news server.
     */
    public static URL buildUrl(String newsSearchQuery) {
        // Uri built is currently not dependent on newsSearchQuery
        Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                .appendQueryParameter(PARAM_SOURCE, SOURCE)
                .appendQueryParameter(PARAM_SORT, SORT)
                .appendQueryParameter(PARAM_API, API)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    // Code to get response from http url; code found in snippet of HW1.
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }


}
