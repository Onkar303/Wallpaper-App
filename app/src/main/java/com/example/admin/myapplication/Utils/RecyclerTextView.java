package com.example.admin.myapplication.Utils;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

public class RecyclerTextView extends TextView {

    public RecyclerTextView(Context context) {
        super(context);
        initWithFont();
    }

    public RecyclerTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initWithFont();
    }


    public RecyclerTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initWithFont();
    }

    public RecyclerTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initWithFont();
    }



    public void initWithFont()
    {
        Typeface typeface=Typeface.createFromAsset(getResources().getAssets(),"lgc.ttf");
        this.setTypeface(typeface);

    }
}
