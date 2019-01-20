package com.example.admin.myapplication.Utils;

import android.app.ProgressDialog;
import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.admin.myapplication.Model.SplashModel;

import java.io.IOException;
import java.io.InputStream;

public class SetBackground extends AsyncTask<String, Void, Bitmap> {

    ProgressDialog progressDialog;
    SplashModel splashModel;
    Context context;
    Bitmap bitmap;

    SetBackground(Context context, SplashModel splashModel) {
        this.splashModel = splashModel;
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(context);
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
        WallpaperManager manager = WallpaperManager.getInstance(context);

        try {
            if (bitmap == null) {
                Toast.makeText(context, "Error :(", Toast.LENGTH_SHORT).show();
            } else {
                manager.setBitmap(bitmap);
                //manager.setBitmap(bitmap,null,false,WallpaperManager.FLAG_LOCK);
                Toast.makeText(context, "Wallpaper set :)", Toast.LENGTH_SHORT).show();
            }

        } catch (IOException e) {
            e.printStackTrace();


        }
        progressDialog.dismiss();
    }
}
