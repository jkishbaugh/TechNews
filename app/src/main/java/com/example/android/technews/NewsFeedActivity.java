package com.example.android.technews;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class NewsFeedActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Article>> {
    public static final String LOG_TAG = NewsFeedActivity.class.getName();
    private static final String REQUEST_URL = "https://content.guardianapis.com/search?order-by=newest&show-elements=image&show-fields=all&page-size=15&q=science%20and%20technology&api-key=43ea601e-62ba-4380-afa6-3b73b3691d5e";
    private ArticleAdapter mAdapter;
    private static final int ARTICLE_LOADER_ID = 1;
    public TextView emptyView;
    public ProgressBar spinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newsfeed);

        ListView articleListView = findViewById(R.id.list);
        emptyView = findViewById(R.id.empty_view);
        articleListView.setEmptyView(emptyView);

        spinner = findViewById(R.id.spinner);

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();

        if(networkInfo != null && networkInfo.isConnected()){
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(ARTICLE_LOADER_ID, null, this);
        }else{
            spinner.findViewById(R.id.spinner);
            spinner.setVisibility(View.GONE);
            emptyView.setText("Not connected to the Internet");
        }

        mAdapter = new ArticleAdapter(this, R.layout.article_layout, new ArrayList<Article>());

        articleListView.setAdapter(mAdapter);

        articleListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Article currentArticle = (Article) mAdapter.getItem(position);
                Uri articleUri = Uri.parse(currentArticle.getUrl());

                Intent articleIntent =  new Intent(Intent.ACTION_VIEW, articleUri);
                startActivity(articleIntent);
            }
        });


    }

    @Override
    public Loader<List<Article>> onCreateLoader(int id, Bundle args) {
        return new ArticleLoader(this, REQUEST_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<Article>> loader, List<Article> data) {
        spinner.setVisibility(View.GONE);
        emptyView.setText(R.string.no_articles);
        mAdapter.clear();

        if(data != null && !data.isEmpty()){
            mAdapter.addAll(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Article>> loader) {
        mAdapter.clear();
    }
}
