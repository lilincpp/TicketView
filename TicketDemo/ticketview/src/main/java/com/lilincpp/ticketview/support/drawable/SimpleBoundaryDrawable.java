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

/**
 * Created by lilin on 2018/1/12.
 */

public class SimpleBoundaryDrawable extends Drawable {

    private static final String TAG = "SimpleBoundaryDrawable";

    private static final int LEFT_BOUNDARY_INDEX = 0;
    private static final int TOP_BOUNDARY_INDEX = 1;
    private static final int RIGHT_BOUNDARY_INDEX = 2;
    private static final int BOTTOM_BOUNDARY_INDEX = 3;

    private IBoundaryShape[] mBoundaryShapes = new IBoundaryShape[4];
    private Paint mShapePaint, mLinePaint;
    private Path mContentPath;
    private float[] mShadowPadding = new float[4];
    private boolean mRounded; //是否开启圆角
    private float mRoundedRadius; //圆角半径

    private SimpleBoundaryDrawable(SimpleBoundaryDrawable.Builder builder) {
        mBoundaryShapes = builder.boundaryShapes;
        mContentPath = new Path();
        mRounded = builder.hasRounded;
        mRoundedRadius = builder.roundedRadius;
        initShape(builder);
        initBoundaryLine(builder);
        builder.shadowPx = builder.hasShadow ? builder.shadowPx : 0;
        calculatePadding(builder.shadowPx, builder.shadowOffsets);
    }

    private void initShape(SimpleBoundaryDrawable.Builder builder) {
        mShapePaint = new Paint();
        mShapePaint.setColor(builder.backgroundColor);
        mShapePaint.setStyle(Paint.Style.FILL);
        mShapePaint.setAntiAlias(true);
        if (builder.hasShadow)
            mShapePaint.setShadowLayer(builder.shadowPx, 0, 0, Color.GRAY);
    }

    private void initBoundaryLine(SimpleBoundaryDrawable.Builder builder) {
        if (builder.hasBoundaryLine) {
            mLinePaint = new Paint();
            mLinePaint.setColor(builder.lineColor);
            mLinePaint.setStyle(Paint.Style.STROKE);
            mLinePaint.setAntiAlias(true);
            mLinePaint.setStrokeWidth(builder.lineWidth);
        }
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        //测定轮廓路径
        mContentPath.reset();
        drawLeft(bounds);
        drawBottom(bounds);
        drawRight(bounds);
        drawTop(bounds);
        mContentPath.close();
    }

    @Override
    public void draw(Canvas canvas) {
        if (canvas != null) {
            //绘制轮廓
            canvas.drawPath(mContentPath, mShapePaint);
            if (mLinePaint != null) {
                //绘制边界线
                canvas.drawPath(mContentPath, mLinePaint);
            }
        }
    }

    private void drawLeft(final Rect bounds) {
        final IBoundaryShape shape = mBoundaryShapes[LEFT_BOUNDARY_INDEX];
        mContentPath.reset();
        mContentPath.moveTo(
                bounds.left + mShadowPadding[LEFT_BOUNDARY_INDEX],
                bounds.top + mShadowPadding[TOP_BOUNDARY_INDEX] + getRoundedOffset());
        if (shape == null) {
            mContentPath.lineTo(mShadowPadding[LEFT_BOUNDARY_INDEX], bounds.bottom - mShadowPadding[BOTTOM_BOUNDARY_INDEX]);
        } else {
            final float itemSpace = shape.getSpace();
            //-1则铺满整个边
            final int quantity = shape.getQuantity() == -1 ?
                    fillBoundaryCount(
                            getDrawHeight(),
                            itemSpace,
                            shape.getHeight())
                    : shape.getQuantity();
            //调整两边的空隙
            int adjustSpace = adjustLastSpace(getDrawHeight(), itemSpace, shape.getHeight(), quantity);
            //能绘制长度
            final float limitSize = bounds.bottom - mShadowPadding[BOTTOM_BOUNDARY_INDEX] - getRoundedOffset();
            final float startDrawSpace = getLeftStartDrawOffset();
            if (startDrawSpace != 0) {
                adjustSpace = 0;
            }
            if (shape.getStyle() == IBoundaryShape.Style.ROUND) {
                mContentPath.lineTo(
                        mShadowPadding[LEFT_BOUNDARY_INDEX],
                        bounds.top + itemSpace + mShadowPadding[TOP_BOUNDARY_INDEX] + startDrawSpace);
                shape.getBounds().offset(mShadowPadding[LEFT_BOUNDARY_INDEX], mShadowPadding[TOP_BOUNDARY_INDEX]);
                shape.getBounds().offset(0, itemSpace + shape.getHeight() / 2 + adjustSpace + startDrawSpace);
                for (int i = 0; i < quantity; ++i) {
                    mContentPath.arcTo(shape.getBounds(), -90, 180, false);
                    shape.getBounds().offset(0, shape.getHeight() + itemSpace);

                    if (shape.getBounds().bottom >= limitSize || (i + 1 >= quantity)) {
                        mContentPath.lineTo(mShadowPadding[LEFT_BOUNDARY_INDEX], limitSize);
                        return;
                    }
                }
            } else {
                //其他形状
            }
        }
    }

