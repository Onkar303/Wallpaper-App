package com.example.admin.myapplication.Adapter;

import android.content.Context;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.admin.myapplication.Model.SplashModel;
import com.example.admin.myapplication.R;

import java.util.List;

public class MyImageViewPagerAdapter extends PagerAdapter {

    Context context;
    List<SplashModel> list;



    public MyImageViewPagerAdapter(Context context,List<SplashModel> list)
    {
        this.context=context;
        this.list=list;

    }


    @Override
    public int getCount() {
        return list.size();
    }


    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        ((ViewPager)container).removeView(container);

    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v=inflater.inflate(R.layout.image_view_pager_image,container,false);
        ImageView imageView=(ImageView)v.findViewById(R.id.imageViewscreen);
        Glide.with(context).load(list.get(position).getUrls().getRegular().toString()).into(imageView);
        ((ViewPager) container).addView(v);
        return v;
    }
}
