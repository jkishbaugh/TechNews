package com.example.android.technews;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.util.List;

public class ArticleLoader extends AsyncTaskLoader {
    private static final String LOG_TAG =ArticleLoader.class.getSimpleName();
    private String mUrl;

    public ArticleLoader(Context context, String url) {
        super(context);
        mUrl = url;

    }

    @Override
    protected void onStartLoading() {
        forceLoad();
        Log.v(LOG_TAG, "on start loading");
    }
    @Override
    public List<Article> loadInBackground() {
       if(mUrl == null){
           return null;
       }
        List<Article> result = QueryUtils.extractArticles(mUrl);
        return result;
    }
}
