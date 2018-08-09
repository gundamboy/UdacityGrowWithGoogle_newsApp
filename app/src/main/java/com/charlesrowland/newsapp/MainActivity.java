package com.charlesrowland.newsapp;

import android.app.ActionBar;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {
    public static final String LOG_TAG = MainActivity.class.getSimpleName();
    private TabLayout tabLayout;
    public  SharedPreferences sharedPrefs;
    private String SELECTED_TAB = "selectedtab";
    int selectedTabPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // appbar stuff. i am using a custom layout with a textview for the name
        // so i can center the text. setElevation(0) removes the dropshadow.
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout);
        getSupportActionBar().setElevation(0);

        // shared preferences stuff
        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        // make an adapter for the fragment
        MainActivityPagerAdapter adapter = new MainActivityPagerAdapter(getSupportFragmentManager(), sharedPrefs, this);

        // set the viewpager for swiping side to side
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setOffscreenPageLimit(5);
        viewPager.setAdapter(adapter);

        // send the viewpager to the tabs
        tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);



        // get the tab index when selecting the tab
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                selectedTabPosition = tab.getPosition();
                sharedPrefs.edit().putInt(SELECTED_TAB, selectedTabPosition).apply();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        // sets the current tab back to what the user was on before they left for the settings
        TabLayout.Tab tab = tabLayout.getTabAt(sharedPrefs.getInt(SELECTED_TAB,0));
        tab.select();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // inflate the options menu
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }
}
