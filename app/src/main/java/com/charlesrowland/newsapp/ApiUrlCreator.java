package com.charlesrowland.newsapp;

/**
 * The point of this class is that there are multiple tabs. it's more efficient to build
 * the url for the api using a class instead of repeating it multiple times.
 */
public final class ApiUrlCreator {
    private static final String LOG_TAG = ApiUrlCreator.class.getSimpleName();

    // empty constructor. non shall pass.
    private ApiUrlCreator() {}

    // strings for the sections/tags that will be in each tab. these are constants. i promise i am not shouting.
    private static final String SECTION_MUSIC_METAL = "&section=music&tag=music/metal";
    private static final String SECTION_GAMES = "&section=games";
    private static final String SECTION_TECH = "&section=technology";
    private static final String SECTION_SCIENCE = "&section=science";
    private static final String SECTION_BOOKS_COMICS = "&section=books&tag=books/comics";

    // the basic part of the url that never changes
    // this grabs the thumbnail and byline by default because we always want that
    private static final String URL_BASE = "https://content.guardianapis.com/search?show-fields=thumbnail,byline";

    // get my super secret api key
    private static final String API_KEY = BuildConfig.ApiKey;

    /**
     *
     * @param section the section constant
     * @return the url string
     */
    public static String buildUrl(String section) {
        // need a StringBuilder so we can... build the string! WOO
        StringBuilder stringBuilder = new StringBuilder();

        // start with the base
        stringBuilder.append(URL_BASE);

        // check for a null section. hey, it could happen..
        if (section != null) {
            stringBuilder.append(section);
        }

        // add the api key. without this, we get nothing.
        stringBuilder.append(API_KEY);

        // url is done! return it.
        return stringBuilder.toString();
    }

}
