package com.lilincpp.ticketview.support.drawable;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.Drawable;

import com.lilincpp.ticketview.IShape;
import com.lilincpp.ticketview.TicketDrawable;
import com.lilincpp.ticketview.TicketParam;

/**
 * Created by colin on 2018/1/4.
 */

public class SimpleTicketDrawable extends TicketDrawable {

    private static final String TAG = "SimpleTicketDrawable";

    private static final int LEFT_BOUNDARY_SHAPE_INDEX = 0;
    private static final int TOP_BOUNDARY_SHAPE_INDEX = 1;
    private static final int RIGHT_BOUNDARY_SHAPE_INDEX = 2;
    private static final int BOTTOM_BOUNDARY_SHAPE_INDEX = 3;
    private IShape[] mBoundaryShapes;
    private Paint mShapePaint;

    private SimpleTicketDrawable(SimpleTicketDrawable.Builder builder) {
        super(builder.background);
        mBoundaryShapes = builder.boundaryShapes;
        mShapePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mShapePaint.setColor(Color.WHITE);
    }

    public Bitmap getShape(IShape shape) {
        Bitmap bitmap = Bitmap.createBitmap(shape.getWidth(), shape.getHeight(), Bitmap.Config.ARGB_8888);
        shape.draw(new Canvas(bitmap), mShapePaint);
        return bitmap;
    }

    @Override
    public void drawTargetShape(Canvas canvas, Paint paint) {
        //设置画笔的模式，占位模式（直接将目标像素变为透明）
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));

        for (int i = 0; i < mBoundaryShapes.length; ++i) {
            final IShape shape = mBoundaryShapes[i];
            if (shape == null) continue;
            switch (i) {
                case LEFT_BOUNDARY_SHAPE_INDEX:
                    drawLeft(canvas, paint, shape);
                    break;
                case TOP_BOUNDARY_SHAPE_INDEX:
                    drawTop(canvas, paint, shape);
                    break;
                case RIGHT_BOUNDARY_SHAPE_INDEX:
                    drawRight(canvas, paint, shape);
                    break;
                case BOTTOM_BOUNDARY_SHAPE_INDEX:
                    drawBottom(canvas, paint, shape);
                    break;
            }
        }
        paint.setXfermode(null);
    }


    protected void drawLeft(Canvas canvas, Paint paint, IShape shape) {
        final Bitmap drawShape = getShape(shape);
        float y = shape.getDividingSpace();
        if (shape.getStartDrawGravity() == TicketParam.DrawGravity.CENTER) {
            y = canvas.getHeight() / 2;
        }
        final int count = shape.getCount() == -1 ?
                calculateItemCount(shape.getHeight(), shape.getDividingSpace(), canvas.getHeight()) : shape.getCount();
        for (int i = 0; i < count; ++i) {
            canvas.drawBitmap(drawShape, shape.getMarginBounds().left, y, paint);
            y = y + shape.getHeight() + shape.getDividingSpace();
        }
        drawShape.recycle();
    }

    protected void drawTop(Canvas canvas, Paint paint, IShape shape) {
        final Bitmap drawShape = getShape(shape);
        float x = shape.getDividingSpace();
        if (shape.getStartDrawGravity() == TicketParam.DrawGravity.CENTER) {
            x = canvas.getWidth() / 2 - drawShape.getWidth() / 2;
        }
        final int count = shape.getCount() == -1 ?
                calculateItemCount(shape.getWidth(), shape.getDividingSpace(), canvas.getWidth()) : shape.getCount();
        for (int i = 0; i < count; ++i) {
            canvas.drawBitmap(drawShape, x, shape.getMarginBounds().top, paint);
            x = x + shape.getWidth() + shape.getDividingSpace();
        }
        drawShape.recycle();
    }

    protected void drawRight(Canvas canvas, Paint paint, IShape shape) {
        final Bitmap drawShape = getShape(shape);
        float y = shape.getDividingSpace();
        if (shape.getStartDrawGravity() == TicketParam.DrawGravity.CENTER) {
            y = canvas.getHeight() / 2;
        }
        final int count = shape.getCount() == -1 ?
                calculateItemCount(shape.getHeight(), shape.getDividingSpace(), canvas.getHeight()) : shape.getCount();
        for (int i = 0; i < count; ++i) {
            canvas.drawBitmap(drawShape, shape.getMarginBounds().right + canvas.getWidth(), y, paint);
            y = y + shape.getHeight() + shape.getDividingSpace();
        }
        drawShape.recycle();
    }

    protected void drawBottom(Canvas canvas, Paint paint, IShape shape) {
        final Bitmap drawShape = getShape(shape);
        float x = shape.getDividingSpace();
        if (shape.getStartDrawGravity() == TicketParam.DrawGravity.CENTER) {
            x =  canvas.getWidth() / 2 - drawShape.getWidth() / 2;
        }
        final int count = shape.getCount() == -1 ?
                calculateItemCount(shape.getWidth(), shape.getDividingSpace(), canvas.getWidth()) : shape.getCount();
        for (int i = 0; i < count; ++i) {
            canvas.drawBitmap(drawShape, x, shape.getMarginBounds().bottom + canvas.getHeight(), paint);
            x = x + shape.getWidth() + shape.getDividingSpace();
        }
        drawShape.recycle();
    }

    private int calculateItemCount(int itemWidth, float space, int contentWidth) {
        if ((itemWidth + space) > 0) {
            return (int) (contentWidth / (itemWidth + space));
        } else {
            return 0;
        }
    }

    public static final class Builder {
        private Drawable background;
        private IShape[] boundaryShapes = new IShape[4];

        public Builder(Drawable background) {
            this.background = background;
        }

        public Builder setBoundaryShape(IShape leftShape,
                                        IShape topShape,
                                        IShape rightShape,
                                        IShape bottomShape) {
            boundaryShapes[LEFT_BOUNDARY_SHAPE_INDEX] = leftShape;
            boundaryShapes[TOP_BOUNDARY_SHAPE_INDEX] = topShape;
            boundaryShapes[RIGHT_BOUNDARY_SHAPE_INDEX] = rightShape;
            boundaryShapes[BOTTOM_BOUNDARY_SHAPE_INDEX] = bottomShape;
            return this;
        }

        public SimpleTicketDrawable create() {
            return new SimpleTicketDrawable(this);
        }
    }


}
