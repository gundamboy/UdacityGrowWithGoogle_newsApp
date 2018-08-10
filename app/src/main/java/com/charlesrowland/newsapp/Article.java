package com.charlesrowland.newsapp;

import android.graphics.Bitmap;

public class Article {
    // webPublicationDate
    private String mArticlePublicationDate;

    // webTitle
    private String mArticleTitle;

    // sectionName
    private String mSectionName;

    // webUrl
    private String mArticleUrl;

    // tags[webTitle]
    private String mArticleAuthor;

    // fields[thumbnail]
    private Bitmap mArticleThumbnail;

    /**
     * Constructs a new {@link Article} object
     *
     * @param mArticlePublicationDate    date the article was published
     * @param mArticleTitle              title of the article
     * @param mArticleUrl                url of the article
     * @param mArticleAuthor             the author of the article
     * @param mArticleThumbnail          url of the thumbnail image associated with the article
     */
    public Article(String mArticlePublicationDate, String mArticleTitle, String mSectionName, String mArticleUrl, String mArticleAuthor, Bitmap mArticleThumbnail) {
        this.mArticlePublicationDate = mArticlePublicationDate;
        this.mArticleTitle = mArticleTitle;
        this.mSectionName = mSectionName;
        this.mArticleUrl = mArticleUrl;
        this.mArticleAuthor = mArticleAuthor;
        this.mArticleThumbnail = mArticleThumbnail;
    }

    public String getmArticlePublicationDate() {
        return mArticlePublicationDate;
    }

    public String getmArticleTitle() {
        return mArticleTitle;
    }

    public String getmSectionName() {
        return mSectionName;
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
}
