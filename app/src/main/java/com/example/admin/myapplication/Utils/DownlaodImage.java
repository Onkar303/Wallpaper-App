package com.example.admin.myapplication.Utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.admin.myapplication.Model.SplashModel;
import com.example.admin.myapplication.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import static android.support.constraint.Constraints.TAG;

public class DownlaodImage extends AsyncTask<Void,Void,Bitmap> {

    SplashModel splashModel;
    ProgressDialog progressDialog;
    Context context;
    View view;


    DownlaodImage(Context context,SplashModel splashModel,View view) {
        this.context = context;
        this.splashModel = splashModel;
        this.view = view;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(context);
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
            CommonUtils.CustomToast(context,"Error Downloading Image :(");

        } else {
           CommonUtils.CustomToast(context, "success");
        }

        progressDialog.dismiss();
    }

}
