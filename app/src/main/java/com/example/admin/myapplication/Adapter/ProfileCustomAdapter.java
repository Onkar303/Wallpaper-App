package com.example.admin.myapplication.Adapter;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.admin.myapplication.ImageScreen;
import com.example.admin.myapplication.Model.SplashModel;
import com.example.admin.myapplication.ProfileActivity;
import com.example.admin.myapplication.R;
import com.example.admin.myapplication.Utils.CustomTextViewMain;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;

import static android.support.constraint.Constraints.TAG;

public class ProfileCustomAdapter extends RecyclerView.Adapter<ProfileCustomAdapter.MyViewHolder> {

    List<SplashModel> list;
    Context context;
    Bitmap bitmap;
    ConstraintSet constraintSet;
    CoordinatorLayout coordinatorLayout;


    public ProfileCustomAdapter(List<SplashModel> list, Context context, CoordinatorLayout coordinatorLayout) {
        this.context = context;
        this.list = list;
        this.coordinatorLayout = coordinatorLayout;
        constraintSet = new ConstraintSet();
    }


    @NonNull
    @Override
    public ProfileCustomAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.profile_recycler_layout, parent, false);
        ProfileCustomAdapter.MyViewHolder holder = new ProfileCustomAdapter.MyViewHolder(v);
        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull final ProfileCustomAdapter.MyViewHolder holder, final int position) {

        holder.likes_profile.setText(String.valueOf(list.get(position).getLikes()) + " likes");


        holder.imageView_profile.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, ImageScreen.class);
                i.putExtra("model",list.get(position));
//                i.putExtra("position", position);
//                ActivityOptionsCompat options = ActivityOptionsCompat.
//                        makeSceneTransitionAnimation((Activity) context, holder.imageView, holder.imageView.getTransitionName());
//
//                context.startActivity(i, options.toBundle());
                context.startActivity(i);
            }
        });

        constraintSet.clone(holder.constraintLayout);
        constraintSet.setDimensionRatio(holder.imageView_profile.getId(), String.valueOf(list.get(position).getWidth()) + ":" + String.valueOf(list.get(position).getHeight()));
        constraintSet.applyTo(holder.constraintLayout);

        holder.cardView.setCardBackgroundColor(Color.parseColor(list.get(position).getColor()));

        holder.imageView_profile.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                ALertDialog(list.get(position));
                return true;

            }
        });

        holder.menu_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopUp(list.get(position));
            }
        });

        Glide.with(context)
                .load(list.get(position).getUrls().getRegular())
                .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL))
                .apply(new RequestOptions().override(1000, 1000))
                .into(holder.imageView_profile);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {


        ImageView imageView_profile, menu_profile;
        View v;
        CustomTextViewMain likes_profile;
        ConstraintLayout constraintLayout;
        CardView cardView;

        public MyViewHolder(View itemView) {
            super(itemView);
            v = itemView;
            imageView_profile = (ImageView) v.findViewById(R.id.user_personal_pics);
            likes_profile = (CustomTextViewMain) v.findViewById(R.id.likes_profile);
            menu_profile = (ImageView) v.findViewById(R.id.recycler_item_menu_profile);
            constraintLayout = (ConstraintLayout) v.findViewById(R.id.profilelist_constraintlayout);
            cardView = (CardView) v.findViewById(R.id.profile_card_view);
        }
    }

    public void ALertDialog(SplashModel model) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = inflater.inflate(R.layout.longpress_image, null);
            ConstraintLayout constraintLayout = (ConstraintLayout) v.findViewById(R.id.longpress_contraintlayout);
            constraintSet.clone(constraintLayout);
            constraintLayout.setBackgroundColor(Color.parseColor(model.getColor()));
            ImageView imageView = (ImageView) v.findViewById(R.id.prodifile_image_longPress);
            constraintSet.setDimensionRatio(imageView.getId(), String.valueOf(model.getWidth()) + ":" + String.valueOf(model.getHeight()));
            constraintSet.applyTo(constraintLayout);
            Glide.with(context).load(model.getUrls().getRegular()).into(imageView);
            AlertDialog dialog = builder.create();
            dialog.setView(v);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().setWindowAnimations(R.style.CustomTheme);
            dialog.show();


        } catch (NullPointerException exception) {
            exception.printStackTrace();
        }

    }


    public void showPopUp(final SplashModel splashModel) {
        LinearLayout download, showProfile, setasbackground;
        ImageView close;
        final android.app.AlertDialog.Builder bottomSheetDialog = new android.app.AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.alert_dialog_view, null, false);
        showProfile = (LinearLayout) view.findViewById(R.id.showprofile_linear_layout);
        close = (ImageView) view.findViewById(R.id.bottom_dialog_close);
        download = (LinearLayout) view.findViewById(R.id.bottom_dialog_download);
        setasbackground = (LinearLayout) view.findViewById(R.id.set_as_background_linearlayout_bottomsheet);
        bottomSheetDialog.setView(view);
        final android.app.AlertDialog dialog = bottomSheetDialog.create();
        dialog.getWindow().setWindowAnimations(R.style.CustomTheme);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        showProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, ProfileActivity.class);
                i.putExtra("myobject", splashModel);
                context.startActivity(i);
                dialog.dismiss();


            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean checkpermission = ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;

                if (!checkpermission) {
                    ActivityCompat.requestPermissions((Activity) context, new String[]{
                            Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE
                    }, 1);
                }

                if (checkpermission) {
                    new ProfileCustomAdapter.DownloadAsyncTask(splashModel).execute();

                } else {
                    Toast.makeText(context, "No permission granted by you :'(", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();

            }
        });

        setasbackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // new CustomAdapter.setAsBackgroundAsyncTask(splashModel).execute();
                new ProfileCustomAdapter.setAsBackgroundAsyncTask(splashModel).execute();
                dialog.dismiss();

            }
        });

        //bottomSheetDialog.getWindow().setBackgroundDrawableResource(R.drawable.background_bottom_sheet);


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

    public class DownloadAsyncTask extends AsyncTask<Void, Void, Bitmap> {
        SplashModel splashModel;
        ProgressDialog progressDialog;

        DownloadAsyncTask(SplashModel splashModel) {
            this.splashModel = splashModel;
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
                Toast.makeText(context, "Error Downloading :(", Toast.LENGTH_SHORT).show();

            } else {
                Snackbar.make(coordinatorLayout, "Download Successfull :)", Snackbar.LENGTH_LONG)
                        .setAction("Open", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                                Uri uri = Uri.parse(Environment.getExternalStorageDirectory().getPath() + "/camtest/");

                                i.setDataAndType(uri, "image/*");
                                context.startActivity(Intent.createChooser(i, "Open folder"));
                            }
                        })
                        .setActionTextColor(context.getResources().getColor(R.color.materialGrey))
                        .show();

            }

            progressDialog.dismiss();
        }
    }


}
