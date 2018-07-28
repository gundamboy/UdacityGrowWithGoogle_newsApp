package com.charlesrowland.newsapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_tests);

        // get the toolbar
        Toolbar toolbar = findViewById(R.id.toolbar_view);

        // use the toolbar
        setSupportActionBar(toolbar);
    }
}
