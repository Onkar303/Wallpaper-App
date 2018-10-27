package com.example.admin.myapplication.Animations;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.view.View;

public class AlertDialogAnimation {

    public static void animation(View v)
    {

        AnimatorSet animatorSet=new AnimatorSet();

        ObjectAnimator animator=ObjectAnimator.ofFloat(v,"translationY",300,0);
        animator.setDuration(200);

        animatorSet.playTogether(animator);
        animatorSet.start();
    }
}
