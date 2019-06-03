package com.example.admin.myapplication;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.admin.myapplication.Utils.CommonUtils;
import com.example.admin.myapplication.Utils.Constants;
import com.example.admin.myapplication.Utils.CustomTextViewMain;
import com.example.admin.myapplication.Utils.CustomTextViewMainBold;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class DeveloperActivity extends AppCompatActivity implements View.OnClickListener {

    LinearLayout profile_container_developer;
    CoordinatorLayout developerCoordinatorLayout;
    CustomTextViewMain bio,Name;
    Button donate_button;
    CardView developer_card;
    ImageView developer_image;




    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developer);
        CommonUtils.setFullScreen(getWindow());
        CommonUtils.setTransLucentNavigationBar(getWindow());
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void init(){
     profile_container_developer = (LinearLayout)findViewById(R.id.profile_container_developer);
     developerCoordinatorLayout = (CoordinatorLayout)findViewById(R.id.coordinator_layout_developer);
     bio = (CustomTextViewMain)findViewById(R.id.bio_developer);
     Name = (CustomTextViewMain)findViewById(R.id.developer_name);
     donate_button = (Button) findViewById(R.id.donate_developer);
     developer_card = (CardView)findViewById(R.id.developer_card);
     developer_image = (ImageView)findViewById(R.id.user_image_developer);
     setDark(CommonUtils.getThemePreference(this));

     donate_button.setOnClickListener(this);

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void setDark(boolean isDark)
    {
        if(isDark)
        {
            developerCoordinatorLayout.setBackgroundColor(Color.parseColor(Constants.MATERIAL_BLACK));
            //developer_card.setBackgroundColor(Color.parseColor(Constants.TOOL_BAR_COLOR_DARK));
            bio.setTextColor(Color.parseColor(Constants.MATERIAL_GGREY));
            Name.setTextColor(Color.parseColor(Constants.MATERIAL_GGREY));
            donate_button.setTextColor(Color.parseColor(Constants.MATERIAL_GGREY));
            donate_button.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(Constants.MATERIAL_GGREY)));
            profile_container_developer.setBackground(getDrawable(R.drawable.borderlines_dark));
            developer_image.setColorFilter(Color.parseColor(Constants.MATERIAL_GGREY));
        }
        else{
            developerCoordinatorLayout.setBackgroundColor(Color.WHITE);
            //developer_card.setBackgroundColor(Color.WHITE);
     //       bio.setTextColor(Color.parseColor(Constants.MATERIAL_BLACK));
            Name.setTextColor(Color.parseColor(Constants.MATERIAL_BLACK));
            donate_button.setTextColor(Color.parseColor(Constants.MATERIAL_BLACK));
            donate_button.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(Constants.MATERIAL_BLACK)));
            profile_container_developer.setBackground(getDrawable(R.drawable.borderlines));
            developer_image.setColorFilter(Color.parseColor(Constants.MATERIAL_BLACK));
        }

    }


    @Override
    public void onClick(View view) {
        switch(view.getId())
        {
            case R.id.donate_developer:
                CommonUtils.myCustomAlertDialog(this,"Under Development","this feature is under development");
                break;
        }
    }
}
