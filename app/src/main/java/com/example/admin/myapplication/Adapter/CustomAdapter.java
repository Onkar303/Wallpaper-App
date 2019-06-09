package com.example.admin.myapplication.Adapter;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.admin.myapplication.ImageActivity;
import com.example.admin.myapplication.Listners.ConfigureDarkTheme;
import com.example.admin.myapplication.Model.SplashModel;
import com.example.admin.myapplication.ProfileActivity;
import com.example.admin.myapplication.R;
import com.example.admin.myapplication.SettingActivity;
import com.example.admin.myapplication.Utils.CommonUtils;
import com.example.admin.myapplication.Utils.Constants;
import com.example.admin.myapplication.Utils.CustomTextViewMain;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

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
    ConfigureDarkTheme theme;
    OnClickedHandled clickedhandled;



    public CustomAdapter(List<Object> list, Context context,View view,OnClickedHandled clickedhandled) {
        this.context = context;
        this.list = list;
        this.view = view;
        this.clickedhandled=clickedhandled;

        constraintSet = new ConstraintSet();

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
            MyRecyclerItemViewHolder splashModel_Holder = new MyRecyclerItemViewHolder(v);
            return splashModel_Holder;
        } else {
            v = LayoutInflater.from(context).inflate(R.layout.recycler_view_progress_bar, parent, false);
            MyRecyclerProgresViewHolder holder = new MyRecyclerProgresViewHolder(v);
            return holder;
        }


    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder,int position) {
        try {
            if (holder instanceof MyRecyclerItemViewHolder) {
                //MyAnimationUtils.Animate(holder,true);
                final MyRecyclerItemViewHolder myholder = (MyRecyclerItemViewHolder) holder;
                final SplashModel model = (SplashModel) list.get(holder.getAdapterPosition());
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
                        clickedhandled.onClickPopUpMenu(holder.getAdapterPosition(),model);
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
                //previous_position = position;
                myholder.ProfileImage.setBorderColor(Color.parseColor(model.getColor()));
                myholder.ProfileImage.setOnClickListener(new View.OnClickListener() {
                    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onClick(View view) {
                        clickedhandled.onClickProfileImage(holder.getAdapterPosition(),model,myholder);
                    }
                });


                myholder.imageView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        clickedhandled.onLongClickImageView(holder.getAdapterPosition(),model);
                        return true;
                    }
                });

                myholder.imageView.setOnClickListener(new View.OnClickListener() {
                    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onClick(View v) {
                        clickedhandled.onClickImageView(holder.getAdapterPosition(),model);

                    }
                });


                if(CommonUtils.getThemePreference(context))
                {
                    myholder.title.setTextColor(Color.parseColor(Constants.MATERIAL_GGREY));
                    myholder.likes.setTextColor(Color.parseColor(Constants.MATERIAL_GGREY));
                    myholder.popupmenu.setColorFilter(Color.parseColor(Constants.MATERIAL_GGREY));
                }
                else
                {
                    myholder.title.setTextColor(Color.parseColor(Constants.MATERIAL_BLACK));
                    myholder.likes.setTextColor(Color.parseColor(Constants.MATERIAL_BLACK));
                    myholder.popupmenu.setColorFilter(Color.parseColor(Constants.MATERIAL_BLACK));
                }

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


    public class MyRecyclerItemViewHolder extends RecyclerView.ViewHolder implements ConfigureDarkTheme {

        public CircleImageView ProfileImage;
        TextView title;
        TextView likes;
        ImageView imageView;
        View v;
        CardView cardView;
        RelativeLayout relativeLayout;
        ImageView popupmenu;
        CustomTextViewMain errorText;
        ConstraintLayout constraintLayout;
        ShimmerFrameLayout shimmerlayout;



        MyRecyclerItemViewHolder(View itemView) {
            super(itemView);
            v = itemView;
            imageView = v.findViewById(R.id.imageView);
            likes = v.findViewById(R.id.likes);
            title = v.findViewById(R.id.title);
            ProfileImage = v.findViewById(R.id.user_profile_image);
            cardView = v.findViewById(R.id.recycler_item_card);
            relativeLayout = v.findViewById(R.id.header_recycleritem);
            popupmenu = v.findViewById(R.id.recycler_item_menu);
            errorText =  v.findViewById(R.id.loading_error_main_list);
            constraintLayout =  v.findViewById(R.id.recycler_item_contraintlayout);
            shimmerlayout =  v.findViewById(R.id.shimmer_layout);
            CommonUtils.getTheme(context,this);
            SettingActivity.theme = this;



        }



        @Override
        public void isDark(boolean isDark) {
            if(isDark)
               {
                    title.setTextColor(Color.parseColor(Constants.MATERIAL_GGREY));
                    likes.setTextColor(Color.parseColor(Constants.MATERIAL_GGREY));
                    popupmenu.setColorFilter(Color.parseColor(Constants.MATERIAL_GGREY));

                }
                else
                {
                    title.setTextColor(Color.parseColor(Constants.MATERIAL_BLACK));
                    likes.setTextColor(Color.parseColor(Constants.MATERIAL_BLACK));
                    popupmenu.setColorFilter(Color.parseColor(Constants.MATERIAL_BLACK));
                }

        }
    }


    public class MyRecyclerProgresViewHolder extends RecyclerView.ViewHolder {

        ProgressBar progressBar;

        MyRecyclerProgresViewHolder(View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.recycler_item_progress_bar);

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


    public interface OnClickedHandled{
        void onClickImageView(int position,SplashModel splashModel);
        void onLongClickImageView(int position,SplashModel splashModel);
        void onClickProfileImage(int position,SplashModel splashModel,MyRecyclerItemViewHolder holder);
        void onClickPopUpMenu(int position,SplashModel splashModel);
    }


}
