package com.example.admin.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.net.URI;

public class ImageScreen extends AppCompatActivity implements View.OnClickListener {

    Intent i;
    ImageView imageView;
    ScaleGestureDetector mscaleDetector;
    private float mScaleFactor = 1;
    Animation openRotation, closeRotation, translateYopen, translateYclose;
    boolean isOpen = false;
    String ImageUrl = null;

    FloatingActionButton mainbutton, sharebutton, downloadbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_screen);
        init();


    }

    public void init() {
        openRotation = AnimationUtils.loadAnimation(this, R.anim.rotateopen);
        closeRotation = AnimationUtils.loadAnimation(this, R.anim.rotateclose);
        translateYopen = AnimationUtils.loadAnimation(this, R.anim.translateyopen);
        translateYclose = AnimationUtils.loadAnimation(this, R.anim.translateyclose);
        mainbutton = (FloatingActionButton) findViewById(R.id.main_fab_button);
        sharebutton = (FloatingActionButton) findViewById(R.id.share_fab_button);
        downloadbutton = (FloatingActionButton) findViewById(R.id.download_fab_button);
        mainbutton.setOnClickListener(this);
        sharebutton.setOnClickListener(this);
        downloadbutton.setOnClickListener(this);


        //setting the visibility to invisible
        sharebutton.setVisibility(View.INVISIBLE);
        downloadbutton.setVisibility(View.INVISIBLE);


        imageView = (ImageView) findViewById(R.id.imageViewscreen);
        if (!getIntent().hasExtra("url")) {
            MyAlertDialog("didnotFetch iurl");
        } else {
            ImageUrl = getIntent().getStringExtra("url");
        }
        mscaleDetector = new ScaleGestureDetector(this, new MyScaleListner());

        Glide.with(this).load(ImageUrl).into(imageView);
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
                    sharebutton.setAnimation(translateYopen);
                    downloadbutton.setAnimation(translateYopen);
                    sharebutton.setVisibility(View.GONE);
                    downloadbutton.setVisibility(View.GONE);
                    mainbutton.setAnimation(openRotation);
                    translateYopen.start();
                    openRotation.start();
                    isOpen = true;
                } else {
                    sharebutton.setAnimation(translateYclose);
                    downloadbutton.setAnimation(translateYclose);
                    sharebutton.setVisibility(View.VISIBLE);
                    downloadbutton.setVisibility(View.VISIBLE);
                    mainbutton.setAnimation(closeRotation);
                    translateYclose.start();
                    closeRotation.start();
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
