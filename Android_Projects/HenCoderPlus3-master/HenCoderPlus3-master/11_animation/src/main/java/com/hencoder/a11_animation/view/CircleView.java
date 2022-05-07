package com.hencoder.a11_animation.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.hencoder.a11_animation.Utils;

import androidx.annotation.Nullable;

public class CircleView extends View {
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private float radius = Utils.dpToPixel(50);

    public CircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawCircle(getWidth() / 2, getHeight() / 2, radius, paint);
    }
}
