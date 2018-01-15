package com.lilincpp.ticketview.support.drawable;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.lilincpp.ticketview.IBoundaryShape;
import com.lilincpp.ticketview.R;

/**
 * Created by Administrator on 2018/1/12.
 */

public class SimpleBoundaryDrawable extends Drawable {

    private static final String TAG = "SimpleBoundaryDrawable";

    private static final int LEFT_BOUNDARY_INDEX = 0;
    private static final int TOP_BOUNDARY_INDEX = 1;
    private static final int RIGHT_BOUNDARY_INDEX = 2;
    private static final int BOTTOM_BOUNDARY_INDEX = 3;

    private IBoundaryShape[] mBoundaryShapes = new IBoundaryShape[4];
    private int mBackgroundColor = Color.WHITE;
    private Paint mShapePaint, mLinePaint;
    private Path mContentPath;
    private float[] mPadding = new float[4];

    private SimpleBoundaryDrawable(SimpleBoundaryDrawable.Builder builder) {
        mBoundaryShapes = builder.boundaryShapes;
        mBackgroundColor = builder.backgroundColor;
        mContentPath = new Path();
        mShapePaint = new Paint();
        mShapePaint.setColor(mBackgroundColor);
        mShapePaint.setStyle(Paint.Style.FILL);
        mShapePaint.setAntiAlias(true);
        mShapePaint.setShadowLayer(16, 0, 0, Color.GRAY);
        calculatePadding();
    }


    @Override
    public void draw(Canvas canvas) {
        if (canvas != null) {
            drawLeft(canvas);
            drawBottom(canvas);
            drawRight(canvas);
            drawTop(canvas);
            canvas.drawPath(mContentPath, mShapePaint);
        }
    }

    private void drawLeft(Canvas canvas) {
        final IBoundaryShape shape = mBoundaryShapes[LEFT_BOUNDARY_INDEX];
        mContentPath.moveTo(mPadding[LEFT_BOUNDARY_INDEX], mPadding[TOP_BOUNDARY_INDEX]);
        if (shape == null) {
            mContentPath.lineTo(mPadding[LEFT_BOUNDARY_INDEX], canvas.getHeight() - mPadding[BOTTOM_BOUNDARY_INDEX]);
        } else {
            final float itemSpace = shape.getSpace();
            final int quantity = shape.getQuantity() == -1 ?
                    fillBoundaryCount(
                            canvas.getHeight() - mPadding[TOP_BOUNDARY_INDEX] - mPadding[BOTTOM_BOUNDARY_INDEX],
                            itemSpace,
                            shape.getHeight())
                    : shape.getQuantity();
            if (shape.getStyle() == IBoundaryShape.Style.ROUND) {
                float y = itemSpace + mPadding[TOP_BOUNDARY_INDEX];
                mContentPath.lineTo(mPadding[LEFT_BOUNDARY_INDEX], y);
                shape.getBounds().offset(mPadding[LEFT_BOUNDARY_INDEX], mPadding[TOP_BOUNDARY_INDEX]);
                for (int i = 0; i < quantity; ++i) {
                    shape.getBounds().offset(0, shape.getHeight() + itemSpace + shape.getHeight() / 2);
                    mContentPath.arcTo(shape.getBounds(), -90, 180, false);
                    y = y + shape.getHeight() + itemSpace;
                    if (y >= canvas.getHeight() - mPadding[BOTTOM_BOUNDARY_INDEX]) {
                        y = canvas.getHeight() - mPadding[BOTTOM_BOUNDARY_INDEX];
                        mContentPath.lineTo(mPadding[LEFT_BOUNDARY_INDEX], y);
                        break;
                    }
                    mContentPath.lineTo(mPadding[LEFT_BOUNDARY_INDEX], y);
                }
            } else {
                //其他形状
            }
        }
    }

    private void drawBottom(Canvas canvas) {
        final IBoundaryShape shape = mBoundaryShapes[BOTTOM_BOUNDARY_INDEX];
        if (shape == null) {
            mContentPath.lineTo(
                    canvas.getWidth() - mPadding[RIGHT_BOUNDARY_INDEX],
                    canvas.getHeight() - mPadding[BOTTOM_BOUNDARY_INDEX]);
        } else {
            final float itemSpace = shape.getSpace();
            final int quantity = shape.getQuantity() == -1 ?
                    fillBoundaryCount(
                            canvas.getWidth() - mPadding[LEFT_BOUNDARY_INDEX] - mPadding[RIGHT_BOUNDARY_INDEX],
                            itemSpace,
                            shape.getWidth())
                    : shape.getQuantity();
            if (shape.getStyle() == IBoundaryShape.Style.ROUND) {
                float x = itemSpace + mPadding[LEFT_BOUNDARY_INDEX];
                mContentPath.lineTo(x, canvas.getHeight() - mPadding[BOTTOM_BOUNDARY_INDEX]);
                shape.getBounds().offset(0,
                        canvas.getHeight() - mPadding[BOTTOM_BOUNDARY_INDEX]);
                for (int i = 0; i < quantity; ++i) {
                    shape.getBounds().offset(shape.getWidth() + itemSpace + shape.getWidth() / 2, 0);
                    mContentPath.arcTo(shape.getBounds(), -180, 180, false);
                    x = x + shape.getWidth() + itemSpace;
                    if (x >= canvas.getWidth() - mPadding[RIGHT_BOUNDARY_INDEX]) {
                        x = canvas.getWidth() - mPadding[RIGHT_BOUNDARY_INDEX];
                        mContentPath.lineTo(x, canvas.getHeight() - mPadding[BOTTOM_BOUNDARY_INDEX]);
                        break;
                    }
                    mContentPath.lineTo(x, canvas.getHeight() - mPadding[BOTTOM_BOUNDARY_INDEX]);
                }
            } else {
                //其他形状
            }
        }
    }


