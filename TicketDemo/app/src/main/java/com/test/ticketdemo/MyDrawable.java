package com.test.ticketdemo;

import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;


/**
 * Created by Administrator on 2018/1/12.
 */

public class MyDrawable extends Drawable {

    private Paint mPaint, mPaint2;
    private Path mPath, mPath2;

    public MyDrawable() {
        mPaint = new Paint();
        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
        mPaint.setShadowLayer(16, 0, 0, Color.GRAY);
//        mPaint.setMaskFilter(new BlurMaskFilter(20, BlurMaskFilter.Blur.SOLID));
        mPaint2 = new Paint();
        mPaint2.setColor(Color.BLACK);
        mPaint2.setStyle(Paint.Style.STROKE);
        mPaint2.setStrokeWidth(8);
        mPath = new Path();
        mPath2 = new Path();
//        mPaint2.setShadowLayer(20,-7,0,Color.GRAY);
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        int offset = 16;
        mPath.moveTo(offset, offset);
        mPath.lineTo(offset, 20);
//        mPath.quadTo(simpleRoundShape.getWidth()/2 , 20+simpleRoundShape.getWidth()/2, 0, 20 + simpleRoundShape.getHeight());
        RectF rectF = new RectF(offset - 32 / 2,
                20 + offset,
                32 / 2 + offset,
                20 + 32 + offset);
        mPath.arcTo(rectF
                , -90, 180, false);
        int radius = 10;
        RectF coner = new RectF(offset, bounds.bottom - offset - 2 * radius, offset + 2 * radius, bounds.bottom - offset);
        mPath.arcTo(coner, -180, -90, false);
        //        mPath.lineTo(offset, bounds.bottom - offset - radius);
        mPath.lineTo(offset + 60, bounds.bottom - offset);
        RectF rectF1 = new RectF(offset + 60, bounds.bottom - offset - 32 / 2, offset + 60 + 32, bounds.bottom - offset + 32 / 2);
        mPath.arcTo(
                rectF1, -180, 180, false
        );
        mPath.lineTo(bounds.right - offset, bounds.bottom - offset);
        mPath.lineTo(bounds.right - offset, bounds.top + offset);
        mPath.lineTo(offset + 2 * radius, bounds.top + offset);
        mPath.arcTo(
                offset,
                offset,
                offset + 2 * radius,
                offset + 2 * radius,
                -90,
                -90,
                false
        );
        mPath.close();

    }


    @Override
    public void draw(@NonNull Canvas canvas) {
        Log.e(TAG, "draw: ");
        canvas.drawPath(mPath, mPaint);
//        canvas.drawPath(mPath, mPaint);
        canvas.drawPath(mPath, mPaint2);
//        mPaint.setStyle(Paint.Style.STROKE);
//        canvas.drawPath(mPath, mPaint);
    }

    private static final String TAG = "MyDrawable";

    @Override
    public void setAlpha(int alpha) {
        Log.e(TAG, "setAlpha: ");
    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        Log.e(TAG, "setColorFilter: ");
    }

    @Override
    public int getOpacity() {
        Log.e(TAG, "getOpacity: ");
        return PixelFormat.TRANSLUCENT;
    }
}
