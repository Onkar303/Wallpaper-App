package com.example.admin.myapplication.Animations;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.v7.widget.RecyclerView;

public class MyAnimationUtils {

    public static void Animate(RecyclerView.ViewHolder holder,boolean goesDown)
    {
        AnimatorSet  animatorSet=new AnimatorSet();


        ObjectAnimator translateY=ObjectAnimator.ofFloat(holder.itemView,"translationY",200,0);
        translateY.setDuration(1000);


        ObjectAnimator translateX=ObjectAnimator.ofFloat(holder.itemView,"translationX",-50,50,-40,40,-30,30,-20,20,-10,10,0);
        translateX.setDuration(1000);


        //ObjectAnimator rotateX=ObjectAnimator.ofFloat(holder.itemView,"rot")

        animatorSet.playTogether(translateY);
        animatorSet.start();
    }




}
