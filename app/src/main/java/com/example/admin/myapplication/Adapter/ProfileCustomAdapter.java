package com.example.admin.myapplication.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
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


    public ProfileCustomAdapter(List<SplashModel> list, Context context) {
        this.context = context;
        this.list = list;
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

        holder.imageView_profile.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                ALertDialog(list.get(position).getUrls().getRegular());
                return true;

            }
        });

        holder.menu_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        Glide.with(context).load(list.get(position).getUrls().getRegular()).apply(new RequestOptions().override(1000, 1000)).into(holder.imageView_profile);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void ALertDialog(String url) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = inflater.inflate(R.layout.longpress_image, null);
            ImageView imageView = (ImageView) v.findViewById(R.id.prodifile_image_longPress);
            Glide.with(context).load(url).into(imageView);
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


        public MyViewHolder(View itemView) {
            super(itemView);
            v = itemView;
            imageView_profile = (ImageView) v.findViewById(R.id.user_personal_pics);
            likes_profile=(RecyclerTextView)v.findViewById(R.id.likes_profile);
            menu_profile=(ImageView)v.findViewById(R.id.recycler_item_menu_profile);
        }
    }


}
