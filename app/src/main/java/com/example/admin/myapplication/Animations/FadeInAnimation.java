package com.example.admin.myapplication.Animations;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.v7.app.AlertDialog;
import android.view.View;

public class FadeInAnimation {
    public static void startFadeInAnimation(View v)
    {
        AnimatorSet set=new AnimatorSet();

        ObjectAnimator animator=ObjectAnimator.ofFloat(v,"alpha",0.25f,1);
        animator.setDuration(1000);

        ObjectAnimator scaleX=ObjectAnimator.ofFloat(v,"scaleX",0.5f,1);
        animator.setDuration(1000);


        ObjectAnimator scaleY=ObjectAnimator.ofFloat(v,"scaleY",0.5f,1);
        animator.setDuration(1000);

        set.playTogether(scaleX,scaleY);
        set.start();


    }

}