    private void drawBottom(final Rect bounds) {
        final IBoundaryShape shape = mBoundaryShapes[BOTTOM_BOUNDARY_INDEX];
        if (shape == null) {
            if (mRounded) {
                mContentPath.arcTo(getBottomLeftCorner(), -180, -90, false);
            }
            mContentPath.lineTo(
                    bounds.right - mShadowPadding[RIGHT_BOUNDARY_INDEX] - getRoundedOffset(),
                    bounds.bottom - mShadowPadding[BOTTOM_BOUNDARY_INDEX]);
        } else {
            final float itemSpace = shape.getSpace();
            final int quantity = shape.getQuantity() == -1 ?
                    fillBoundaryCount(
                            getDrawWidth(),
                            itemSpace,
                            shape.getWidth())
                    : shape.getQuantity();
            int adjustSpace = adjustLastSpace(getDrawWidth(), itemSpace, shape.getWidth(), quantity);
            final float limitSize = bounds.right - mShadowPadding[RIGHT_BOUNDARY_INDEX] - getRoundedOffset();
            final float startDrawSpace = getBottomStartDrawOffset();
            if (startDrawSpace != 0) {
                adjustSpace = 0;
            }
            if (shape.getStyle() == IBoundaryShape.Style.ROUND) {
                if (mRounded) {
                    mContentPath.arcTo(getBottomLeftCorner(), -180, -90, false);
                }
                shape.getBounds().offset(
                        mShadowPadding[LEFT_BOUNDARY_INDEX],
                        bounds.bottom - mShadowPadding[BOTTOM_BOUNDARY_INDEX]);
                shape.getBounds().offset(itemSpace + shape.getWidth() / 2 + adjustSpace + startDrawSpace, 0);
                for (int i = 0; i < quantity; ++i) {
                    mContentPath.arcTo(shape.getBounds(), -180, 180, false);
                    shape.getBounds().offset(shape.getWidth() + itemSpace, 0);
                    if (shape.getBounds().right >= limitSize
                            || (i + 1 >= quantity)) {

                        mContentPath.lineTo(
                                limitSize,
                                bounds.bottom - mShadowPadding[BOTTOM_BOUNDARY_INDEX]);
                        return;
                    }
                }
            } else {
                //其他形状
            }
        }
    }


