package com.example.admin.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.myapplication.Adapter.MyViewPagerAdapter;
import com.example.admin.myapplication.Fragments.Fragment1;
import com.example.admin.myapplication.Fragments.Fragment2;
import com.example.admin.myapplication.Fragments.Fragment3;
import com.example.admin.myapplication.Utils.CommonUtils;
import com.example.admin.myapplication.Utils.IntroductionScreenButton;

import java.util.ArrayList;
import java.util.List;

public class IntroductionActivity extends AppCompatActivity implements ViewPager.PageTransformer,View.OnClickListener,ViewPager.OnPageChangeListener {

    ViewPager viewPager;
    MyViewPagerAdapter adapter;
    List<Fragment> list;
    Toolbar toolbar;
    Button finish,previous,next;
    TabLayout tablayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isFirstTime();
        setContentView(R.layout.activity_view_pager);
        CommonUtils.setTransLucentNavigationBar(getWindow());
        CommonUtils.setFullScreen(getWindow());
        CommonUtils.setStickyNavigationBar(getWindow());
        init();
    }

    public void init() {
        tablayout=(TabLayout)findViewById(R.id.tablayout);
        viewPager = (ViewPager) findViewById(R.id.ViewPager1);
        list = new ArrayList<>();
        list.add(new Fragment1());
        list.add(new Fragment2());
        list.add(new Fragment3());
        adapter = new MyViewPagerAdapter(getSupportFragmentManager(), list, this);
        viewPager.setAdapter(adapter);

        next=(IntroductionScreenButton)findViewById(R.id.next);
        next.setOnClickListener(this);

        previous=(IntroductionScreenButton)findViewById(R.id.previous);
        previous.setOnClickListener(this);

        finish =(IntroductionScreenButton)findViewById(R.id.finish);
        finish.setOnClickListener(this);



        viewPager.setPageTransformer(false, this);
        tablayout.setupWithViewPager(viewPager);

        viewPager.addOnPageChangeListener(this);


        //
    }


    @Override
    public void transformPage(@NonNull View page, float position) {


        ImageView imageView = (ImageView) page.findViewById(R.id.image32);
        TextView textView=(TextView)page.findViewById(R.id.fragment_text);
        int pageWidth = page.getWidth();


        if (position < -1) { // [-Infinity,-1)
            // This page is way off-screen to the left.
            page.setAlpha(1);

        } else if (position <= 1) { // [-1,1]

            imageView.setTranslationX(-position * (pageWidth / 2)); //Half the normal speed


        } else { // (1,+Infinity]
            // This page is way off-screen to the right.
            page.setAlpha(1);


        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        CommonUtils.setStickyNavigationBar(getWindow());
    }


    public void isFirstTime()
    {
        SharedPreferences sharedPreferences=getSharedPreferences("AppPreference",0);
        boolean firstRun=sharedPreferences.getBoolean("FirstLaunch",true);
        if(firstRun)
        {
            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.putBoolean("FirstLaunch",false);
            editor.commit();
        }
        else
        {
            Intent launchMainActivity =new Intent(this,MainActivity.class);
            startActivity(launchMainActivity);
            finish();
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.finish:
                Intent i=new Intent(IntroductionActivity.this,MainActivity.class);
                startActivity(i);
                finish();
                break;

            case R.id.previous:
                viewPager.setCurrentItem(viewPager.getCurrentItem()-1,true);
                break;


            case R.id.next:
                viewPager.setCurrentItem(viewPager.getCurrentItem()+1,true);
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
     if(position==0)
     {
         previous.setVisibility(View.GONE);
         finish.setVisibility(View.GONE);
         next.setVisibility(View.VISIBLE);
     }else if(position==1)
     {
         previous.setVisibility(View.VISIBLE);
         finish.setVisibility(View.GONE);
         next.setVisibility(View.VISIBLE);
     }
     else
     {
         previous.setVisibility(View.VISIBLE);
         finish.setVisibility(View.VISIBLE);
         next.setVisibility(View.GONE);
     }

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
