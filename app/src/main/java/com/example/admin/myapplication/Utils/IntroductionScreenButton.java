package com.example.admin.myapplication.Utils;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;

public class IntroductionScreenButton extends AppCompatButton {
    public IntroductionScreenButton(Context context) {
        super(context);
        init();
    }

    public IntroductionScreenButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public IntroductionScreenButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init()
    {
        Typeface typeface= Typeface.createFromAsset(getResources().getAssets(),"lgc_bold.ttf");
        this.setTypeface(typeface);
    }
}