    private void drawRight(final Rect bounds) {
        final IBoundaryShape shape = mBoundaryShapes[RIGHT_BOUNDARY_INDEX];
        if (shape == null) {
            if (mRounded) {
                mContentPath.arcTo(getBottomRightCorner(), -270, -90, false);
            }
            mContentPath.lineTo(
                    bounds.right - mShadowPadding[RIGHT_BOUNDARY_INDEX],
                    mShadowPadding[TOP_BOUNDARY_INDEX] + getRoundedOffset()
            );
        } else {
            final float itemSpace = shape.getSpace();
            final int quantity = shape.getQuantity() == -1 ?
                    fillBoundaryCount(
                            getDrawHeight(),
                            itemSpace,
                            shape.getHeight())
                    : shape.getQuantity();
            int adjustSpace = adjustLastSpace(getDrawHeight(), itemSpace, shape.getHeight(), quantity);
            final float limitSize = mShadowPadding[TOP_BOUNDARY_INDEX] + getRoundedOffset();
            final float startDrawSpace = getRightStartDrawOffset();
            if (startDrawSpace != 0) {
                adjustSpace = 0;
            }
            if (shape.getStyle() == IBoundaryShape.Style.ROUND) {
                shape.getBounds().offset(
                        bounds.right - mShadowPadding[RIGHT_BOUNDARY_INDEX],
                        bounds.bottom - mShadowPadding[BOTTOM_BOUNDARY_INDEX]);
                shape.getBounds().offset(0, -itemSpace - shape.getHeight() / 2 - adjustSpace - startDrawSpace);
                if (mRounded) {
                    mContentPath.arcTo(getBottomRightCorner(), -270, -90, false);
                }
                for (int i = 0; i < quantity; ++i) {
                    mContentPath.arcTo(shape.getBounds(), -270, 180, false);
                    shape.getBounds().offset(0, -shape.getHeight() - itemSpace);
                    if (shape.getBounds().top <= limitSize || (i + 1 >= quantity)) {
                        mContentPath.lineTo(bounds.right - mShadowPadding[RIGHT_BOUNDARY_INDEX], limitSize);
                        return;
                    }
                }
            } else {
                //其他形状
            }
        }
    }

    private void drawTop(final Rect bounds) {
        final IBoundaryShape shape = mBoundaryShapes[TOP_BOUNDARY_INDEX];
        if (shape == null) {
            if (mRounded) {
                mContentPath.arcTo(getTopRightCorner(), 0, -90, false);
                mContentPath.arcTo(getTopLeftCorner(), -90, -90, false);
            }
            mContentPath.lineTo(
                    mShadowPadding[LEFT_BOUNDARY_INDEX] + getRoundedOffset(),
                    mShadowPadding[TOP_BOUNDARY_INDEX]);
        } else {
            final float itemSpace = shape.getSpace();
            final int quantity = shape.getQuantity() == -1 ?
                    fillBoundaryCount(
                            getDrawWidth(),
                            itemSpace,
                            shape.getWidth())
                    : shape.getQuantity();
            int adjustSpace = adjustLastSpace(getDrawWidth(), itemSpace, shape.getWidth(), quantity);
            final float limitSize = mShadowPadding[LEFT_BOUNDARY_INDEX] + getRoundedOffset();
            final float startDrawSpace = getTopStartDrawOffset();
            if (startDrawSpace != 0) {
                adjustSpace = 0;
            }
            Log.e(TAG, "drawTop: adjustSpace=" + adjustSpace);
            Log.e(TAG, "drawTop: startDrawSpace=" + startDrawSpace);
            Log.e(TAG, "drawTop: "+(mBoundaryShapes[TOP_BOUNDARY_INDEX].getStartDrawPositionWeight() == 0) );
            if (shape.getStyle() == IBoundaryShape.Style.ROUND) {
                if (mRounded) {
                    mContentPath.arcTo(getTopRightCorner(), 0, -90, false);
                }
                shape.getBounds().offset(
                        bounds.right - mShadowPadding[RIGHT_BOUNDARY_INDEX] - getRoundedOffset() - startDrawSpace,
                        mShadowPadding[TOP_BOUNDARY_INDEX]);
                shape.getBounds().offset(-itemSpace - shape.getWidth() / 2 - adjustSpace - startDrawSpace, 0);
                for (int i = 0; i < quantity; ++i) {
                    mContentPath.arcTo(shape.getBounds(), -360, 180, false);
                    shape.getBounds().offset(-shape.getWidth() - itemSpace, 0);
                    if (shape.getBounds().left <= limitSize || (i + 1 >= quantity)) {
                        mContentPath.lineTo(limitSize, mShadowPadding[TOP_BOUNDARY_INDEX]);
                        if (mRounded) {
                            mContentPath.arcTo(getTopLeftCorner(), -90, -90, false);
                        }
                        return;
                    }
                }
            } else {
                //其他形状
            }
        }
    }

    private RectF getBottomLeftCorner() {
        return new RectF(
                getBounds().left + mShadowPadding[LEFT_BOUNDARY_INDEX],
                getBounds().bottom - mShadowPadding[BOTTOM_BOUNDARY_INDEX] - 2 * getRoundedOffset(),
                getBounds().left + mShadowPadding[LEFT_BOUNDARY_INDEX] + 2 * getRoundedOffset(),
                getBounds().bottom - mShadowPadding[BOTTOM_BOUNDARY_INDEX]
        );
    }

