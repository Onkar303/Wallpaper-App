package com.example.admin.myapplication.Animations;

import android.view.animation.Animation;

public class BounceAnimation implements android.view.animation.Interpolator {

    double amplitude=1;
    double frquency=10;

    public BounceAnimation(double amplitude,double frequency)
    {
        this.amplitude=amplitude;
        this.frquency=frequency;
    }

    @Override
    public float getInterpolation(float input) {
        return (float) (-1 * Math.pow(Math.E, -input/ amplitude) *
                Math.cos(frquency * input) + 1);
    }
}
