package com.example.admin.myapplication;

import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Explode;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.admin.myapplication.Animations.BounceAnimation;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener{

    Button button;
    RelativeLayout relativeLayout;
    AnimationDrawable animationDrawable;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main2);
        // onCreate


        init();
    }

    public void init()
    {
        button=(Button)findViewById(R.id.button1);
        button.setOnClickListener(this);

        relativeLayout=(RelativeLayout)findViewById(R.id.myrelativelayouyt);
        animationDrawable =(AnimationDrawable)relativeLayout.getBackground();
        animationDrawable.setEnterFadeDuration(5000);
        animationDrawable.setExitFadeDuration(2000);
        //backgroundTransitionAnimation();


    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.button1:
               //scaleAnimtionForButton();
                TranslateAnimation();
                //backgroundTransitionAnimation();
                break;
        }


    }


    public void scaleAnimtionForButton()
    {
        ObjectAnimator objectAnimator=ObjectAnimator.ofFloat(button,"scaleX",0.1f);
        objectAnimator.setDuration(1000);
        AnimatorSet animatorSet=new AnimatorSet();
        animatorSet.play(objectAnimator);
        animatorSet.start();


    }

    public void TranslateAnimation()
    {
        Animation animation=AnimationUtils.loadAnimation(this,R.anim.animtion1);
        //animation.setInterpolator(new BounceAnimation(0.2,20));
        button.setAnimation(animation);
        button.startAnimation(animation);
    }

    public void backgroundTransitionAnimation()
    {
        int colorFrom = getResources().getColor(R.color.colorAccent);
        int colorTo = getResources().getColor(R.color.colorPrimary);
        int colorTo1 = getResources().getColor(R.color.colorPrimaryDark);


        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
        colorAnimation.setDuration(2000); // milliseconds
        colorAnimation.setRepeatMode(ValueAnimator.REVERSE);
        colorAnimation.setRepeatCount(Animation.INFINITE);
        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                relativeLayout.setBackgroundColor((int) animator.getAnimatedValue());
            }

        });
        colorAnimation.start();


    }

    @Override
    protected void onResume() {
        super.onResume();
        animationDrawable.start();
    }
}
