package com.example.admin.myapplication.Utils;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

public class CustomTextViewMainBold extends TextView {
    public CustomTextViewMainBold(Context context) {
        super(context);
        init();
    }

    public CustomTextViewMainBold(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomTextViewMainBold(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public CustomTextViewMainBold(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public void init()
    {
        Typeface typeface=Typeface.createFromAsset(getResources().getAssets(),"lgc_bold.ttf");
        this.setTypeface(typeface);
    }
}