    private RectF getBottomRightCorner() {
        return new RectF(
                getBounds().right - mShadowPadding[RIGHT_BOUNDARY_INDEX] - 2 * getRoundedOffset(),
                getBounds().bottom - mShadowPadding[BOTTOM_BOUNDARY_INDEX] - 2 * getRoundedOffset(),
                getBounds().right - mShadowPadding[RIGHT_BOUNDARY_INDEX],
                getBounds().bottom - mShadowPadding[BOTTOM_BOUNDARY_INDEX]
        );
    }

    private RectF getTopRightCorner() {
        return new RectF(
                getBounds().right - mShadowPadding[RIGHT_BOUNDARY_INDEX] - 2 * getRoundedOffset(),
                getBounds().top + mShadowPadding[TOP_BOUNDARY_INDEX],
                getBounds().right - mShadowPadding[RIGHT_BOUNDARY_INDEX],
                getBounds().top + mShadowPadding[TOP_BOUNDARY_INDEX] + 2 * getRoundedOffset()
        );
    }

    private RectF getTopLeftCorner() {
        return new RectF(
                getBounds().left + mShadowPadding[LEFT_BOUNDARY_INDEX],
                getBounds().top + mShadowPadding[TOP_BOUNDARY_INDEX],
                getBounds().left + mShadowPadding[LEFT_BOUNDARY_INDEX] + 2 * getRoundedOffset(),
                getBounds().top + mShadowPadding[TOP_BOUNDARY_INDEX] + 2 * getRoundedOffset()
        );
    }

    private float getLeftStartDrawOffset() {
        return mBoundaryShapes[LEFT_BOUNDARY_INDEX] == null || mBoundaryShapes[LEFT_BOUNDARY_INDEX].getStartDrawPositionWeight() == 0 ? 0 :
                mBoundaryShapes[LEFT_BOUNDARY_INDEX].getStartDrawPositionWeight() * getDrawHeight()
                        - mBoundaryShapes[LEFT_BOUNDARY_INDEX].getHeight() / 2;
    }

    private float getRightStartDrawOffset() {
        return mBoundaryShapes[RIGHT_BOUNDARY_INDEX] == null || mBoundaryShapes[RIGHT_BOUNDARY_INDEX].getStartDrawPositionWeight() == 0 ? 0 :
                mBoundaryShapes[RIGHT_BOUNDARY_INDEX].getStartDrawPositionWeight() * getDrawHeight()
                        - mBoundaryShapes[RIGHT_BOUNDARY_INDEX].getHeight() / 2;
    }

    private float getTopStartDrawOffset() {
        return mBoundaryShapes[TOP_BOUNDARY_INDEX] == null || mBoundaryShapes[TOP_BOUNDARY_INDEX].getStartDrawPositionWeight() == 0 ? 0 :
                mBoundaryShapes[TOP_BOUNDARY_INDEX].getStartDrawPositionWeight() * getDrawWidth()
                        - mBoundaryShapes[TOP_BOUNDARY_INDEX].getWidth() / 2;
    }

    private float getBottomStartDrawOffset() {
        return mBoundaryShapes[BOTTOM_BOUNDARY_INDEX] == null || mBoundaryShapes[BOTTOM_BOUNDARY_INDEX].getStartDrawPositionWeight() == 0 ? 0 :
                mBoundaryShapes[BOTTOM_BOUNDARY_INDEX].getStartDrawPositionWeight() * getDrawWidth()
                        - mBoundaryShapes[BOTTOM_BOUNDARY_INDEX].getWidth() / 2;
    }

    private float getRoundedOffset() {
        return mRounded ? mRoundedRadius : 0f;
    }

    private int getDrawHeight() {
        return (int) (getBounds().bottom - mShadowPadding[TOP_BOUNDARY_INDEX] - mShadowPadding[BOTTOM_BOUNDARY_INDEX]
                - getRoundedOffset());
    }

