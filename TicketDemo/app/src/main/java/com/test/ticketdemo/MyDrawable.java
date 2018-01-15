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
        mPath.lineTo(offset, bounds.bottom - offset);
        mPath.lineTo(offset + 20, bounds.bottom - offset);
        RectF rectF1 = new RectF(offset + 20, bounds.bottom - offset - 32 / 2, offset + 20 + 32, bounds.bottom - offset + 32 / 2);
        mPath.arcTo(
                rectF1,
                -180, 180, false
        );
        mPath.lineTo(bounds.right - offset, bounds.bottom - offset);
        mPath.lineTo(bounds.right - offset, bounds.top + offset);
        mPath.close();

    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        int offset = 100;
        mPath2.moveTo(offset, offset);
        mPath2.lineTo(offset,canvas.getHeight()-offset);
        mPath2.lineTo(canvas.getWidth()-offset,canvas.getHeight()-offset);
        mPath2.lineTo(canvas.getWidth()-offset,offset);
        mPath2.close();
        canvas.drawPath(mPath2, mPaint);
//        canvas.drawPath(mPath, mPaint);
//        canvas.drawPath(mPath, mPaint2);
//        mPaint.setStyle(Paint.Style.STROKE);
//        canvas.drawPath(mPath, mPaint);
    }

    @Override
    public void setAlpha(int alpha) {

    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }
}
