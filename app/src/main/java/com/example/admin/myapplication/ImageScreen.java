package com.example.admin.myapplication;

import android.app.ProgressDialog;
import android.app.WallpaperManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.admin.myapplication.Adapter.MyImageViewPagerAdapter;
import com.example.admin.myapplication.Model.SplashModel;
import com.jsibbold.zoomage.ZoomageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static android.support.constraint.Constraints.TAG;

public class ImageScreen extends AppCompatActivity implements View.OnClickListener {

    Intent i;
    ZoomageView imageView;
    ScaleGestureDetector mscaleDetector;
    private float mScaleFactor = 1;
    Animation openRotation, closeRotation, translateYopen, translateYclose, fadein, fadeout;
    boolean isOpen = false;
    String ImageUrl = null;
    SplashModel model;
    int image_position;
    TextView sharetext, setbackgroundtext, downloadtext;

    ProgressBar progressBar;

    FloatingActionButton mainbutton, sharebutton, downloadbutton, setbackgroundImage;

    LinearLayout set_as_background_linearlayout, download_linearlayout, share_linearlayout;

    CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_screen);
        init();


    }

    public void init() {


        progressBar = (ProgressBar)findViewById(R.id.ImageScreenProgressBar);

        coordinatorLayout= (CoordinatorLayout)findViewById(R.id.ImageScreenParentLayout);

        sharetext = (TextView) findViewById(R.id.share_text);
        setbackgroundtext = (TextView) findViewById(R.id.set_background_text);
        downloadtext = (TextView) findViewById(R.id.download_text);

        openRotation = AnimationUtils.loadAnimation(this, R.anim.rotateopen);
        closeRotation = AnimationUtils.loadAnimation(this, R.anim.rotateclose);
        translateYopen = AnimationUtils.loadAnimation(this, R.anim.translateyopen);

        fadein = AnimationUtils.loadAnimation(this, R.anim.fadein);
        fadeout = AnimationUtils.loadAnimation(this, R.anim.fadeout);

        translateYclose = AnimationUtils.loadAnimation(this, R.anim.translateyclose);
        mainbutton = (FloatingActionButton) findViewById(R.id.main_fab_button);
        sharebutton = (FloatingActionButton) findViewById(R.id.share_fab_button);
        setbackgroundImage = (FloatingActionButton) findViewById(R.id.set_background_image);
        downloadbutton = (FloatingActionButton) findViewById(R.id.download_fab_button);
        mainbutton.setOnClickListener(this);
        sharebutton.setOnClickListener(this);
        downloadbutton.setOnClickListener(this);
        setbackgroundImage.setOnClickListener(this);


        //linearlayout
        set_as_background_linearlayout = (LinearLayout) findViewById(R.id.set_as_background_linearlayout);
        download_linearlayout = (LinearLayout) findViewById(R.id.download_linearlayout);
        share_linearlayout = (LinearLayout) findViewById(R.id.share_linear_layout);

        set_as_background_linearlayout.setOnClickListener(this);
        download_linearlayout.setOnClickListener(this);
        share_linearlayout.setOnClickListener(this);

        //setting the visibility to invisible
        sharebutton.setVisibility(View.GONE);
        downloadbutton.setVisibility(View.GONE);
        setbackgroundImage.setVisibility(View.GONE);

        sharetext.setVisibility(View.GONE);
        downloadtext.setVisibility(View.GONE);
        setbackgroundtext.setVisibility(View.GONE);

        imageView = (ZoomageView) findViewById(R.id.ImageScreenImageView);


        if (!getIntent().hasExtra("model")) {
            MyAlertDialog("didnotFetch url");
        } else {
            model=(SplashModel) getIntent().getSerializableExtra("model");
        }

        Glide.with(this).load(model.getUrls().getFull()).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object o, Target<Drawable> target, boolean b) {
                progressBar.setVisibility(View.GONE);
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable drawable, Object o, Target<Drawable> target, DataSource dataSource, boolean b) {
                progressBar.setVisibility(View.GONE);
                return false;
            }
        }).into(imageView);
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
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.main_fab_button:
                if (isOpen == false) {
                    //setbackgroundImage.setAnimation(translateYopen);
                    //sharebutton.setAnimation(translateYopen);
                    //downloadbutton.setAnimation(translateYopen);

                    share_linearlayout.setAnimation(translateYopen);
                    set_as_background_linearlayout.setAnimation(translateYopen);
                    download_linearlayout.setAnimation(translateYopen);

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
                    //sharebutton.setAnimation(translateYclose);
                    //downloadbutton.setAnimation(translateYclose);
                    //setbackgroundImage.setAnimation(translateYclose);

                    share_linearlayout.setAnimation(translateYclose);
                    set_as_background_linearlayout.setAnimation(translateYclose);
                    download_linearlayout.setAnimation(translateYclose);

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

            case R.id.share_linear_layout:

                Intent intent = new Intent(android.content.Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_STREAM,Uri.parse(model.getUrls().toString()));
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.setType("image/*");
                startActivity(intent);


                break;


            case R.id.download_linearlayout:
                new DownloadAsyncTask(model).execute();
                break;


            case R.id.set_as_background_linearlayout:
                new setAsBackgroundAsyncTask(model).execute();
                break;

        }
    }


    public class setAsBackgroundAsyncTask extends AsyncTask<String, Void, Bitmap> {

        ProgressDialog progressDialog;
        SplashModel splashModel;
        Bitmap bitmap;

        setAsBackgroundAsyncTask(SplashModel splashModel) {
            this.splashModel = splashModel;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(ImageScreen.this);
            progressDialog.setTitle("Downloading");
            progressDialog.setMessage("In progress.....");
            progressDialog.setIndeterminate(false);
            progressDialog.show();
        }

        @Override
        protected Bitmap doInBackground(String... strings) {

            String imageURL = splashModel.getUrls().getRegular();

            Bitmap bitmap = null;
            try {
                // Download Image from URL
                InputStream input = new java.net.URL(imageURL).openStream();
                // Decode Bitmap
                bitmap = BitmapFactory.decodeStream(input);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            WallpaperManager manager = WallpaperManager.getInstance(ImageScreen.this);

            try {
                if (bitmap == null) {
                    Toast.makeText(ImageScreen.this, "Error :(", Toast.LENGTH_SHORT).show();
                } else {
                    manager.setBitmap(bitmap);
                    //manager.setBitmap(bitmap,null,false,WallpaperManager.FLAG_LOCK);
                    Toast.makeText(ImageScreen.this, "Wallpaper set :)", Toast.LENGTH_SHORT).show();
                }

            } catch (IOException e) {
                e.printStackTrace();


            }
            progressDialog.dismiss();
        }
    }

    public class DownloadAsyncTask extends AsyncTask<Void, Void, Bitmap> {
        SplashModel splashModel;
        ProgressDialog progressDialog;


        DownloadAsyncTask(SplashModel splashModel) {
            this.splashModel = splashModel;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(ImageScreen.this);
            progressDialog.setTitle("Downloading");
            progressDialog.setMessage("In Progress...");
            progressDialog.setIndeterminate(false);
            progressDialog.show();


        }

        @Override
        protected Bitmap doInBackground(Void... voids) {

            String imageURL = splashModel.getUrls().getRegular();

            Bitmap bitmap = null;
            try {
                // Download Image from URL
                InputStream input = new java.net.URL(imageURL).openStream();
                // Decode Bitmap
                bitmap = BitmapFactory.decodeStream(input);

                FileOutputStream outStream = null;
                File sdCard = Environment.getExternalStorageDirectory();
                File dir = new File(sdCard.getAbsolutePath() + "/camtest");
                if (!dir.exists() && !dir.isDirectory()) {
                    dir.mkdirs();
                }
                String fileName = String.format("%d.jpg", System.currentTimeMillis());
                File outFile = new File(dir, fileName);

                outStream = new FileOutputStream(outFile);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
                outStream.flush();
                outStream.close();
                //Toast.makeText(context, "Wallpaper Downloaded", Toast.LENGTH_SHORT).show();

                Log.d(TAG, "onPictureTaken - wrote to " + outFile.getAbsolutePath());


            } catch (Exception e) {
                e.printStackTrace();
                //Toast.makeText(context, "Error Downloading wallpaper", Toast.LENGTH_SHORT).show();

            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if (bitmap == null) {
                Toast.makeText(ImageScreen.this, "Error Downloading :(", Toast.LENGTH_LONG).show();

            } else {
                Snackbar.make(coordinatorLayout, "Download Successfull :)", Snackbar.LENGTH_LONG)
                        .setAction("Open", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent i=new Intent(Intent.ACTION_GET_CONTENT);
                                Uri uri=Uri.parse(Environment.getExternalStorageDirectory().getPath()+"/camtest/");

                                i.setDataAndType(uri,"resource/folder");
                                startActivity(i);
                            }
                        })
                        .setActionTextColor(getResources().getColor(R.color.materialGrey))
                        .show();
            }

            progressDialog.dismiss();
        }
    }


}
