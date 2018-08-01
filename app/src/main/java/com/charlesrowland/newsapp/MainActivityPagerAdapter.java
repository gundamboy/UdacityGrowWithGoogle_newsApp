package com.charlesrowland.newsapp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

public class MainActivityPagerAdapter extends FragmentPagerAdapter {
    public static final String LOG_TAG = MainActivityPagerAdapter.class.getSimpleName();

    // context
    Context context;

    // constructor.
    public MainActivityPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
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
                return context.getString(R.string.section_title_comics);
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

        Bundle bundle = new Bundle();

       switch (position) {
            case 0:
                bundle.putString("url", ApiUrlCreator.buildUrl(ApiUrlCreator.SECTION_MUSIC_METAL));
                break;
            case 1:
                bundle.putString("url", ApiUrlCreator.buildUrl(ApiUrlCreator.SECTION_GAMES));
                break;
            case 2:
                bundle.putString("url", ApiUrlCreator.buildUrl(ApiUrlCreator.SECTION_BOOKS_COMICS));
                break;
            case 3:
                bundle.putString("url", ApiUrlCreator.buildUrl(ApiUrlCreator.SECTION_TECH));
                break;
            case 4:
                bundle.putString("url", ApiUrlCreator.buildUrl(ApiUrlCreator.SECTION_SCIENCE));
                break;
            default:
                // in the instance something wacky happens send the url over as null
                bundle.putString("url", ApiUrlCreator.buildUrl(null));
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
