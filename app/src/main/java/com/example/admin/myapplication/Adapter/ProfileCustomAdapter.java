package com.example.admin.myapplication.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.admin.myapplication.ImageActivity;
import com.example.admin.myapplication.Listners.OnClickHandled;
import com.example.admin.myapplication.Model.SplashModel;
import com.example.admin.myapplication.R;
import com.example.admin.myapplication.Utils.CommonUtils;
import com.example.admin.myapplication.Utils.Constants;
import com.example.admin.myapplication.Utils.CustomTextViewMain;

import java.util.List;

public class ProfileCustomAdapter extends RecyclerView.Adapter<ProfileCustomAdapter.MyViewHolder> {

    List<SplashModel> list;
    Context context;
    ConstraintSet constraintSet;
    CoordinatorLayout coordinatorLayout;
    OnClickHandled onClickHandled;


    public ProfileCustomAdapter(List<SplashModel> list, Context context, CoordinatorLayout coordinatorLayout,OnClickHandled onClickHandled) {
        this.context = context;
        this.list = list;
        this.coordinatorLayout = coordinatorLayout;
        constraintSet = new ConstraintSet();
        this.onClickHandled=onClickHandled;
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
    public void onBindViewHolder(@NonNull final ProfileCustomAdapter.MyViewHolder holder, int position) {

        holder.likes_profile.setText(String.valueOf(list.get(position).getLikes()) + " likes");


        holder.imageView_profile.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {

                onClickHandled.onClickImageView(holder.getAdapterPosition(),list.get(holder.getAdapterPosition()));
            }
        });

        constraintSet.clone(holder.constraintLayout);
        constraintSet.setDimensionRatio(holder.imageView_profile.getId(), String.valueOf(list.get(position).getWidth()) + ":" + String.valueOf(list.get(position).getHeight()));
        constraintSet.applyTo(holder.constraintLayout);

        holder.cardView.setCardBackgroundColor(Color.parseColor(list.get(position).getColor()));

        holder.imageView_profile.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                onClickHandled.onLongClickImageView(holder.getAdapterPosition(),list.get(holder.getAdapterPosition()));
                return true;

            }
        });

        holder.menu_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickHandled.onClickPopUpMenu(holder.getAdapterPosition(),list.get(holder.getAdapterPosition()));

            }
        });

        Glide.with(context)
                .load(list.get(position).getUrls().getRegular())
                .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL))
                .apply(new RequestOptions().override(1000, 1000))
                .into(holder.imageView_profile);

        if(CommonUtils.getThemePreference(context))
        {
            holder.menu_profile.setColorFilter(Color.parseColor(Constants.MATERIAL_GGREY));
            holder.likes_profile.setTextColor(Color.parseColor(Constants.MATERIAL_GGREY));
        }
        else
        {
            holder.menu_profile.setColorFilter(Color.parseColor(Constants.MATERIAL_BLACK));
            holder.likes_profile.setTextColor(Color.parseColor(Constants.MATERIAL_BLACK));
        }


    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {


        ImageView imageView_profile, menu_profile;
        View v;
        CustomTextViewMain likes_profile;
        ConstraintLayout constraintLayout;
        CardView cardView;

         MyViewHolder(View itemView) {
            super(itemView);
            v = itemView;
            imageView_profile = v.findViewById(R.id.user_personal_pics);
            likes_profile =  v.findViewById(R.id.likes_profile);
            menu_profile = v.findViewById(R.id.recycler_item_menu_profile);
            constraintLayout =  v.findViewById(R.id.profilelist_constraintlayout);
            cardView =  v.findViewById(R.id.profile_card_view);
        }
    }


}
