package com.lilincpp.ticketview;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.util.Log;


/**
 * Created by lilin on 2018/1/4.
 */

public abstract class TicketDrawable extends Drawable {

    private static final String TAG = "TicketDrawable";

    private Drawable mBackground;//原背景
    private Paint mPaint;

    public TicketDrawable(Drawable background) {
        mBackground = background;
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.WHITE);
    }

    @Override
    public void draw(Canvas canvas) {
        if (mBackground != null && canvas != null) {
            mBackground.setBounds(getBounds());//在draw()方法前，必须调用该方法-设置其边界范围
            int layerId = canvas.saveLayer(0, 0, canvas.getWidth(), canvas.getHeight(), mPaint, Canvas.ALL_SAVE_FLAG);
            mBackground.draw(canvas);
            //绘制目标形状
            drawTargetShape(canvas, mPaint);
            canvas.restoreToCount(layerId);
        }
    }

    public abstract void drawTargetShape(Canvas canvas, Paint paint);


    public void setBackground(Drawable background) {
        mBackground = background;
    }

    @Override
    public void setAlpha(int alpha) {
        if (mBackground != null) {
            mBackground.setAlpha(alpha);
        }
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        if (mBackground != null) {
            mBackground.setColorFilter(colorFilter);
        }
    }

    @Override
    public int getOpacity() {
        if (mBackground != null) {
            return mBackground.getOpacity();
        }
        return PixelFormat.OPAQUE;
    }


}
