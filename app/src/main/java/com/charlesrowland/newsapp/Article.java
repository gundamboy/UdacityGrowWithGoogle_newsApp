package com.charlesrowland.newsapp;

import android.graphics.Bitmap;

public class Article {
    // webPublicationDate
    private String mArticlePublicationDate;

    // webTitle
    private String mArticleTitle;

    // webUrl
    private String mArticleUrl;

    // tags[webTitle]
    private String mArticleAuthor;

    // fields[thumbnail]
    private Bitmap mArticleThumbnail;

    // determines the image to use. either from the api or from the drawable folder
    private boolean mHasThumbnail;

    /**
     * Constructs a new {@link Article} object
     *
     * @param mArticlePublicationDate    date the article was published
     * @param mArticleTitle              title of the article
     * @param mArticleUrl                url of the article
     * @param mArticleAuthor             the author of the article
     * @param mHasThumbnail              true or false.
     * @param mArticleThumbnail          url of the thumbnail image associated with the article
     */
    public Article(String mArticlePublicationDate, String mArticleTitle, String mArticleUrl, String mArticleAuthor, boolean mHasThumbnail, Bitmap mArticleThumbnail) {
        this.mArticlePublicationDate = mArticlePublicationDate;
        this.mArticleTitle = mArticleTitle;
        this.mArticleUrl = mArticleUrl;
        this.mArticleAuthor = mArticleAuthor;
        this.mArticleThumbnail = mArticleThumbnail;
    }
    /**
     * Constructs a new {@link Article} object
     *
     * @param mArticlePublicationDate    date the article was published
     * @param mArticleTitle              title of the article
     * @param mArticleUrl                url of the article
     * @param mArticleAuthor             the author of the article
     * @param mHasThumbnail              true or false.
     */
    public Article(String mArticlePublicationDate, String mArticleTitle, String mArticleUrl, String mArticleAuthor, boolean mHasThumbnail) {
        this.mArticlePublicationDate = mArticlePublicationDate;
        this.mArticleTitle = mArticleTitle;
        this.mArticleUrl = mArticleUrl;
        this.mArticleAuthor = mArticleAuthor;
        this.mHasThumbnail = mHasThumbnail;
    }

    public String getmArticlePublicationDate() {
        return mArticlePublicationDate;
    }

    public String getmArticleTitle() {
        return mArticleTitle;
    }

    public String getmArticleUrl() {
        return mArticleUrl;
    }

    public String getmArticleAuthor() {
        return mArticleAuthor;
    }

    public Bitmap getmArticleThumbnail() {
        return mArticleThumbnail;
    }

    public boolean getmHasThumbnail() {
        return mHasThumbnail;
    }
}
