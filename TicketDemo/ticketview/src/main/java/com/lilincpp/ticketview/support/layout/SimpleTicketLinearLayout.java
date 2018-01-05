package com.lilincpp.ticketview.support.layout;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.LinearLayout;

import com.lilincpp.ticketview.IBoundaryShape;
import com.lilincpp.ticketview.TicketParam;
import com.lilincpp.ticketview.support.shape.RoundBoundaryShape;
import com.lilincpp.ticketview.support.drawable.SimpleTicketDrawable;

/**
 * Created by Administrator on 2018/1/5.
 */

public class SimpleTicketLinearLayout extends LinearLayout {
    private static final String TAG = "SimpleTicketLinearLayou";

    public SimpleTicketLinearLayout(Context context) {
        super(context);
        initView();
    }

    public SimpleTicketLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public SimpleTicketLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        SimpleTicketDrawable.Builder builder = new SimpleTicketDrawable.Builder(getBackground());
        RoundBoundaryShape leftAndRight = new RoundBoundaryShape();
        RoundBoundaryShape topAndBottom = new RoundBoundaryShape();
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
        Log.e(TAG, "onDraw: ");
        super.onDraw(canvas);
    }
}
