package com.lilincpp.ticketview.support.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import com.lilincpp.ticketview.IDividingLineShape;
import com.lilincpp.ticketview.R;

/**
 * Created by colin on 2018/1/9.
 */

public class SimpleTicketDividingLine extends View implements IDividingLineShape {

    private static final String TAG = "SimpleTicketDividingLin";

    public static final int VERTICAL = 0;
    public static final int HORIZONTAL = 1;

    private int mLineColor;
    private float mLineWeight;
    private int mLineGravity;
    private int mLinePadding;
    private int mLineWidth;
    private int mLineGap;

    private Paint mPaint;
    private Path mPath;

    public SimpleTicketDividingLine(Context context) {
        super(context);
        initView(context, null);
    }

    public SimpleTicketDividingLine(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public SimpleTicketDividingLine(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {

        if (attrs != null) {
            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.SimpleTicketDividingLine);
            mLineGravity = array.getInt(R.styleable.SimpleTicketDividingLine_line_gravity, HORIZONTAL);
            mLineColor = array.getInt(R.styleable.SimpleTicketDividingLine_line_color, Color.WHITE);
            mLineWeight = array.getFloat(R.styleable.SimpleTicketDividingLine_line_drawWeight, 0f);
            mLinePadding = array.getDimensionPixelSize(R.styleable.SimpleTicketDividingLine_line_padding, 0);
            mLineWidth = array.getDimensionPixelSize(R.styleable.SimpleTicketDividingLine_line_width,
                    (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, context.getResources().getDisplayMetrics()));
            mLineGap = array.getDimensionPixelSize(R.styleable.SimpleTicketDividingLine_line_gap,
                    (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, context.getResources().getDisplayMetrics()));
            array.recycle();
        }

        initLinePaint();
    }

    private void initLinePaint() {
        mPaint = new Paint();
        mPaint.setColor(mLineColor);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mLineWidth);
        mPaint.setPathEffect(new DashPathEffect(new float[]{mLineGap, mLineGap, mLineGap, mLineGap}, 1));
        mPath = new Path();
    }

    @Override
    public void drawLine(Canvas canvas) {
        mPath.reset();
        if (mLineGravity == HORIZONTAL) {
            mPath.moveTo(mLinePadding, mLineWeight * canvas.getHeight());
            mPath.lineTo(canvas.getWidth() - mLinePadding, mLineWeight * canvas.getHeight());
        } else {
            mPath.moveTo(mLineWeight * canvas.getWidth(), mLinePadding);
            mPath.lineTo(mLineWeight * canvas.getWidth(), canvas.getHeight() - mLinePadding);
        }
        canvas.drawPath(mPath, mPaint);
    }
}
