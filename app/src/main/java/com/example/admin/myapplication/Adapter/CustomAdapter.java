package com.example.admin.myapplication.Adapter;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.renderscript.RenderScript;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.admin.myapplication.ImageScreen;
import com.example.admin.myapplication.Model.SplashModel;
import com.example.admin.myapplication.ProfileActivity;
import com.example.admin.myapplication.R;
import com.example.admin.myapplication.Utils.CommonUtils;
import com.example.admin.myapplication.Utils.CustomTextViewMain;
import com.example.admin.myapplication.Utils.RSBlurProcessor;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import jp.wasabeef.blurry.Blurry;
import jp.wasabeef.blurry.internal.Blur;

import static android.support.constraint.Constraints.TAG;

public class CustomAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements ActivityCompat.OnRequestPermissionsResultCallback {
    List<Object> list;
    Context context;
    Bitmap bitmap;
    final int SPLASHMODEL_TYPE = 0;
    final int PROGRESSMODEL_TYPE = 1;
    int previous_position = 0;
    ConstraintSet constraintSet;
    DrawerLayout drawerLayout;
    View view;


    public CustomAdapter(List<Object> list, Context context, DrawerLayout drawerLayout,View view) {
        this.context = context;
        this.list = list;
        this.drawerLayout = drawerLayout;
        constraintSet = new ConstraintSet();
        this.view = view;
    }

    @Override
    public int getItemViewType(int position) {
        if (list.get(position) instanceof SplashModel) {
            return SPLASHMODEL_TYPE;
        } else {
            return PROGRESSMODEL_TYPE;
        }
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        if (viewType == SPLASHMODEL_TYPE) {
            v = LayoutInflater.from(context).inflate(R.layout.recycleritem, parent, false);
            MyRecyclerItemViewHolder holder = new MyRecyclerItemViewHolder(v);
            return holder;
        } else {
            v = LayoutInflater.from(context).inflate(R.layout.recycler_view_progress_bar, parent, false);
            MyRecyclerProgresViewHolder holder = new MyRecyclerProgresViewHolder(v);
            return holder;
        }


    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {

        try {


            if (holder instanceof MyRecyclerItemViewHolder) {
                //MyAnimationUtils.Animate(holder,true);
                final MyRecyclerItemViewHolder myholder = (MyRecyclerItemViewHolder) holder;
                final SplashModel model = (SplashModel) list.get(position);
                myholder.title.setText(model.getUser().getFirstName());
                myholder.likes.setText(model.getUser().getTotalLikes() + " likes");
                RequestOptions requestOptions = new RequestOptions();
                constraintSet.clone(myholder.constraintLayout);
                constraintSet.setDimensionRatio(myholder.imageView.getId(), String.valueOf(model.getWidth()) + ":" + String.valueOf(model.getHeight()));
                constraintSet.setDimensionRatio(myholder.shimmerlayout.getId(), String.valueOf(model.getWidth()) + ":" + String.valueOf(model.getHeight()));
                constraintSet.applyTo(myholder.constraintLayout);
                myholder.cardView.setCardBackgroundColor(Color.parseColor(model.getColor()));


                Glide.with(context)
                        .setDefaultRequestOptions(requestOptions)
                        .load(model.getUrls().getRegular())
                        .thumbnail(0.2f)
                        .apply(requestOptions.override(1000, 1000))
                        .apply(requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL))
                        .listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                // log exception

                                myholder.shimmerlayout.stopShimmer();
                                myholder.errorText.setVisibility(View.VISIBLE);

                                return false;

                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                myholder.errorText.setVisibility(View.GONE);
                                myholder.shimmerlayout.stopShimmer();
                                return false;
                            }
                        })
                        .into(myholder.imageView);

                Glide.with(context)
                        .load(model.getUser().getProfileImage().getMedium())
                        .thumbnail(0.2f)
                        .apply(requestOptions.override(50, 50))
                        .apply(requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL))
                        .into(myholder.ProfileImage);


                myholder.popupmenu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        CommonUtils.showPopUp(context,model,drawerLayout);
                    }
                });