    private int getDrawWidth() {
        return (int) (getBounds().right - mShadowPadding[LEFT_BOUNDARY_INDEX] - mShadowPadding[RIGHT_BOUNDARY_INDEX] - getRoundedOffset());
    }

    private int fillBoundaryCount(float boundaryWidth, float itemSpace, float itemSize) {
        if ((itemSize + itemSpace) > 0) {
            return (int) (boundaryWidth / (itemSize + itemSpace));
        } else {
            return 0;
        }
    }

    private int adjustLastSpace(int width, float itemSpace, float itemSize, int quantity) {
        return (int) (Math.abs((width - ((itemSize + itemSpace) * quantity + itemSpace))) / 2);
    }

    private void calculatePadding(float shadowPx, float[] offsets) {
        mShadowPadding[LEFT_BOUNDARY_INDEX] = shadowPx + offsets[LEFT_BOUNDARY_INDEX];
        mShadowPadding[TOP_BOUNDARY_INDEX] = shadowPx + offsets[TOP_BOUNDARY_INDEX];
        mShadowPadding[RIGHT_BOUNDARY_INDEX] = shadowPx + offsets[RIGHT_BOUNDARY_INDEX];
        mShadowPadding[BOTTOM_BOUNDARY_INDEX] = shadowPx + offsets[BOTTOM_BOUNDARY_INDEX];
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


    public static class Builder {
        private IBoundaryShape[] boundaryShapes = new IBoundaryShape[4];
        private float[] shadowOffsets = new float[4];
        private float shadowPx = 16;
        private int backgroundColor = Color.WHITE;
        private int lineColor = Color.GRAY;
        private float lineWidth = 2f;
        private float roundedRadius = 8;
        private boolean hasBoundaryLine, hasShadow, hasRounded;

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

        public Builder setBoundaryShape(IBoundaryShape left,
                                        IBoundaryShape top,
                                        IBoundaryShape right,
                                        IBoundaryShape bottom) {
            boundaryShapes[LEFT_BOUNDARY_INDEX] = left;
            boundaryShapes[TOP_BOUNDARY_INDEX] = top;
            boundaryShapes[RIGHT_BOUNDARY_INDEX] = right;
            boundaryShapes[BOTTOM_BOUNDARY_INDEX] = bottom;
            return this;
        }

        public Builder setLeftBoundaryShape(IBoundaryShape shape) {
            boundaryShapes[LEFT_BOUNDARY_INDEX] = shape;
            return this;
        }

        public Builder setTopBoundaryShape(IBoundaryShape shape) {
            boundaryShapes[TOP_BOUNDARY_INDEX] = shape;
            return this;
        }

        public Builder setRightBoundaryShape(IBoundaryShape shape) {
            boundaryShapes[RIGHT_BOUNDARY_INDEX] = shape;
            return this;
        }

        public Builder setBottomBoundaryShape(IBoundaryShape shape) {
            boundaryShapes[BOTTOM_BOUNDARY_INDEX] = shape;
            return this;
        }

        public Builder setShadowPx(float px) {
            shadowPx = px;
            if (px > 0) {
                hasShadow = true;
            }
            return this;
        }

        public Builder setBoundaryShadowOffset(float left, float top, float right, float bottom) {
            shadowOffsets[LEFT_BOUNDARY_INDEX] = left;
            shadowOffsets[TOP_BOUNDARY_INDEX] = top;
            shadowOffsets[RIGHT_BOUNDARY_INDEX] = right;
            shadowOffsets[BOTTOM_BOUNDARY_INDEX] = bottom;
            return this;
        }


        public Builder setStrokeColor(int color) {
            lineColor = color;
            return this;
        }

        public Builder setStrokeWidth(float size) {
            lineWidth = size;
            return this;
        }

        public Builder setRoundedRadius(float radius) {
            roundedRadius = radius;
            if (radius > 0) {
                hasRounded = true;
            }
            return this;
        }

        public Builder setShadowEnable(boolean enable) {
            hasShadow = enable;
            return this;
        }

        public Builder setBoundaryEnable(boolean enable) {
            hasBoundaryLine = enable;
            return this;
        }

        public Builder setRoundedEnable(boolean enable) {
            hasRounded = enable;
            return this;
        }

        public SimpleBoundaryDrawable create() {
            return new SimpleBoundaryDrawable(this);
        }
    }
}
