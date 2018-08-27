package com.example.admin.myapplication.Adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.widget.TextView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyViewPagerAdapter extends FragmentPagerAdapter {
    List<Fragment> list;
    Context c;


    public MyViewPagerAdapter(FragmentManager fm,List<Fragment> list,Context c)
    {
        super (fm);
        this.c=c;
        this.list=list;
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }
}
