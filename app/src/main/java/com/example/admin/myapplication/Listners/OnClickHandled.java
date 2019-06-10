package com.example.admin.myapplication.Listners;

import com.example.admin.myapplication.Adapter.CustomAdapter;
import com.example.admin.myapplication.Model.SplashModel;

public interface OnClickHandled {
    void onClickImageView(int position, SplashModel splashModel);
    void onLongClickImageView(int position,SplashModel splashModel);
    void onClickProfileImage(int position, SplashModel splashModel, CustomAdapter.MyRecyclerItemViewHolder holder);
    void onClickPopUpMenu(int position,SplashModel splashModel);
}
