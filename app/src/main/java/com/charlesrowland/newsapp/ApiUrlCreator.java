package com.charlesrowland.newsapp;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * The point of this class is that there are multiple tabs. it's more efficient to build
 * the url for the api using a class instead of repeating it multiple times.
 */
public final class ApiUrlCreator {
    private static final String LOG_TAG = ApiUrlCreator.class.getSimpleName();

    // empty constructor. non shall pass.
    private ApiUrlCreator() {}

    // strings for the sections/tags that will be in each tab. these are constants. i promise i am not shouting.
    private static final String SHOW_FIELDS = "show-fields=thumbnail,byline";
    private static final String TAG_PREFIX = "&tag=";
    private static final String ORDERBY_PREFIX = "&order-by=";
    public static final String SECTION_MUSIC = "music?";
    public static final String SECTION_GAMES = "games?";
    public static final String SECTION_TECH = "technology?";
    public static final String SECTION_SCIENCE = "science?";
    public static final String SECTION_BOOKS = "books?";


    // the basic part of the url that never changes
    // this grabs the thumbnail and byline by default because we always want that
    //private static final String URL_BASE = "https://content.guardianapis.com/search";
    private static final String URL_BASE = "https://content.guardianapis.com/";

    // get my super secret api key
    private static final String API_KEY = BuildConfig.ApiKey;

    /**
     *
     * @param section the section constant
     * @return the url string
     */
    public static String buildUrl(String section, String tag, String orderBy) {
        // need a StringBuilder so we can... build the string! WOO
        StringBuilder stringBuilder = new StringBuilder();

        // start with the base
        stringBuilder.append(URL_BASE);

        // check for a null section. hey, it could happen..
        if (section != null) {
            stringBuilder.append(section);

            // add the fields
            stringBuilder.append(SHOW_FIELDS);

            if (tag != null) {
                stringBuilder.append(TAG_PREFIX);
                stringBuilder.append(tag);
            }

            if (orderBy != null) {
                stringBuilder.append(ORDERBY_PREFIX);
                stringBuilder.append(orderBy);
            }

            // add the api key. without this, we get nothing.
            stringBuilder.append(API_KEY);
        }

        // url is done! return it.
        return stringBuilder.toString();
    }
}
