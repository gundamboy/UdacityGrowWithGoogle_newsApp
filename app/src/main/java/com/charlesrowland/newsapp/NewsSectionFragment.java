package com.charlesrowland.newsapp;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass. One fragment to rule them all.
 */
public class NewsSectionFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<Article>> {

    public static final String LOG_TAG = NewsSectionFragment.class.getSimpleName();
    private static final int NEWS_LOADER_ID = 1;

    // set some views
    SwipeRefreshLayout swipeRefreshLayout;
    ListView listView;
    TextView emptyText;
    private View loadingIndicator;

    // adapter
    private ArticleAdapter mAdapter;

    // empty url construct
    private String api_url = ApiUrlCreator.buildUrl(null, null, null);

    public NewsSectionFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.article_list, container, false);

        // the url we need to query comes into the fragment as arguments
        String url = getArguments().getString("url");
        Log.v(LOG_TAG, "url: " + url);
        if (!TextUtils.isEmpty(url)) {
            api_url = url;
        }

        // grab the list view and set the list adapter
        listView = rootView.findViewById(R.id.news_list);


        // swipe view
        swipeRefreshLayout = rootView.findViewById(R.id.swipeRefreshLayout);

        // empty text view
        emptyText = rootView.findViewById(R.id.empty_view);

        // set up the swipe to refresh
        initSwipeToRefresh();

        loadingIndicator = rootView.findViewById(R.id.loading_indicator);

        // lets see here. we need to do stuff based on an internet connection
        if (!checkInterntStatus()) {
            // no internet. that sucks. show the no internet message
            loadingIndicator.setVisibility(View.GONE);
            emptyText.setText(getString(R.string.no_internet));
        } else {
            // internet works YEAH! start it up!
            getLoaderManager().initLoader(NEWS_LOADER_ID, null, this);
        }

        return rootView;
    }

    // create a loader. it's gonna spin, don't get dizzy.
    @NonNull
    @Override
    public Loader<List<Article>> onCreateLoader(int i, Bundle bundle) {
        return new ArticleLoader(getContext(), api_url);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<Article>> loader, List<Article> articles) {
        // Stop swipe refreshing animation. its annoying anyway.
        swipeRefreshLayout.setRefreshing(false);

        // goodbye error message. it was nice to know you.
        emptyText.setVisibility(View.GONE);

        // oh, what's this.. bye bye loader!
        loadingIndicator.setVisibility(View.GONE);

        // time to send our info into the ArticleAdapter so we have stuff to look at.
        if (articles != null && !articles.isEmpty()) {
            // add the articles to the adapter
            mAdapter = new ArticleAdapter(getContext(), articles);
            listView.setAdapter(mAdapter);
        } else if (!checkInterntStatus()) {
            // no internet.
            emptyText.setText(getString(R.string.no_internet));
            emptyText.setVisibility(View.VISIBLE);
        } else {
            // no articles
            emptyText.setText(getString(R.string.no_articles));
            emptyText.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<Article>> loader) {
        mAdapter.clear();
    }

    /**
     * checks the status of the users internet. hopefully it comes back true. not having internet stinks.
     *
     * @return true or false on internet status.
     */
    public boolean checkInterntStatus() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) {
            return false;
        }

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    /**
     * if there wasn't internet but now there is then start the loader WEEEEEEEE
     */
    private void restartLoader() {
        if (checkInterntStatus()) {
            swipeRefreshLayout.setRefreshing(true);
            //getLoaderManager().initLoader(NEWS_LOADER_ID, null, this);

            // we have to restart the main activity because that is where the tabs are initiated
            // and that is how i set up my feeds to load and im too lazy to redo the ENTIRE architecture.
            Intent homeIntent = new Intent(getContext(), MainActivity.class);
            startActivity(homeIntent);
        } else {
            // no internet? you get NOTHING!
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    /**
     *  used when the user pulls down to refresh the view
     */
    private void initSwipeToRefresh() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getLoaderManager().destroyLoader(NEWS_LOADER_ID);
                restartLoader();
            }
        });
    }
}