package com.example.admin.myapplication.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.admin.myapplication.LoginActivity;
import com.example.admin.myapplication.Main2Activity;
import com.example.admin.myapplication.R;

public class Fragment1 extends Fragment implements View.OnClickListener {

    Button button;

    ImageView imageView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment1,container,false);
        imageView=(ImageView)v.findViewById(R.id.image32);

        Glide.with(getContext()).load(getResources().getDrawable(R.drawable.paper1)).into(imageView);

        //button=(Button)v.findViewById(R.id.button);
        //button.setOnClickListener(this);
        return v;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {

        }
    }
}
