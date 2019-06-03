package com.example.admin.myapplication;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Switch;

import com.example.admin.myapplication.Listners.ConfigureDarkTheme;
import com.example.admin.myapplication.Utils.CommonUtils;
import com.example.admin.myapplication.Utils.Constants;
import com.example.admin.myapplication.Utils.CustomTextViewMainBold;

public class Setting extends AppCompatActivity implements View.OnClickListener,Switch.OnCheckedChangeListener {

    RelativeLayout relativeLayout,mainrelativeLayout;
    Switch aSwitch;
    CardView cardView1,cardView2;
    CustomTextViewMainBold termsandCondition,developer,darkTheme,appname,appversion;
    public static ConfigureDarkTheme theme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        init();


    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public void init(){
        relativeLayout = (RelativeLayout)findViewById(R.id.developer_text_layout);
        relativeLayout.setOnClickListener(this);

        cardView2 = (CardView)findViewById(R.id.card_one_setting);
        cardView1 = (CardView)findViewById(R.id.card_two_setting);

        cardView1.setRadius(10);
        cardView2.setRadius(10);


        appname=(CustomTextViewMainBold)findViewById(R.id.setting_appname);
        appversion=(CustomTextViewMainBold)findViewById(R.id.setting_aapversion);

        mainrelativeLayout = (RelativeLayout) findViewById(R.id.setting_relativeLayout);

        termsandCondition = (CustomTextViewMainBold)findViewById(R.id.settings_termsandconditions_text);
        darkTheme = (CustomTextViewMainBold)findViewById(R.id.setting_darkTheme_text);
        developer = (CustomTextViewMainBold)findViewById(R.id.settings_developer_text);

        aSwitch = (Switch)findViewById(R.id.themeSwitch);
        aSwitch.setOnCheckedChangeListener(this);

        applyThemeToUI();
    }

    @Override
    public void onClick(View v)
    {
        switch(v.getId())
        {
            case R.id.developer_text_layout:
                Intent intent = new Intent(this,DeveloperActivity.class);
                startActivity(intent);
                break;
        }
    }


    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if(b)
        {
            CommonUtils.configureDarkTheme(this,b);
            setDarkTheme();
            theme.isDark(b);
        }
        else
        {
            CommonUtils.configureDarkTheme(this,b);
            setLightTheme();
            theme.isDark(b);
        }


    }

    public void setDarkTheme()
    {
        mainrelativeLayout.setBackgroundColor(Color.parseColor(Constants.MATERIAL_BLACK));
        cardView1.setBackgroundColor(Color.parseColor(Constants.TOOL_BAR_COLOR_DARK));
        cardView1.setBackground(getResources().getDrawable(R.drawable.setting_card_dark));
        cardView1.setRadius(10);
        cardView2.setBackground(getResources().getDrawable(R.drawable.setting_card_dark));
        cardView2.setRadius(10);
        appversion.setTextColor(Color.parseColor(Constants.MATERIAL_GGREY));
        appname.setTextColor(Color.parseColor(Constants.MATERIAL_GGREY));
        termsandCondition.setTextColor(Color.parseColor(Constants.MATERIAL_GGREY));
        developer.setTextColor(Color.parseColor(Constants.MATERIAL_GGREY));
        darkTheme.setTextColor(Color.parseColor(Constants.MATERIAL_GGREY));
    }

    public void setLightTheme(){
        mainrelativeLayout.setBackgroundColor(Color.WHITE);
        cardView1.setBackground(getResources().getDrawable(R.drawable.setting_card_light));
        cardView1.setRadius(10);
        cardView2.setBackground(getResources().getDrawable(R.drawable.setting_card_light));
        cardView2.setRadius(10);
        appversion.setTextColor(Color.parseColor(Constants.MATERIAL_BLACK));
        appname.setTextColor(Color.parseColor(Constants.MATERIAL_BLACK));
        termsandCondition.setTextColor(Color.parseColor(Constants.MATERIAL_BLACK));
        developer.setTextColor(Color.parseColor(Constants.MATERIAL_BLACK));
        darkTheme.setTextColor(Color.parseColor(Constants.MATERIAL_BLACK));
    }



    public void applyThemeToUI(){
        if(CommonUtils.getThemePreference(this))
        {
            setDarkTheme();
            aSwitch.setChecked(true);
        }
        else
        {
            setLightTheme();
            aSwitch.setChecked(false);
        }
    }



}
