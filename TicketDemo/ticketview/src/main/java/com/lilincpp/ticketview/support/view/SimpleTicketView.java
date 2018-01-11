package com.lilincpp.ticketview.support.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.lilincpp.ticketview.ICustomShape;
import com.lilincpp.ticketview.R;
import com.lilincpp.ticketview.support.shape.SimpleRoundShape;
import com.lilincpp.ticketview.support.drawable.SimpleTicketDrawable;

/**
 * Created by colin on 2018/1/5.
 */

public class SimpleTicketView extends FrameLayout {
    private static final String TAG = "SimpleTicketView";

    private static final int FLAG_LEFT_VISIABLE = 0x01;
    private static final int FLAG_TOP_VISIABLE = 0x02;
    private static final int FLAG_RIGHT_VISIABLE = 0x04;
    private static final int FLAG_BOTTOM_VISIABLE = 0x08;

    public SimpleTicketView(Context context) {
        super(context);
        initView(context, null);
    }

    public SimpleTicketView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public SimpleTicketView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        if (attrs != null) {
            SimpleTicketDrawable.Builder builder = new SimpleTicketDrawable.Builder(getBackground());
            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.SimpleTicketView);
            int showMask = array.getInt(R.styleable.SimpleTicketView_boundary_show, 0);
            if ((showMask & FLAG_LEFT_VISIABLE) == FLAG_LEFT_VISIABLE) {
                int radius = array.getDimensionPixelSize(R.styleable.SimpleTicketView_boundary_leftRadius, 0);
                int space = array.getDimensionPixelSize(R.styleable.SimpleTicketView_boundary_leftItemSpace, 0);
                int quantity = array.getInt(R.styleable.SimpleTicketView_boundary_leftItemQuantity, -1);
                float gravity = array.getFloat(R.styleable.SimpleTicketView_boundary_leftDrawWeight, 0);
                SimpleRoundShape shape = new SimpleRoundShape(quantity, radius, space, gravity);
                builder.setLeftBoundaryShape(shape);
            }

            if ((showMask & FLAG_TOP_VISIABLE) == FLAG_TOP_VISIABLE) {
                int radius = array.getDimensionPixelSize(R.styleable.SimpleTicketView_boundary_topRadius, 0);
                int space = array.getDimensionPixelSize(R.styleable.SimpleTicketView_boundary_topItemSpace, 0);
                int quantity = array.getInt(R.styleable.SimpleTicketView_boundary_topItemQuantity, -1);
                float gravity = array.getFloat(R.styleable.SimpleTicketView_boundary_topDrawWeight, 0);
                SimpleRoundShape shape = new SimpleRoundShape(quantity, radius, space, gravity);
                builder.setTopBoundaryShape(shape);
            }

            if ((showMask & FLAG_RIGHT_VISIABLE) == FLAG_RIGHT_VISIABLE) {
                int radius = array.getDimensionPixelSize(R.styleable.SimpleTicketView_boundary_rightRadius, 0);
                int space = array.getDimensionPixelSize(R.styleable.SimpleTicketView_boundary_rightItemSpace, 0);
                int quantity = array.getInt(R.styleable.SimpleTicketView_boundary_rightItemQuantity, -1);
                float gravity = array.getFloat(R.styleable.SimpleTicketView_boundary_rightDrawWeight, 0);
                SimpleRoundShape shape = new SimpleRoundShape(quantity, radius, space, gravity);
                builder.setRightBoundaryShape(shape);
            }


            if ((showMask & FLAG_BOTTOM_VISIABLE) == FLAG_BOTTOM_VISIABLE) {
                int radius = array.getDimensionPixelSize(R.styleable.SimpleTicketView_boundary_bottomRadius, 0);
                int space = array.getDimensionPixelSize(R.styleable.SimpleTicketView_boundary_bottomItemSpace, 0);
                int quantity = array.getInt(R.styleable.SimpleTicketView_boundary_bottomItemQuantity, -1);
                float gravity = array.getFloat(R.styleable.SimpleTicketView_boundary_bottomDrawWeight, 0);
                SimpleRoundShape shape = new SimpleRoundShape(quantity, radius, space, gravity);
                builder.setBottomBoundaryShape(shape);
            }
            array.recycle();
            setBackground(builder.create());
        }
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
