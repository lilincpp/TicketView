package com.lilincpp.ticketview.support.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.FrameLayout;

import com.lilincpp.ticketview.support.drawable.SimpleBoundaryDrawable;
import com.lilincpp.ticketview.support.shape.SimpleBoundaryShape;

/**
 * Created by Administrator on 2018/1/15.
 */

public class TicketView extends FrameLayout {

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
        builder.setBoundaryShape(
                new SimpleBoundaryShape(),
                null,
                new SimpleBoundaryShape(),
                null
        );
        builder.setBackgroundColor(Color.WHITE);
        builder.setShadowPx(16);
        builder.setShadowEnable(false);
        builder.setBoundaryEnable(true);
        setBackground(builder.create());
    }

    private static final String TAG = "TicketView";

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.e(TAG, "onDraw: ");
    }
}