    private void drawRight(Canvas canvas) {
        final IBoundaryShape shape = mBoundaryShapes[RIGHT_BOUNDARY_INDEX];
        if (shape == null) {
            mContentPath.lineTo(canvas.getWidth() - mPadding[RIGHT_BOUNDARY_INDEX],
                    mPadding[TOP_BOUNDARY_INDEX]);
        } else {
            final float itemSpace = shape.getSpace();
            final int quantity = shape.getQuantity() == -1 ?
                    fillBoundaryCount(
                            canvas.getHeight() - mPadding[TOP_BOUNDARY_INDEX] - mPadding[BOTTOM_BOUNDARY_INDEX],
                            itemSpace,
                            shape.getHeight())
                    : shape.getQuantity();
            if (shape.getStyle() == IBoundaryShape.Style.ROUND) {
                float y = canvas.getHeight() - mPadding[BOTTOM_BOUNDARY_INDEX];
                shape.getBounds().offset(canvas.getWidth() - mPadding[RIGHT_BOUNDARY_INDEX], y);
                y = y - itemSpace;
                for (int i = 0; i < quantity; ++i) {
                    shape.getBounds().offset(0, -shape.getHeight() - shape.getHeight() / 2 - itemSpace);
                    mContentPath.arcTo(shape.getBounds(), -270, 180, false);
                    y = y - shape.getHeight() - itemSpace;
                    if (y <= mPadding[TOP_BOUNDARY_INDEX]) {
                        y = mPadding[TOP_BOUNDARY_INDEX];
                        mContentPath.lineTo(canvas.getWidth()-mPadding[RIGHT_BOUNDARY_INDEX],y);
                        break;
                    }
                    mContentPath.lineTo(canvas.getWidth()-mPadding[RIGHT_BOUNDARY_INDEX],y);
                }
            } else {
                //其他形状
            }
        }
    }

    private void drawTop(Canvas canvas) {
        final IBoundaryShape shape = mBoundaryShapes[TOP_BOUNDARY_INDEX];
        if (shape == null) {
            mContentPath.lineTo(
                    mPadding[LEFT_BOUNDARY_INDEX],
                    mPadding[TOP_BOUNDARY_INDEX]);
        } else {
            final float itemSpace = shape.getSpace();
            final int quantity = shape.getQuantity() == -1 ?
                    fillBoundaryCount(
                            canvas.getWidth() - mPadding[LEFT_BOUNDARY_INDEX] - mPadding[RIGHT_BOUNDARY_INDEX],
                            itemSpace,
                            shape.getWidth())
                    : shape.getQuantity();
            if (shape.getStyle() == IBoundaryShape.Style.ROUND) {
                float x = canvas.getWidth() - mPadding[RIGHT_BOUNDARY_INDEX] - itemSpace;
                mContentPath.lineTo(x, mPadding[TOP_BOUNDARY_INDEX]);
                shape.getBounds().offset(x,
                        mPadding[TOP_BOUNDARY_INDEX]);
                for (int i = 0; i < quantity; ++i) {
                    shape.getBounds().offset(-shape.getWidth()-itemSpace - shape.getWidth() / 2, 0);
                    mContentPath.arcTo(shape.getBounds(), -360, 180, false);
                    x = x - shape.getWidth() - itemSpace;
                    if (x <= mPadding[LEFT_BOUNDARY_INDEX]) {
                        x = mPadding[LEFT_BOUNDARY_INDEX];
                        mContentPath.lineTo(x, mPadding[TOP_BOUNDARY_INDEX]);
                        break;
                    }
                    mContentPath.lineTo(x, mPadding[TOP_BOUNDARY_INDEX]);
                }
            } else {
                //其他形状
            }
        }
    }


    private int fillBoundaryCount(float boundaryWidth, float itemSpace, float itemSize) {
        if ((itemSize + itemSpace) > 0) {
            return (int) (boundaryWidth / (itemSize + itemSpace));
        } else {
            return 0;
        }
    }

    private void calculatePadding() {
        mPadding[LEFT_BOUNDARY_INDEX] =
                mBoundaryShapes[LEFT_BOUNDARY_INDEX] == null ?
                        0 : mBoundaryShapes[LEFT_BOUNDARY_INDEX].getShadowPx();
        mPadding[TOP_BOUNDARY_INDEX] =
                mBoundaryShapes[TOP_BOUNDARY_INDEX] == null ?
                        0 : mBoundaryShapes[TOP_BOUNDARY_INDEX].getShadowPx();
        mPadding[RIGHT_BOUNDARY_INDEX] =
                mBoundaryShapes[RIGHT_BOUNDARY_INDEX] == null ?
                        0 : mBoundaryShapes[RIGHT_BOUNDARY_INDEX].getShadowPx();
        mPadding[BOTTOM_BOUNDARY_INDEX] =
                mBoundaryShapes[BOTTOM_BOUNDARY_INDEX] == null ?
                        0 : mBoundaryShapes[BOTTOM_BOUNDARY_INDEX].getShadowPx();
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
        return PixelFormat.TRANSLUCENT;
    }

    private static class Position {
        float x, y;

        public Position(float x, float y) {
            this.x = x;
            this.y = y;
        }
    }

    public static class Builder {
        private IBoundaryShape[] boundaryShapes
                = new IBoundaryShape[4];
        private int backgroundColor = Color.WHITE;

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
