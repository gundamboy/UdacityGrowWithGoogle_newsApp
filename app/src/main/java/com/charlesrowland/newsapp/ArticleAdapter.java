package com.charlesrowland.newsapp;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v4.content.res.ResourcesCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ArticleAdapter extends ArrayAdapter<Article> {
    public static final String LOG_TAG = ArticleAdapter.class.getSimpleName();

    public ArticleAdapter(Context context, List<Article> articles) {
        super(context, 0, articles);
    }

    /**
     * Returns a list item view that displays information about the article at the given position
     *
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // check to see if we are reusing an existing view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.article_list_item, parent, false);
        }

        // get the current article that got passed in
        Article currentArticle = getItem(position);

        // get the imageview
        ImageView articleImage = listItemView.findViewById(R.id.article_image);

        // we need to check to see what image we are using. if hasThumbnail comes back true its the api image
        // if its false, use a local resource.
        if (currentArticle.getmArticleThumbnail() != null) {
            articleImage.setImageBitmap(currentArticle.getmArticleThumbnail());
        } else {
            articleImage.setImageDrawable(ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.gaurdian_logo_green, null));
        }

        // credit where credit is due. Get the author
        TextView author = listItemView.findViewById(R.id.author);
        author.setText(currentArticle.getmArticleAuthor());

        // set the date
//        TextView articleDate = listItemView.findViewById(R.id.date);
//        articleDate.setText(formatDate(currentArticle.getmArticlePublicationDate()));

        // article title
        TextView articleTitle = listItemView.findViewById(R.id.article_title);
        articleTitle.setText(currentArticle.getmArticleTitle());

        // article section
        //TextView articleSection = listItemView.findViewById(R.id.section);
        TextView articleSectionDate = listItemView.findViewById(R.id.section_date);
        String sectionName = currentArticle.getmSectionName();
        String articleDate = formatDate(currentArticle.getmArticlePublicationDate());
        String fullSectionDate = "In " + sectionName + " on " + articleDate;

        articleSectionDate.setText(fullSectionDate);

        // lets make some stuff clickable shall we
        ArrayList<View> viewsArray = new ArrayList<>();
        viewsArray.add(author);
        viewsArray.add(articleSectionDate);
        viewsArray.add(articleTitle);
        viewsArray.add(articleImage);

        goToArticleWebsite(viewsArray, currentArticle.getmArticleUrl());

        // return the list item
        return listItemView;
    }

    /**
     * Return the formatted date string (i.e. "Mar 3, 1984") from a Date object.
     */
    private String formatDate(String date) {
        // the api brings in a super funky date string. this pattern matches it.
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'Z'", Locale.getDefault());

        // need a try/catch here
        try {
            // turn the date into an actual date
            Date articleDate = dateFormat.parse(date);

            // converts to date to a readable format
            dateFormat.applyPattern("LLL dd, yyyy");;
            // return the formatted date
            return dateFormat.format(articleDate);

        } catch (ParseException e) {
            // if there was a problem return null
            Log.e(LOG_TAG, "Problem formatting the date.", e);
            return null;
        }
    }

    // a generic method to make it easy to put a click listener on several views
    private void goToArticleWebsite(ArrayList<View> viewsArray, String url) {
        // the website isn't going to change, it can live outside the loop
        final Uri website = Uri.parse(url);

        // loop through the views to set click listeners on them. im mad scientisting this right now
        for(int i = 0; i < viewsArray.size(); i++) {
            View currentView = viewsArray.get(i);

            currentView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent webIntent = new Intent(Intent.ACTION_VIEW);
                    webIntent.setData(website);
                    getContext().startActivity(webIntent);
                }
            });
        }

    }
}
