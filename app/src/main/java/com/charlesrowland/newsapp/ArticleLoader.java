package com.charlesrowland.newsapp;

import android.support.v4.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.util.List;

public class ArticleLoader extends AsyncTaskLoader<List<Article>> {
    private static final String LOG_TAG = ArticleLoader.class.getName();

    // the url we need to get
    private String url;

    // constructor
    public ArticleLoader(Context context, String url) {
        super(context);
        this.url = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Article> loadInBackground() {
        // check the url first. we don't want non of that hooligan crap.
        if (url == null) {
            return null;
        }

        // and here it. what we have been waiting for. return. that. data.
        List<Article> articles = QueryUtils.fetchArticleData(url);
        return articles;
    }
}
