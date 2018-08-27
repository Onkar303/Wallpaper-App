package com.example.admin.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class CustomView extends View {


    private static final int START_ANGLE_POINT = 90;

    final Paint paint;
    final RectF rect;

    private float angle;
    public CustomView(Context context) {
        super(context);

         final int strokeWidth = 40;

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(strokeWidth);
        //Circle color
        paint.setColor(Color.RED);

        //size 200x200 example
        rect = new RectF(strokeWidth, strokeWidth, 200 + strokeWidth, 200 + strokeWidth);

        //Initial Angle (optional, it can be zero)
        setAngle(120);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawArc(rect, START_ANGLE_POINT, getAngle(), false, paint);
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }
}
