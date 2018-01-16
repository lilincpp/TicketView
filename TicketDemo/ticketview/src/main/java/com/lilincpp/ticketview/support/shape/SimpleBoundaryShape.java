package com.lilincpp.ticketview.support.shape;

import android.graphics.RectF;

import com.lilincpp.ticketview.IBoundaryShape;

/**
 * Created by lilin on 2018/1/12.
 */

public class SimpleBoundaryShape implements IBoundaryShape {

    private static final float DEFAULT_WIDTH = 32;
    private static final int DEFAULT_SPACE = 24;
    private static final int DEFAULT_ITEM_COUNT = -1;
    private static final int DEFAULT_SHADOW_PX = 16;

    private RectF mRectF = new RectF(
            -DEFAULT_WIDTH / 2,
            -DEFAULT_WIDTH / 2,
            DEFAULT_WIDTH / 2,
            DEFAULT_WIDTH / 2);
    private float mItemSpace = DEFAULT_SPACE;
    private int mItemCount = DEFAULT_ITEM_COUNT;
    private float mStartDrawWeight = 0;
    private boolean mStartDrawInCenter = false;
    private Style mStyle = Style.ROUND;
    private float mShadowPx = DEFAULT_SHADOW_PX;


    @Override
    public RectF getBounds() {
        return mRectF;
    }

    @Override
    public float getSpace() {
        return mItemSpace;
    }

    @Override
    public int getQuantity() {
        return mItemCount;
    }

    @Override
    public float getStartDrawPositionWeight() {
        return mStartDrawWeight;
    }

    @Override
    public boolean startDrawPositionInCenter() {
        return mStartDrawInCenter;
    }

    @Override
    public Style getStyle() {
        return mStyle;
    }

    @Override
    public float getWidth() {
        return mRectF.width();
    }

    @Override
    public float getHeight() {
        return mRectF.height();
    }

    @Override
    public float getShadowPx() {
        return mShadowPx;
    }

    public void setWidth(float width, float height) {
        mRectF.left = -width / 2;
        mRectF.right = width / 2;
        mRectF.top = -height / 2;
        mRectF.bottom = height / 2;
    }

    public void setItemSpace(float space) {
        mItemSpace = space;
    }

    public void setItemCount(int count) {
        mItemCount = count;
    }

    public void setStartDrawPositionWeight(float weight) {
        mStartDrawWeight = weight;
    }

    public void startDrawInCenter(boolean enable) {
        mStartDrawInCenter = enable;
    }

    public void setItemStyle(Style style) {
        mStyle = style;
    }

    public void setShadowPx(float px) {
        mShadowPx = px;
    }

}
