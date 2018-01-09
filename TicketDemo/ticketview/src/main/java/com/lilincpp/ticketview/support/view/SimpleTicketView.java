package com.lilincpp.ticketview.support.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.lilincpp.ticketview.ICustomShape;
import com.lilincpp.ticketview.support.shape.SimpleRoundShape;
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
        SimpleRoundShape leftAndRight = new SimpleRoundShape();
        builder.setBoundaryShape(
                leftAndRight,
                null,
                leftAndRight,
                null
        );
        setBackground(builder.create());
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制线
        for (int i = 0; i < getChildCount(); ++i) {
            final View child = getChildAt(i);
            if (child != null && child instanceof ICustomShape) {
                ((ICustomShape) child).drawCustom(canvas);
            }
        }
    }

}
