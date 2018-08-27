package com.example.admin.myapplication;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.example.admin.myapplication.Adapter.MyViewPagerAdapter;
import com.example.admin.myapplication.Fragments.Fragment1;
import com.example.admin.myapplication.Fragments.Fragment2;
import com.example.admin.myapplication.Fragments.Fragment3;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerActivity extends AppCompatActivity implements ViewPager.PageTransformer {

    ViewPager viewPager;
    MyViewPagerAdapter adapter;
    List<Fragment> list;
    Toolbar toolbar;
    Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        init();
    }

    public void init() {
        viewPager = (ViewPager) findViewById(R.id.ViewPager1);
        list = new ArrayList<>();
        list.add(new Fragment1());
        list.add(new Fragment2());
        list.add(new Fragment3());
        adapter = new MyViewPagerAdapter(getSupportFragmentManager(), list, this);
        viewPager.setAdapter(adapter);

        button=(Button)findViewById(R.id.next);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(ViewPagerActivity.this,LoginActivity.class);
                startActivity(i);
            }
        });
        viewPager.setPageTransformer(false, this);


        //
    }


    @Override
    public void transformPage(@NonNull View page, float position) {


        ImageView imageView = (ImageView) page.findViewById(R.id.image32);
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
}
