package com.example.admin.myapplication.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.admin.myapplication.ImageScreen;
import com.example.admin.myapplication.Model.SplashModel;
import com.example.admin.myapplication.ProfileActivity;
import com.example.admin.myapplication.R;
import com.example.admin.myapplication.Utils.RecyclerTextView;

import java.io.Serializable;
import java.util.List;

public class ProfileCustomAdapter extends RecyclerView.Adapter<ProfileCustomAdapter.MyViewHolder> {

    List<SplashModel> list;
    Context context;
    Bitmap bitmap;
    ConstraintSet constraintSet;


    public ProfileCustomAdapter(List<SplashModel> list, Context context) {
        this.context = context;
        this.list = list;
        constraintSet=new ConstraintSet();
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

        holder.likes_profile.setText(String.valueOf(list.get(position).getLikes())+" likes");


        holder.imageView_profile.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, ImageScreen.class);
                i.putExtra("list",(Serializable) list);
                i.putExtra("position",position);
//                ActivityOptionsCompat options = ActivityOptionsCompat.
//                        makeSceneTransitionAnimation((Activity) context, holder.imageView, holder.imageView.getTransitionName());
//
//                context.startActivity(i, options.toBundle());
                context.startActivity(i);
            }
        });

        constraintSet.clone(holder.constraintLayout);
        constraintSet.setDimensionRatio(holder.imageView_profile.getId(),String.valueOf(list.get(position).getWidth())+":"+String.valueOf(list.get(position).getHeight()));
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

    public void ALertDialog(SplashModel model) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = inflater.inflate(R.layout.longpress_image, null);
            ConstraintLayout constraintLayout=(ConstraintLayout) v.findViewById(R.id.longpress_contraintlayout);
            constraintSet.clone(constraintLayout);
            ImageView imageView = (ImageView) v.findViewById(R.id.prodifile_image_longPress);
            constraintSet.setDimensionRatio(imageView.getId(),String.valueOf(model.getWidth())+":"+String.valueOf(model.getHeight()));
            constraintSet.applyTo(constraintLayout);
            Glide.with(context).load(model.getUrls().getRegular()).into(imageView);
            AlertDialog dialog = builder.create();
            dialog.setView(v);
            dialog.show();
            dialog.getWindow().getAttributes().windowAnimations = R.style.AppTheme;

        } catch (NullPointerException exception) {
            exception.printStackTrace();
        }

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        ImageView imageView_profile,menu_profile;
        View v;
        RecyclerTextView likes_profile;
        ConstraintLayout constraintLayout;
        CardView cardView;

        public MyViewHolder(View itemView) {
            super(itemView);
            v = itemView;
            imageView_profile = (ImageView) v.findViewById(R.id.user_personal_pics);
            likes_profile=(RecyclerTextView)v.findViewById(R.id.likes_profile);
            menu_profile=(ImageView)v.findViewById(R.id.recycler_item_menu_profile);
            constraintLayout=(ConstraintLayout)v.findViewById(R.id.profilelist_constraintlayout);
            cardView=(CardView)v.findViewById(R.id.profile_card_view);
        }
    }


}
