package com.lilincpp.ticketview.support.drawable;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;

import com.lilincpp.ticketview.IBoundaryShape;

/**
 * Created by Administrator on 2018/1/12.
 */

public class SimpleBoundaryDrawable extends Drawable {

    private static final int LEFT_BOUNDARY_INDEX = 0;
    private static final int TOP_BOUNDARY_INDEX = 1;
    private static final int RIGHT_BOUNDARY_INDEX = 2;
    private static final int BOTTOM_BOUNDARY_INDEX = 3;

    private IBoundaryShape[] mBoundaryShapes = new IBoundaryShape[4];

    private Paint mShapePaint, mLinePaint;

    private SimpleBoundaryDrawable(SimpleBoundaryDrawable.Builder builder) {
        mBoundaryShapes = builder.boundaryShapes;
        mShapePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    public void draw(Canvas canvas) {

    }

    @Override
    public void setAlpha(int alpha) {
        setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        if (getAlpha() == 0) {
            return PixelFormat.TRANSPARENT;
        }
        if (getAlpha() == 255) {
            return PixelFormat.OPAQUE;
        }
        return PixelFormat.TRANSLUCENT;
    }

    public static class Builder {
        private IBoundaryShape[] boundaryShapes
                = new IBoundaryShape[4];
        private int backgroundColor;

        public Builder setBackgroundColor(int color) {
            backgroundColor = color;
            return this;
        }

        public Builder setBackgroundColor(ColorDrawable drawable) {
            if (drawable != null) {
                backgroundColor = drawable.getColor();
            }
            return this;
        }

        public Builder setBoundaryShape(IBoundaryShape left, IBoundaryShape top, IBoundaryShape right, IBoundaryShape bottom) {
            boundaryShapes[LEFT_BOUNDARY_INDEX] = left;
            boundaryShapes[TOP_BOUNDARY_INDEX] = top;
            boundaryShapes[RIGHT_BOUNDARY_INDEX] = right;
            boundaryShapes[BOTTOM_BOUNDARY_INDEX] = bottom;
            return this;
        }

        public SimpleBoundaryDrawable create() {
            return new SimpleBoundaryDrawable(this);
        }
    }
}