//                if(position>previous_position)
//                {
//                    MyAnimationUtils.Animate(myholder, true);
//                }
//                else
//                {
//                    MyAnimationUtils.Animate(holder,false);
//                }
                // MyAnimationUtils.Animate(holder,true);
                previous_position = position;
                myholder.ProfileImage.setBorderColor(Color.parseColor(model.getColor()));
                //holder.imageView.setMaxHeight(1000);
                //holder.relativeLayout.setBackgroundColor(Color.parseColor(list.get(position).getColor()));
                myholder.ProfileImage.setOnClickListener(new View.OnClickListener() {
                    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onClick(View view) {
                        Intent i1 = new Intent(context, ProfileActivity.class);
                        ActivityOptionsCompat options = ActivityOptionsCompat.
                                makeSceneTransitionAnimation((Activity) context, myholder.ProfileImage, myholder.ProfileImage.getTransitionName());

                        i1.putExtra("myobject", model);
                        context.startActivity(i1, options.toBundle());
                    }
                });


                myholder.imageView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        CommonUtils.longPressImage(context,model);
                        return true;
                    }
                });

                // holder.recycleritemrelativelayout.setBackgroundColor(Color.parseColor(list.get(position).getColor()));
                //holder.cardView.setCardBackgroundColor(Color.parseColor(list.get(position).getColor()));

                //Glide.with(context).load(list.get(position).getUrls().getRegular().toString()).into(holder.imageView);
                myholder.imageView.setOnClickListener(new View.OnClickListener() {
                    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(context, ImageScreen.class);
                        i.putExtra("model", model);
//                    ActivityOptionsCompat options = ActivityOptionsCompat.
//                            makeSceneTransitionAnimation((Activity) context, holder.imageView,holder.imageView.getTransitionName());
//                    context.startActivity(i,options.toBundle());

                        context.startActivity(i);


                    }
                });

            } else {

                MyRecyclerProgresViewHolder myholder = (MyRecyclerProgresViewHolder) holder;
                myholder.progressBar.setIndeterminate(true);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class MyRecyclerItemViewHolder extends RecyclerView.ViewHolder {

        CircleImageView ProfileImage;
        TextView title;
        TextView likes, id;
        ImageView imageView;
        View v;
        CardView cardView;
        LinearLayout linearLayout;
        RelativeLayout relativeLayout, recycleritemrelativelayout;
        ImageView popupmenu;
        ProgressBar progressBar;
        CustomTextViewMain errorText;
        ConstraintLayout constraintLayout;
        ShimmerFrameLayout shimmerlayout;


        public MyRecyclerItemViewHolder(View itemView) {
            super(itemView);
            v = itemView;
            imageView = (ImageView) v.findViewById(R.id.imageView);
            likes = (TextView) v.findViewById(R.id.likes);
            title = (TextView) v.findViewById(R.id.title);
            ProfileImage = (CircleImageView) v.findViewById(R.id.user_profile_image);
            cardView = (CardView) v.findViewById(R.id.recycler_item_card);
            relativeLayout = (RelativeLayout) v.findViewById(R.id.header_recycleritem);
            popupmenu = (ImageView) v.findViewById(R.id.recycler_item_menu);
            errorText = (CustomTextViewMain) v.findViewById(R.id.loading_error_main_list);
            constraintLayout = (ConstraintLayout) v.findViewById(R.id.recycler_item_contraintlayout);
            shimmerlayout = (ShimmerFrameLayout) v.findViewById(R.id.shimmer_layout);

        }

    }


    public class MyRecyclerProgresViewHolder extends RecyclerView.ViewHolder {

        ProgressBar progressBar;

        public MyRecyclerProgresViewHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.recycler_item_progress_bar);

        }
    }




    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case 1:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(context, "Permission Granted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Permission Not Granted", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    public Bitmap getBitmapFromView(View v) {
        Bitmap b = Bitmap.createBitmap(v.getWidth(),v.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        v.layout(0, 0, v.getLayoutParams().width, v.getLayoutParams().height);
        v.draw(c);
        return b;
    }


}
