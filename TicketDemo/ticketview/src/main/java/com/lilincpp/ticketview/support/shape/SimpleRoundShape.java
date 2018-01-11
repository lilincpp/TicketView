package com.lilincpp.ticketview.support.shape;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.lilincpp.ticketview.IBoundaryShape;

/**
 * Created by lilin on 2018/1/5.
 */

public class SimpleRoundShape implements IBoundaryShape {
    private static final String TAG = "SimpleRoundShape";

    private static final int DEFAULT_RADIUS = 32;
    private static final int DEFAULT_QUANTITY = -1;
    private int mQuantity = DEFAULT_QUANTITY;
    private int mRadius;
    private float mDividingSpace;
    private float mDrawWeight;

    public SimpleRoundShape() {

    }

    public SimpleRoundShape(int quantity, int radius, float space, float drawWeight) {
        this.mQuantity = quantity;
        this.mRadius = radius;
        this.mDividingSpace = space;
        this.mDrawWeight = drawWeight;
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        canvas.drawCircle(canvas.getWidth() / 2, canvas.getHeight() / 2, getRadius(), paint);
    }

    @Override
    public float getDividingSpace() {
        return (mDividingSpace == 0 ? getRadius() / 2 : mDividingSpace);
    }

    @Override
    public float getStartDrawWeight() {
        return mDrawWeight;
    }

    @Override
    public int getWidth() {
        return getRadius() * 2;
    }

    @Override
    public int getHeight() {
        return getRadius() * 2;
    }

    @Override
    public int getCount() {
        return mQuantity;
    }

    @Override
    public Rect getMarginBounds() {
        return new Rect(-getRadius(), -getRadius(), -getRadius(), -getRadius());
    }

    public void setRadius(int radius) {
        mRadius = radius;
    }

    public int getRadius() {
        return mRadius == 0 ? DEFAULT_RADIUS : mRadius;
    }

    public void setQuantity(int quantity) {
        mQuantity = quantity;
    }

    public void setStartDrawGravity(float gravityWeight) {
        mDrawWeight = gravityWeight;
    }

    public void setDividingSpace(float dividingSpace) {
        this.mDividingSpace = dividingSpace;
    }
}
