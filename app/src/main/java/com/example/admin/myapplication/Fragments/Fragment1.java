package com.example.admin.myapplication.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.admin.myapplication.R;

public class Fragment1 extends Fragment implements View.OnClickListener {

    Button button;

    Context context;
    ImageView imageView;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context=context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.intro_screen_fragment1,container,false);
        imageView=(ImageView)v.findViewById(R.id.image32);

        //
        try{
           // Glide.with(context).load(context.getResources().getDrawable(R.drawable.paper1)).into(imageView);
            imageView.setImageResource(R.drawable.paper1);
        }catch (Exception e)
        {
            e.printStackTrace();
        }


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
