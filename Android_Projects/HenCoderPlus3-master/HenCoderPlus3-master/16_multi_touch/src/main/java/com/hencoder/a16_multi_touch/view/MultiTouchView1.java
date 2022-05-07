package com.hencoder.a16_multi_touch.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.hencoder.a16_multi_touch.Utils;

public class MultiTouchView1 extends View {
    private static final float IMAGE_WIDTH = Utils.dpToPixel(200);

    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Bitmap bitmap;

    float offsetX;
    float offsetY;
    float downX;
    float downY;
    float imageOffsetX;
    float imageOffsetY;
    int trackingPointerId;

    public MultiTouchView1(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        bitmap = Utils.getAvatar(getResources(), (int) IMAGE_WIDTH);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawBitmap(bitmap, offsetX, offsetY, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                trackingPointerId = event.getPointerId(0);
                downX = event.getX();
                downY = event.getY();
                imageOffsetX = offsetX;
                imageOffsetY = offsetY;
                break;
            case MotionEvent.ACTION_MOVE:
                int index = event.findPointerIndex(trackingPointerId);
                offsetX = imageOffsetX + event.getX(index) - downX;
                offsetY = imageOffsetY + event.getY(index) - downY;
                invalidate();
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                int actionIndex = event.getActionIndex();
                trackingPointerId = event.getPointerId(actionIndex);
                downX = event.getX(actionIndex);
                downY = event.getY(actionIndex);
                imageOffsetX = offsetX;
                imageOffsetY = offsetY;
                break;
            case MotionEvent.ACTION_POINTER_UP:
                actionIndex = event.getActionIndex();
                int pointerId = event.getPointerId(actionIndex);
                if (pointerId == trackingPointerId) {
                    int newIndex;
                    if (actionIndex == event.getPointerCount() - 1) {
                        newIndex = event.getPointerCount() - 2;
                    } else {
                        newIndex = event.getPointerCount() - 1;
                    }
                    trackingPointerId = event.getPointerId(newIndex);
                    downX = event.getX(newIndex);
                    downY = event.getY(newIndex);
                    imageOffsetX = offsetX;
                    imageOffsetY = offsetY;
                }
        }
        return true;
    }
}
