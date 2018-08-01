package com.charlesrowland.newsapp;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    public static final String LOG_TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get the toolbar
        Toolbar toolbar = findViewById(R.id.toolbar_view);

        // use the toolbar
        setSupportActionBar(toolbar);

        // this disables the app name from going into the toolbar
        // this is important because it's my app and i dont want it there!
        // no for real though, the appname and toolbar title shouldn't occupy the same space.
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        // make an adapter for the fragment
        MainActivityPagerAdapter adapter = new MainActivityPagerAdapter(getSupportFragmentManager(), this);

        // set the viewpager for swiping side to side
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setOffscreenPageLimit(5);
        viewPager.setAdapter(adapter);

        // send the viewpager to the tabs
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);


    }
}
