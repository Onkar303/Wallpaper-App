package com.example.admin.myapplication.Utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.view.View;

import com.example.admin.myapplication.Adapter.ProfileCustomAdapter;
import com.example.admin.myapplication.Model.SplashModel;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ProfileBottomSheetCallBack extends BottomSheetBehavior.BottomSheetCallback {

    List<SplashModel> list;
    Context c;
    ProfileCustomAdapter adapter;

    public ProfileBottomSheetCallBack(List<SplashModel> list,Context c,ProfileCustomAdapter adapter)
    {
      this.list=list;
      this.c=c;
      this.adapter=adapter;
    }

    @Override
    public void onStateChanged(@NonNull View bottomSheet, int newState) {
        if(newState==BottomSheetBehavior.STATE_COLLAPSED)
        {
            list.clear();
            adapter.notifyDataSetChanged();

        }


    }

    @Override
    public void onSlide(@NonNull View bottomSheet, float slideOffset) {

    }
}
