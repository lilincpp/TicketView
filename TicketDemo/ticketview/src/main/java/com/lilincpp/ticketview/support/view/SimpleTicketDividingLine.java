package com.lilincpp.ticketview.support.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import com.lilincpp.ticketview.ICustomShape;
import com.lilincpp.ticketview.R;

/**
 * Created by colin on 2018/1/9.
 */

public class SimpleTicketDividingLine extends View implements ICustomShape {

    private static final String TAG = "SimpleTicketDividingLin";

    public static final int VERTICAL = 0;
    public static final int HORIZONTAL = 1;

    private int mLineColor;
    private float mLineWeight;
    private int mLineGravity;
    private Rect mLinePadding;
    private int mLineWidth;
    private int mLineGap;

    private Paint mPaint;
    private Path mPath;

    private boolean mNoChanged = false;
    private float mTopDistance = -1, mLeftDistance = -1;

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
        mLinePadding = new Rect(0, 0, 0, 0);
        if (attrs != null) {
            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.SimpleTicketDividingLine);
            mLineGravity = array.getInt(R.styleable.SimpleTicketDividingLine_line_gravity, HORIZONTAL);
            mLineColor = array.getInt(R.styleable.SimpleTicketDividingLine_line_color, Color.WHITE);
            mLineWeight = array.getFloat(R.styleable.SimpleTicketDividingLine_line_drawWeight, 0f);
            mNoChanged = array.getBoolean(R.styleable.SimpleTicketDividingLine_line_noChange, false);
            int padding = array.getDimensionPixelSize(R.styleable.SimpleTicketDividingLine_line_padding, 0);
            int paddingLeft = array.getDimensionPixelSize(R.styleable.SimpleTicketDividingLine_line_paddingLeft, 0);
            int paddingTop = array.getDimensionPixelSize(R.styleable.SimpleTicketDividingLine_line_paddingTop, 0);
            int paddingRight = array.getDimensionPixelSize(R.styleable.SimpleTicketDividingLine_line_paddingRight, 0);
            int paddingBottom = array.getDimensionPixelSize(R.styleable.SimpleTicketDividingLine_line_paddingBottom, 0);
            mLinePadding.left = paddingLeft == 0 ? padding : paddingLeft;
            mLinePadding.top = paddingTop == 0 ? padding : paddingTop;
            mLinePadding.right = paddingRight == 0 ? padding : paddingRight;
            mLinePadding.bottom = paddingBottom == 0 ? padding : paddingBottom;
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
    public void drawCustom(Canvas canvas) {
        mPath.reset();

        if (mLineGravity == HORIZONTAL) {
            if (mNoChanged) {
                if (mTopDistance == -1) {
                    mTopDistance = mLineWeight * canvas.getHeight();
                }
            } else {
                mTopDistance = -1;
            }
            float y = mNoChanged ? mTopDistance : mLineWeight * canvas.getHeight();
            mPath.moveTo(mLinePadding.left, y);
            mPath.lineTo(canvas.getWidth() - mLinePadding.right, y);
        } else {
            if (mNoChanged) {
                if (mLeftDistance == -1) {
                    mLeftDistance = mLineWeight * canvas.getWidth();
                }
            } else {
                mLeftDistance = -1;
            }
            float x = mNoChanged ? mLeftDistance : mLineWeight * canvas.getWidth();
            mPath.moveTo(x, mLinePadding.top);
            mPath.lineTo(x, canvas.getHeight() - mLinePadding.bottom);
        }
        canvas.drawPath(mPath, mPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(0, 0);
    }
}
