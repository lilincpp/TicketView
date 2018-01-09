package com.lilincpp.ticketview.support.shape;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import com.lilincpp.ticketview.IBoundaryShape;
import com.lilincpp.ticketview.TicketParam;

/**
 * Created by Administrator on 2018/1/5.
 */

public class RoundBoundaryShape implements IBoundaryShape {
    private static final String TAG = "RoundBoundaryShape";

    private static final int DEFAULT_RADIUS = 32;
    private static final int DEFAULT_QUANTITY = -1;
    private int mQuantity;
    private int mRadius;
    private TicketParam.DrawGravity mStartDrawSpace = TicketParam.DrawGravity.START;

    @Override
    public void draw(Canvas canvas, Paint paint) {
        canvas.drawCircle(canvas.getWidth() / 2, canvas.getHeight() / 2, getRadius(), paint);
    }

    @Override
    public float getDividingSpace() {
        return getRadius() / 2;
    }

    @Override
    public TicketParam.DrawGravity getStartDrawGravity() {
        return mStartDrawSpace;
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
        return mQuantity == 0 ? DEFAULT_QUANTITY : mQuantity;
    }

    @Override
    public Rect getMarginBounds() {
        return new Rect(-getRadius() , -getRadius(), -getRadius() , -getRadius() );
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

    public void setStartDrawGravity(TicketParam.DrawGravity gravity) {
        mStartDrawSpace = gravity;
    }
}
