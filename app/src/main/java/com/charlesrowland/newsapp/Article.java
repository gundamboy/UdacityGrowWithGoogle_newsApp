package com.charlesrowland.newsapp;

import android.graphics.Bitmap;

public class Article {
    // webPublicationDate
    private String mArticlePublicationDate;

    // webTitle
    private String mArticleTitle;

    // webUrl
    private String mArticleUrl;

    // pillarName
    private String mArticleCategory;

    // tags[webTitle]
    private String mArticleAuthor;

    // fields[thumbnail]
    private Bitmap mArticleThumbnail;

    // default image in case an article does not have an image
    private int mDefaultImageId;

    /**
     * Constructs a new {@link Article} object
     *
     * @param mArticlePublicationDate    date the article was published
     * @param mArticleTitle              title of the article
     * @param mArticleUrl                url of the article
     * @param mArticleCategory           the category the article is in
     * @param mArticleAuthor             the author of the article
     * @param mArticleThumbnail          url of the thumbnail image associated with the article
     */
    public Article(String mArticlePublicationDate, String mArticleTitle, String mArticleUrl, String mArticleCategory, String mArticleAuthor, Bitmap mArticleThumbnail) {
        this.mArticlePublicationDate = mArticlePublicationDate;
        this.mArticleTitle = mArticleTitle;
        this.mArticleUrl = mArticleUrl;
        this.mArticleCategory = mArticleCategory;
        this.mArticleAuthor = mArticleAuthor;
        this.mArticleThumbnail = mArticleThumbnail;
    }
    /**
     * Constructs a new {@link Article} object
     *
     * @param mArticlePublicationDate    date the article was published
     * @param mArticleTitle              title of the article
     * @param mArticleUrl                url of the article
     * @param mArticleCategory           the category the article is in
     * @param mArticleAuthor             the author of the article
     * @param mDefaultImageId            default image if there is no thumbnail to display
     */
    public Article(String mArticlePublicationDate, String mArticleTitle, String mArticleUrl, String mArticleCategory, String mArticleAuthor, int mDefaultImageId) {
        this.mArticlePublicationDate = mArticlePublicationDate;
        this.mArticleTitle = mArticleTitle;
        this.mArticleUrl = mArticleUrl;
        this.mArticleCategory = mArticleCategory;
        this.mArticleAuthor = mArticleAuthor;
        this.mDefaultImageId = mDefaultImageId;
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

    public String getmArticleCategory() {
        return mArticleCategory;
    }

    public String getmArticleAuthor() {
        return mArticleAuthor;
    }

    public Bitmap getmArticleThumbnail() {
        return mArticleThumbnail;
    }

    public int getmDefaultImageId() {
        return mDefaultImageId;
    }
}
