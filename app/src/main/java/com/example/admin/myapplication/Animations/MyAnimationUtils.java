package com.example.admin.myapplication.Animations;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.v7.widget.RecyclerView;

public class MyAnimationUtils {

    public static void Animate(RecyclerView.ViewHolder holder,boolean goesDown)
    {
        if(goesDown)
        {
            AnimatorSet  animatorSet=new AnimatorSet();


            ObjectAnimator translateY=ObjectAnimator.ofFloat(holder.itemView,"translationY",200,0);
            translateY.setDuration(1000);


            //ObjectAnimator rotateX=ObjectAnimator.ofFloat(holder.itemView,"rot")

            animatorSet.playTogether(translateY);
            animatorSet.start();
        }

    }




}
