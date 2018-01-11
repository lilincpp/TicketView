package com.lilincpp.ticketview;

import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

import static android.view.View.LAYER_TYPE_SOFTWARE;


/**
 * Created by colin on 2018/1/4.
 * <p>
 * 如果需要
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
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.GRAY);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(8);
//        paint.setShadowLayer(20,0,0,Color.BLACK);
        paint.setMaskFilter(new BlurMaskFilter(20,BlurMaskFilter.Blur.NORMAL));
        Path[] lines = test();
        Path[] shapes = test2();

        for (int i = 0; i < lines.length; ++i) {
            canvas.drawPath(lines[i], paint);
        }

        for (int i = 0; i < shapes.length; ++i) {
            canvas.drawPath(shapes[i], paint);
        }


    }

    /**
     * 绘制任何你想要的形状，并且你可以通过为Paint设置{@link android.graphics.PorterDuff.Mode}来达到你想要的效果
     *
     * @param canvas
     * @param paint
     */
    public abstract void drawTargetShape(Canvas canvas, Paint paint);

    public abstract Path[] test();

    public abstract Path[] test2();


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
