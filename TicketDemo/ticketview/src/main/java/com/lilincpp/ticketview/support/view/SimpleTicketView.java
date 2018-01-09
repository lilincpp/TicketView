package com.lilincpp.ticketview.support.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.lilincpp.ticketview.IDividingLineShape;
import com.lilincpp.ticketview.TicketParam;
import com.lilincpp.ticketview.support.shape.RoundShape;
import com.lilincpp.ticketview.support.drawable.SimpleTicketDrawable;

/**
 * Created by colin on 2018/1/5.
 */

public class SimpleTicketView extends FrameLayout {
    private static final String TAG = "SimpleTicketView";

    public SimpleTicketView(Context context) {
        super(context);
        initView();
    }

    public SimpleTicketView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public SimpleTicketView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        SimpleTicketDrawable.Builder builder = new SimpleTicketDrawable.Builder(getBackground());
        RoundShape leftAndRight = new RoundShape();
        RoundShape topAndBottom = new RoundShape();
        topAndBottom.setRadius(leftAndRight.getRadius() * 4);
        topAndBottom.setStartDrawGravity(TicketParam.DrawGravity.CENTER);
        topAndBottom.setQuantity(1);
        builder.setBoundaryShape(
                leftAndRight,
                topAndBottom,
                leftAndRight,
                topAndBottom
        );
        setBackground(builder.create());
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制线
        for (int i = 0; i < getChildCount(); ++i) {
            final View child = getChildAt(i);
            if (child != null && child instanceof IDividingLineShape) {
                ((IDividingLineShape) child).drawLine(canvas);
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(0, 0);
    }
}
