package com.lilincpp.ticketview;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Xfermode;
import android.graphics.drawable.Drawable;


/**
 * Created by Administrator on 2018/1/4.
 */

public abstract class TicketDrawable extends Drawable {


    private Drawable mBackground;//原背景
    private Paint mPaint;

    public TicketDrawable(Drawable background) {
        mBackground = background;
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    public void draw(Canvas canvas) {
        if (mBackground != null && canvas != null) {
            int layerId = canvas.saveLayer(0, 0, canvas.getWidth(), canvas.getHeight(), mPaint, Canvas.ALL_SAVE_FLAG);
            mBackground.draw(canvas);
            mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
            //绘制目标形状
            drawTargetShape(canvas, mPaint);
            mPaint.setXfermode(null);
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
