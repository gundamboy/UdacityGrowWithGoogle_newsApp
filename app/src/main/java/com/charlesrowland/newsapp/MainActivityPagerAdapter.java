package com.charlesrowland.newsapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

public class MainActivityPagerAdapter extends FragmentPagerAdapter {
    public static final String LOG_TAG = MainActivityPagerAdapter.class.getSimpleName();

    // context
    Context context;
    SharedPreferences sharedPrefs;

    // constructor.
    public MainActivityPagerAdapter(FragmentManager fm, SharedPreferences sharedPrefs, Context context) {
        super(fm);
        this.context = context;
        this.sharedPrefs = sharedPrefs;
    }

    // get the titles for the tabs=
    @Override
    public CharSequence getPageTitle(int position) {
        // switch condition because if statements are lame
        switch (position) {
            case 0:
                return context.getString(R.string.section_title_music);
            case 1:
                return context.getString(R.string.section_title_games);
            case 2:
                return context.getString(R.string.section_title_books);
            case 3:
                return context.getString(R.string.section_title_tech);
            case 4:
                return context.getString(R.string.section_title_science);
            default:
                return context.getString(R.string.section_title_default);
        }
    }

    @Override
    public Fragment getItem(int position) {
        // grabs the current position and sends an argument bundle to the fragment
        // so it can display the right stuff.

        String orderBy  = sharedPrefs.getString(context.getString(R.string.settings_order_by_key), context.getString(R.string.settings_order_by_default));
        String musicTag  = sharedPrefs.getString(context.getString(R.string.settings_music_choices_key), context.getString(R.string.settings_music_default_value));
        String booksTag  = sharedPrefs.getString(context.getString(R.string.settings_books_choices_key), context.getString(R.string.settings_books_default_value));
        String gamesTag  = sharedPrefs.getString(context.getString(R.string.settings_games_key), context.getString(R.string.settings_games_default_value));
        String technologyTag  = sharedPrefs.getString(context.getString(R.string.settings_technology_key), context.getString(R.string.settings_technology_default_value));
        String scienceTag  = sharedPrefs.getString(context.getString(R.string.settings_science_key), context.getString(R.string.settings_science_default_value));

        Bundle bundle = new Bundle();

       switch (position) {
            case 0:
                bundle.putString("url", ApiUrlCreator.buildUrl(ApiUrlCreator.SECTION_MUSIC, musicTag, orderBy));
                break;
            case 1:
                bundle.putString("url", ApiUrlCreator.buildUrl(ApiUrlCreator.SECTION_GAMES, gamesTag, orderBy));
                break;
            case 2:
                bundle.putString("url", ApiUrlCreator.buildUrl(ApiUrlCreator.SECTION_BOOKS, booksTag, orderBy));
                break;
            case 3:
                bundle.putString("url", ApiUrlCreator.buildUrl(ApiUrlCreator.SECTION_TECH, technologyTag, orderBy));
                break;
            case 4:
                bundle.putString("url", ApiUrlCreator.buildUrl(ApiUrlCreator.SECTION_SCIENCE, scienceTag, orderBy));
                break;
            default:
                // in the instance something wacky happens send the url over as null
                bundle.putString("url", ApiUrlCreator.buildUrl(null, null, null));
       }

        // fragment time. i really don't like fragments......
       NewsSectionFragment newsSectionFragment = new NewsSectionFragment();
       newsSectionFragment.setArguments(bundle);

       // return the fragment
       return newsSectionFragment;

    }

    @Override
    public int getCount() {
        return 5;
    }
}
