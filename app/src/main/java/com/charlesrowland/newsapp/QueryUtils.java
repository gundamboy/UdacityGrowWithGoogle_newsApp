package com.charlesrowland.newsapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public final class QueryUtils {
    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    // empty constructor. we're calling methods here, not baking a cake.
    private QueryUtils() { }

    /**
     * Quesries the Guardians API and returns a list of {@link Article} objects
     *
     * @param requestUrl api url
     * @return list of articles
     */
    public static List<Article> fetchArticleData(String requestUrl) {
        // create URL object. you can't surf the web without one!
        URL url = createUrl(requestUrl);

        // do an HTTP request and get some JSON back. it's null at first because that's how we roll.
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        // get relevant data from the json response and make a list. lists are cool.
        List<Article> articles = extractFeatureDataFromJson(jsonResponse);

        // return the list of articles.
        return articles;
    }

    /**
     *
     * @param stringUrl the url string
     * @return a URL object
     */
    private static URL createUrl(String stringUrl) {
        // not much to explain here. its building a url, which is different from say, putting on shoes.
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }

    /**
     *
     * @param url the url for the request
     * @return returns the json response from the http request
     * @throws IOException only if the stream has issues
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early. why would you send a faulty url. cheeky internet i tell ya.
        if (url == null) {
            return jsonResponse;
        }

        // set up the connection
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200), then read the input stream and parse the response.
            // if the request was bad though... ah nuts.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the News Article JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown. Basically, we are covering out butts here.
                inputStream.close();
            }
        }

        // return all the things!
        return jsonResponse;
    }

    /**
     *
     * @param inputStream an InputStream object
     * @return returns the json response as a string
     * @throws IOException only if the stream has a problem
     */
    private static String readFromStream(InputStream inputStream) throws IOException {

        // StringBuilder.... builds a string!
        StringBuilder output = new StringBuilder();

        // we don't want to do anything if the inputStream is null. that would be very bad.
        if (inputStream != null) {
            // grab the stream
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));

            // BufferedReader speeds reading up and its efficient too.
            BufferedReader reader = new BufferedReader(inputStreamReader);

            // reads the line....
            String line = reader.readLine();

            // only do stuff if there is a line.
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }

        // return all the things!
        return output.toString();
    }

    /**
     *
     * @param articleJSON this is a string of json
     * @return returns a {@link Article} object
     */
    private static List<Article> extractFeatureDataFromJson(String articleJSON) {
        // if the JSON string is empty or null we return early because wtf
        // TextUtils.isEmpty is a short cut. it checks for both null and empty strings. SWEET!
        if (TextUtils.isEmpty(articleJSON)) {
            return null;
        }

        // make an empty ArrayList of Article objects so we can get the party started
        List<Article> articles = new ArrayList<>();

        // Try Catch block time. If the JSON is formatted crappy a JSONException is thrown.
        // buckle your seat belts, here we go.
        try {
            // Create a JSONObject from the JSON response string
            JSONObject baseJsonResponse = new JSONObject(articleJSON);

            // grab the key called 'response' from the json object. this is where it all starts
            JSONObject rootObject = baseJsonResponse.getJSONObject("response");

            // we have the rootObject but what we want is the stuff under it. get the key 'results' and lets party
            JSONArray resultsArray = rootObject.getJSONArray("results");

            // loop through all ths results and create a new Article object to shove into the ArrayList
            for (int i = 0; i < resultsArray.length(); i++) {
                // grab the info at the current position. i is the position.
                JSONObject currentResult = resultsArray.getJSONObject(i);

                // it's possible the fields array can come back without an author or an image url.
                // set up some defaults to prevent a crash. users would be pretty mad if there's a crash.
                String articleAuthor = "Unknown Contributor";
                String articleImage = "";

                // grab all the stuff we need for our Article object
                String publishDate = currentResult.getString("webPublicationDate");
                String articleTitle = currentResult.getString("webTitle");
                String articleUrl = currentResult.getString("webUrl");
                String articleSection = currentResult.getString("sectionName");

                if (!currentResult.isNull("fields")) {
                    JSONObject fields = currentResult.getJSONObject("fields");
                    if (fields != null) {
                        if (fields.has("byline")) {
                            articleAuthor = fields.getString("byline");
                        }

                        if (fields.has("thumbnail") && !TextUtils.isEmpty(fields.getString("thumbnail"))) {
                            articleImage = fields.getString("thumbnail");
                        }
                    }
                }


                // add a new Article based on the thumbnail image
                articles.add(new Article(publishDate, articleTitle, articleSection, articleUrl, articleAuthor, downloadArticleImage(articleImage)));
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing the article JSON results", e);
        }

        // return all the things!
        return articles;
    }

    /**
     * Get an image from the url and return a {@link Bitmap} object
     *
     * @param url string
     * @return a bitmap
     */
    private static Bitmap downloadArticleImage(String url) {
        // null at first because in case the url is. this way it will toss out the exception

        Bitmap image = null;

        if (url != null && !url.isEmpty()) {
            // try to do all the things

            try {
                InputStream inputStream = new URL(url).openStream();
                image = BitmapFactory.decodeStream(inputStream);
            } catch (Exception e) {
                Log.e(LOG_TAG, e.getMessage());
            }
        }

        // return image we snatched liked h4x3rs
        return image;
    }
}
