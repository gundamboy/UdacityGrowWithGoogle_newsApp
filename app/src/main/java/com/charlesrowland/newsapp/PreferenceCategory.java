package com.charlesrowland.newsapp;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

public class PreferenceCategory extends android.preference.PreferenceCategory{

    public PreferenceCategory(Context context){
        super(context);
    }

    public PreferenceCategory(Context context, AttributeSet attrs){
        super(context, attrs);
    }

    public PreferenceCategory(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onBindView(View view) {
        super.onBindView(view);
        TextView titleView = (TextView) view.findViewById(android.R.id.title);
        int getColorId = ContextCompat.getColor(getContext(), R.color.textColorPrimary);
        titleView.setTextColor(getColorId);
        titleView.setTextSize(20);
    }

}
