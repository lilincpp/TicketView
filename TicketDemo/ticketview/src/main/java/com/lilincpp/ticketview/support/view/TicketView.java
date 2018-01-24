package com.lilincpp.ticketview.support.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.FrameLayout;

import com.lilincpp.ticketview.ICustomShape;
import com.lilincpp.ticketview.R;
import com.lilincpp.ticketview.support.drawable.SimpleBoundaryDrawable;
import com.lilincpp.ticketview.support.shape.SimpleBoundaryShape;

/**
 * Created by colin on 2018/1/15.
 */

public class TicketView extends FrameLayout {

    private static final int BOUNDARY_LEFT = 1;
    private static final int BOUNDARY_TOP = 2;
    private static final int BOUNDARY_RIGHT = 4;
    private static final int BOUNDARY_BOTTOM = 8;

    public TicketView(Context context) {
        super(context);
        initView(context, null);
    }

    public TicketView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public TicketView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        SimpleBoundaryDrawable.Builder builder = new SimpleBoundaryDrawable.Builder();
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TicketView);

            final int showMask = a.getInt(R.styleable.TicketView_boundary_show, 0);

            if ((showMask & BOUNDARY_LEFT) == BOUNDARY_LEFT) {
                final int quantity = a.getInt(
                        R.styleable.TicketView_boundary_leftItemQuantity, -1);
                final int itemSpace = a.getDimensionPixelSize(
                        R.styleable.TicketView_boundary_leftItemSpace, SimpleBoundaryShape.DEFAULT_ITEM_SPACE);
                final int radius = a.getDimensionPixelSize(
                        R.styleable.TicketView_boundary_leftRadius, SimpleBoundaryShape.DEFAULT_ITEM_RADIUS);
                final float startWeight = a.getFloat(
                        R.styleable.TicketView_boundary_leftDrawWeight, 0f);
                SimpleBoundaryShape shape = new SimpleBoundaryShape();
                shape.setItemCount(quantity);
                shape.setItemSpace(itemSpace);
                shape.setRadius(radius);
                shape.setStartDrawPositionWeight(startWeight);
                builder.setLeftBoundaryShape(shape);
            }

            if ((showMask & BOUNDARY_RIGHT) == BOUNDARY_RIGHT) {
                final int quantity = a.getInt(
                        R.styleable.TicketView_boundary_rightItemQuantity, -1);
                final int itemSpace = a.getDimensionPixelSize(
                        R.styleable.TicketView_boundary_rightItemSpace, SimpleBoundaryShape.DEFAULT_ITEM_SPACE);
                final int radius = a.getDimensionPixelSize(
                        R.styleable.TicketView_boundary_rightRadius, SimpleBoundaryShape.DEFAULT_ITEM_RADIUS);
                final float startWeight = a.getFloat(
                        R.styleable.TicketView_boundary_rightDrawWeight, 0f);
                SimpleBoundaryShape shape = new SimpleBoundaryShape();
                shape.setItemCount(quantity);
                shape.setItemSpace(itemSpace);
                shape.setRadius(radius);
                shape.setStartDrawPositionWeight(startWeight);
                builder.setRightBoundaryShape(shape);
            }

            if ((showMask & BOUNDARY_TOP) == BOUNDARY_TOP) {
                final int quantity = a.getInt(
                        R.styleable.TicketView_boundary_topItemQuantity, -1);
                final int itemSpace = a.getDimensionPixelSize(
                        R.styleable.TicketView_boundary_topItemSpace, SimpleBoundaryShape.DEFAULT_ITEM_SPACE);
                final int radius = a.getDimensionPixelSize(
                        R.styleable.TicketView_boundary_topRadius, SimpleBoundaryShape.DEFAULT_ITEM_RADIUS);
                final float startWeight = a.getFloat(
                        R.styleable.TicketView_boundary_topDrawWeight, 0f);
                SimpleBoundaryShape shape = new SimpleBoundaryShape();
                shape.setItemCount(quantity);
                shape.setItemSpace(itemSpace);
                shape.setRadius(radius);
                shape.setStartDrawPositionWeight(startWeight);
                Log.e(TAG, "initView: " + shape.getStartDrawPositionWeight());
                builder.setTopBoundaryShape(shape);
            }


            if ((showMask & BOUNDARY_BOTTOM) == BOUNDARY_BOTTOM) {
                final int quantity = a.getInt(
                        R.styleable.TicketView_boundary_bottomItemQuantity, -1);
                final int itemSpace = a.getDimensionPixelSize(
                        R.styleable.TicketView_boundary_bottomItemSpace, SimpleBoundaryShape.DEFAULT_ITEM_SPACE);
                final int radius = a.getDimensionPixelSize(
                        R.styleable.TicketView_boundary_bottomRadius, SimpleBoundaryShape.DEFAULT_ITEM_RADIUS);
                final float startWeight = a.getFloat(
                        R.styleable.TicketView_boundary_bottomDrawWeight, 0f);
                SimpleBoundaryShape shape = new SimpleBoundaryShape();
                shape.setItemCount(quantity);
                shape.setItemSpace(itemSpace);
                shape.setRadius(radius);
                shape.setStartDrawPositionWeight(startWeight);
                builder.setBottomBoundaryShape(shape);
            }

            int shadow = a.getDimensionPixelSize(R.styleable.TicketView_boundary_shadowPx, 0);
            boolean hasBoundaryLine = a.getBoolean(R.styleable.TicketView_boundary_hasStroke, false);
            int strokeWidth = a.getDimensionPixelSize(R.styleable.TicketView_boundary_strokeWidth, 2);
            int strokeColor = a.getColor(R.styleable.TicketView_boundary_strokeColor, Color.GRAY);
            float roundRadius = a.getDimensionPixelSize(R.styleable.TicketView_boundary_roundCorner, 0);
            builder.setShadowPx(shadow);
            builder.setBoundaryEnable(hasBoundaryLine);
            builder.setStrokeColor(strokeColor);
            builder.setStrokeWidth(strokeWidth);
            builder.setRoundedRadius(roundRadius);
            a.recycle();
        }
        setLayerType(LAYER_TYPE_SOFTWARE, null);
        setBackground(builder.create());
    }

    private static final String TAG = "TicketView";

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.e(TAG, "onDraw: " );
        for (int i = 0; i < getChildCount(); ++i) {
            if (getChildAt(i) instanceof ICustomShape) {
                ((ICustomShape) getChildAt(i)).drawCustom(canvas);
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.e(TAG, "onMeasure: " );
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.e(TAG, "onLayout: " );
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.e(TAG, "onSizeChanged: " );
    }
}
