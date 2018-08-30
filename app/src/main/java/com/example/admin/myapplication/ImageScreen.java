package com.example.admin.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.admin.myapplication.Adapter.MyImageViewPagerAdapter;
import com.example.admin.myapplication.Model.SplashModel;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class ImageScreen extends AppCompatActivity implements View.OnClickListener {

    Intent i;
    ImageView imageView;
    ScaleGestureDetector mscaleDetector;
    private float mScaleFactor = 1;
    Animation openRotation, closeRotation, translateYopen, translateYclose,fadein,fadeout;
    boolean isOpen = false;
    String ImageUrl = null;
    ViewPager imageViewPager;
    MyImageViewPagerAdapter adapter;
    List<SplashModel> list;
    int image_position;
    TextView sharetext,setbackgroundtext,downloadtext;


    FloatingActionButton mainbutton, sharebutton, downloadbutton,setbackgroundImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_screen);
        init();


    }

    public void init() {
        imageViewPager=(ViewPager)findViewById(R.id.image_view_pager);
        list=new ArrayList<>();


        sharetext=(TextView)findViewById(R.id.share_text);
        setbackgroundtext=(TextView)findViewById(R.id.set_background_text);
        downloadtext=(TextView)findViewById(R.id.download_text);

        openRotation = AnimationUtils.loadAnimation(this, R.anim.rotateopen);
        closeRotation = AnimationUtils.loadAnimation(this, R.anim.rotateclose);
        translateYopen = AnimationUtils.loadAnimation(this, R.anim.translateyopen);

        fadein=AnimationUtils.loadAnimation(this,R.anim.fadein);
        fadeout=AnimationUtils.loadAnimation(this,R.anim.fadeout);

        translateYclose = AnimationUtils.loadAnimation(this, R.anim.translateyclose);
        mainbutton = (FloatingActionButton) findViewById(R.id.main_fab_button);
        sharebutton = (FloatingActionButton) findViewById(R.id.share_fab_button);
        setbackgroundImage=(FloatingActionButton)findViewById(R.id.set_background_image);
        downloadbutton = (FloatingActionButton) findViewById(R.id.download_fab_button);
        mainbutton.setOnClickListener(this);
        sharebutton.setOnClickListener(this);
        downloadbutton.setOnClickListener(this);
        setbackgroundImage.setOnClickListener(this);


        //setting the visibility to invisible
        sharebutton.setVisibility(View.GONE);
        downloadbutton.setVisibility(View.GONE);
        setbackgroundImage.setVisibility(View.GONE);

        sharetext.setVisibility(View.GONE);
        downloadtext.setVisibility(View.GONE);
        setbackgroundtext.setVisibility(View.GONE);


        imageView = (ImageView) findViewById(R.id.imageViewscreen);
        if (!getIntent().hasExtra("list")) {
            MyAlertDialog("didnotFetch iurl");
        } else {
            list=(List<SplashModel>) getIntent().getSerializableExtra("list");
        }

        if(getIntent().hasExtra("position"))
        {
            image_position=getIntent().getIntExtra("position",0);
        }

        adapter=new MyImageViewPagerAdapter(this,list);
        imageViewPager.setAdapter(adapter);
        imageViewPager.setCurrentItem(image_position);
        mscaleDetector = new ScaleGestureDetector(this, new MyScaleListner());

        //Glide.with(this).load(lis).into(imageView);
    }

    public void MyAlertDialog(String name) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(name);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mscaleDetector.onTouchEvent(event);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.main_fab_button:
                if (isOpen == false) {
                    setbackgroundImage.setAnimation(translateYopen);
                    sharebutton.setAnimation(translateYopen);
                    downloadbutton.setAnimation(translateYopen);

                    sharetext.setAnimation(fadein);
                    downloadtext.setAnimation(fadein);
                    setbackgroundtext.setAnimation(fadein);

                    sharebutton.setClickable(true);
                    downloadbutton.setClickable(true);
                    setbackgroundImage.setClickable(true);

                    sharetext.setVisibility(View.VISIBLE);
                    downloadtext.setVisibility(View.VISIBLE);
                    setbackgroundtext.setVisibility(View.VISIBLE);



                    sharebutton.setVisibility(View.VISIBLE);
                    downloadbutton.setVisibility(View.VISIBLE);
                    setbackgroundImage.setVisibility(View.VISIBLE);

                    mainbutton.setAnimation(openRotation);
                    translateYopen.start();
                    openRotation.start();
                    fadein.start();
                    isOpen = true;
                } else {
                    sharebutton.setAnimation(translateYclose);
                    downloadbutton.setAnimation(translateYclose);
                    setbackgroundImage.setAnimation(translateYclose);

                    sharetext.setAnimation(fadeout);
                    downloadtext.setAnimation(fadeout);
                    setbackgroundtext.setAnimation(fadeout);

                    sharetext.setVisibility(View.GONE);
                    downloadtext.setVisibility(View.GONE);
                    setbackgroundtext.setVisibility(View.GONE);

                    sharebutton.setVisibility(View.GONE);
                    downloadbutton.setVisibility(View.GONE);
                    setbackgroundImage.setVisibility(View.GONE);

                    sharebutton.setClickable(false);
                    downloadbutton.setClickable(false);
                    setbackgroundImage.setClickable(false);

                    mainbutton.setAnimation(closeRotation);
                    translateYclose.start();
                    closeRotation.start();
                    fadeout.start();
                    isOpen = false;
                }
                break;

            case R.id.share_fab_button:
                Intent i=new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse( ImageUrl));
                startActivity(i);
                break;


            case R.id.download_fab_button:
                break;

        }
    }


    private class MyScaleListner extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            mScaleFactor *= detector.getScaleFactor();
            mScaleFactor = Math.max(0.1f, Math.min(mScaleFactor, 10.0f));
            imageView.setScaleX(mScaleFactor);
            imageView.setScaleY(mScaleFactor);
            return true;
        }
    }


}
