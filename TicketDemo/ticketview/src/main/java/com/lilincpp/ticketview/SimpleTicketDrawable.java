package com.lilincpp.ticketview;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;

/**
 * Created by Administrator on 2018/1/4.
 */

public class SimpleTicketDrawable extends TicketDrawable {

    private static final float DEFALUT_ROUND_RADIUS = 16f;
    private TicketParam mParam;


    public SimpleTicketDrawable(Drawable background) {
        super(background);
    }

    @Override
    public void drawTargetShape(Canvas canvas, Paint paint) {
        drawTop(canvas, paint);
        drawBottom(canvas, paint);
        drawLeft(canvas, paint);
        drawRight(canvas, paint);
    }

    private void drawTop(Canvas canvas, Paint paint) {
        if (mParam.showTop) {
            final int width = canvas.getWidth();
            final int height = canvas.getHeight();


            final float radius = (mParam.topRoundRadius == -1 ?
                    (mParam.roundRadius == -1 ? DEFALUT_ROUND_RADIUS : mParam.roundRadius)
                    : mParam.topRoundRadius);

            final int quantity = (mParam.topQuantity == -1 ?
                    (mParam.quantity == -1 ? DEFALUT_SHAPE_QUANTITY : mParam.quantity)
                    : mParam.topQuantity);

            if (quantity * radius >= width) {
                //若数量大于
            }
        }
    }

    private void drawBottom(Canvas canvas, Paint paint) {

    }

    private void drawLeft(Canvas canvas, Paint paint) {

    }

    private void drawRight(Canvas canvas, Paint paint) {

    }


    private int calculateDefalutQuantity(int width, float radius) {
        

    }


    public static final class Builder {
        TicketParam param;

        public Builder(Drawable background) {
            param = new TicketParam();
            param.background = background;
        }

        public Builder showDirection(boolean left, boolean top, boolean right, boolean bottom) {
            param.showLeft = left;
            param.showTop = top;
            param.showRight = right;
            param.showBottom = bottom;
            return this;
        }

        public SimpleTicketDrawable create() {
            SimpleTicketDrawable drawable = new SimpleTicketDrawable(param.background);
            param.apply(drawable);
            return drawable;
        }
    }

    public static final class TicketParam {
        public Drawable background;
        public boolean showTop;
        public boolean showBottom;
        public boolean showLeft;
        public boolean showRight;
        public int quantity = -1;
        public int topQuantity = -1;
        public int bottomQuantity = -1;
        public int rightQuantity = -1;
        public int leftQuantity = -1;
        public float roundRadius = -1;
        public float leftRoundRadius = -1;
        public float topRoundRadius = -1;
        public float rightRoundRadius = -1;
        public float bottomRoundRadius = -1;

        public void apply(SimpleTicketDrawable drawable) {
            drawable.mParam = this;
        }
    }
}
