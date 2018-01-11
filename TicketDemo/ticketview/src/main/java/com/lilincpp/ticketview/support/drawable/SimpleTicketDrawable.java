package com.lilincpp.ticketview.support.drawable;

import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.MaskFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.lilincpp.ticketview.IBoundaryShape;
import com.lilincpp.ticketview.TicketDrawable;
import com.lilincpp.ticketview.support.shape.SimpleRoundShape;

import static android.view.View.LAYER_TYPE_SOFTWARE;

/**
 * Created by colin on 2018/1/4.
 */

public class SimpleTicketDrawable extends TicketDrawable {

    private static final String TAG = "SimpleTicketDrawable";

    private static final int LEFT_BOUNDARY_SHAPE_INDEX = 0;
    private static final int TOP_BOUNDARY_SHAPE_INDEX = 1;
    private static final int RIGHT_BOUNDARY_SHAPE_INDEX = 2;
    private static final int BOTTOM_BOUNDARY_SHAPE_INDEX = 3;
    private IBoundaryShape[] mBoundaryShapes; //4个边的形状
    private boolean mHasBoundaryLine = false; //是否描边
    private int mBoundaryLineColor = Color.parseColor("#ADADADAD");//描边的颜色
    private int mBoundaryLineSize = 4;
    private Paint mShapePaint;
    private Path mShapePath, mLinePath;
    private Path[] mShapePaths, mLinePaths;

    private SimpleTicketDrawable(SimpleTicketDrawable.Builder builder) {
        super(builder.background);
        mBoundaryShapes = builder.boundaryShapes;
        mShapePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mShapePaint.setAntiAlias(true);
        mShapePaint.setColor(Color.WHITE);
        mHasBoundaryLine = builder.hasBoundaryLine;
        mBoundaryLineColor = builder.boundaryLineColor;
        mBoundaryLineSize = builder.boundaryLineSize;
        mShapePath = new Path();
        mLinePath = new Path();
        mShapePaths = new Path[4];
        mLinePaths = new Path[4];
    }

    public Bitmap getShape(IBoundaryShape shape) {
        Bitmap bitmap = Bitmap.createBitmap(shape.getWidth(), shape.getHeight(), Bitmap.Config.ARGB_8888);
        shape.draw(new Canvas(bitmap), mShapePaint);
        return bitmap;
    }

    @Override
    public void drawTargetShape(Canvas canvas, Paint paint) {
        //设置画笔的模式，占位模式（直接将目标像素变为透明）
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        //绘制四个边的修饰物
        for (int i = 0; i < mBoundaryShapes.length; ++i) {
            IBoundaryShape shape = mBoundaryShapes[i];
            if (shape == null) {
                Log.e(TAG, "drawTargetShape:null ");
                shape = new SimpleRoundShape(0, 0, 0, 0);
            }
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

        if (mHasBoundaryLine) {
            mShapePaint.setColor(mBoundaryLineColor);
            mShapePaint.setStyle(Paint.Style.STROKE);
            mShapePaint.setStrokeWidth(mBoundaryLineSize);
            for (Path path : mShapePaths) {
                if (path == null) continue;
                canvas.drawPath(path, mShapePaint);
            }
            mShapePaint.setStrokeWidth(mBoundaryLineSize + 2);
            for (Path path : mLinePaths) {
                if (path == null) continue;
                canvas.drawPath(path, mShapePaint);
            }
        }

//        mShapePaint.setStyle(Paint.Style.STROKE);
//        mShapePaint.setStrokeWidth(8);
//        mShapePaint.setShadowLayer(2, 0, 0, Color.BLACK);
//        mShapePaint.setColor(Color.parseColor("#60ADADAD"));
////        BlurMaskFilter maskFilter = new BlurMaskFilter(100, BlurMaskFilter.Blur.SOLID);
////        mShapePaint.setMaskFilter(maskFilter);
////        mShapePaint.setMaskFilter(new BlurMaskFilter(100, BlurMaskFilter.Blur.INNER));
//        Path test = new Path();
//        for (int i = 0; i < mShapePaths.length; ++i) {
//            canvas.save();
//
//           canvas.drawPath(mShapePaths[i],mShapePaint);
//           canvas.restore();
//        }
//        mShapePaint.setStrokeWidth(12);
//        for (int i = 0; i < mLinePaths.length; ++i) {
//            canvas.save();
//            switch (i){
//                case LEFT_BOUNDARY_SHAPE_INDEX:
//                    canvas.translate(-2,0);
//                    break;
//                case TOP_BOUNDARY_SHAPE_INDEX:
//                    canvas.translate(0,-2);
//                    break;
//                case RIGHT_BOUNDARY_SHAPE_INDEX:
//                    canvas.translate(2,0);
//                    break;
//                case BOTTOM_BOUNDARY_SHAPE_INDEX:
//                    canvas.translate(0,2);
//                    break;
//            }
//            canvas.restore();
//            canvas.drawPath(mLinePaths[i],mShapePaint);
//        }
//        test.close();
        Shader mShader =
                new LinearGradient(0, 0, 40, 60, new int[]{Color.RED, Color.GREEN, Color.BLUE}, null, Shader.TileMode.REPEAT);
//        mShapePaint.setShader(mShader);
//        canvas.drawPath(test, mShapePaint);
    }

    @Override
    public Path[] test() {
        return mLinePaths;
    }

    @Override
    public Path[] test2() {
        return mShapePaths;
    }

    protected void drawLeft(Canvas canvas, Paint paint, IBoundaryShape shape) {
        mLinePath.reset();
        mShapePath.reset();
        final Bitmap drawShape = getShape(shape);
        float y = shape.getDividingSpace();
        final int count = shape.getCount() == -1 ?
                calculateItemCount(shape.getHeight(), shape.getDividingSpace(), canvas.getHeight()) : shape.getCount();
        if (shape.getStartDrawWeight() != 0) {
            y = shape.getStartDrawWeight() * canvas.getHeight() - shape.getWidth() / 2;
        } else {
            float adjustSpace = adjustVerticalSpace(canvas.getHeight(), count, shape);
            y = y + adjustSpace;
        }
        mLinePath.moveTo(0, 0);
        mLinePath.lineTo(0, y);
        for (int i = 0; i < count; ++i) {
            canvas.drawBitmap(drawShape, shape.getMarginBounds().left, y, paint);
            RectF rect = new RectF(-shape.getWidth() / 2, y, shape.getWidth() / 2, y + shape.getHeight());
            mShapePath.addArc(rect,
                    0, 360);
            mLinePath.moveTo(0, y + shape.getHeight());
            y = y + shape.getHeight() + shape.getDividingSpace();
            mLinePath.lineTo(0, y);
        }
        mLinePath.lineTo(0, canvas.getHeight());
        drawShape.recycle();
        mShapePaths[LEFT_BOUNDARY_SHAPE_INDEX] = new Path(mShapePath);
        mLinePaths[LEFT_BOUNDARY_SHAPE_INDEX] = new Path(mLinePath);
    }

    protected void drawTop(Canvas canvas, Paint paint, IBoundaryShape shape) {
        mLinePath.reset();
        mShapePath.reset();
        final Bitmap drawShape = getShape(shape);
        float x = shape.getDividingSpace();
        final int count = shape.getCount() == -1 ?
                calculateItemCount(shape.getWidth(), shape.getDividingSpace(), canvas.getWidth()) : shape.getCount();
        if (shape.getStartDrawWeight() != 0) {
            x = shape.getStartDrawWeight() * canvas.getWidth() - shape.getWidth() / 2;
        } else {
            float adjustSpace = adjustHorizontalSpace(canvas.getWidth(), count, shape);
            x += adjustSpace;
        }
        mLinePath.moveTo(0, 0);
        mLinePath.lineTo(x, 0);
        for (int i = 0; i < count; ++i) {
            canvas.drawBitmap(drawShape, x, shape.getMarginBounds().top, paint);
            RectF rect = new RectF(x, -shape.getHeight() / 2, x + shape.getWidth(), shape.getHeight() / 2);
            mShapePath.addArc(rect,
                    0, 360);
            mLinePath.moveTo(x + shape.getHeight(), 0);
            x = x + shape.getWidth() + shape.getDividingSpace();
            mLinePath.lineTo(x, 0);
        }
        mLinePath.lineTo(canvas.getWidth(), 0);
        drawShape.recycle();
        mShapePaths[TOP_BOUNDARY_SHAPE_INDEX] = new Path(mShapePath);
        mLinePaths[TOP_BOUNDARY_SHAPE_INDEX] = new Path(mLinePath);
    }

    protected void drawRight(Canvas canvas, Paint paint, IBoundaryShape shape) {
        mLinePath.reset();
        mShapePath.reset();
        final Bitmap drawShape = getShape(shape);
        float y = shape.getDividingSpace();
        final int count = shape.getCount() == -1 ?
                calculateItemCount(shape.getHeight(), shape.getDividingSpace(), canvas.getHeight()) : shape.getCount();
        if (shape.getStartDrawWeight() != 0) {
            y = shape.getStartDrawWeight() * canvas.getHeight() - shape.getWidth() / 2;
        } else {
            float adjustSpace = adjustVerticalSpace(canvas.getHeight(), count, shape);
            y = y + adjustSpace;
        }
        mLinePath.moveTo(canvas.getWidth(), 0);
        mLinePath.lineTo(canvas.getWidth(), y);
        for (int i = 0; i < count; ++i) {
            canvas.drawBitmap(drawShape, shape.getMarginBounds().right + canvas.getWidth(), y, paint);
            RectF rect = new RectF(canvas.getWidth() - shape.getWidth() / 2, y, canvas.getWidth() + shape.getWidth() / 2, y + shape.getHeight());
            mShapePath.addArc(rect,
                    0, 360);
            mLinePath.moveTo(canvas.getWidth(), y + shape.getHeight());
            y = y + shape.getHeight() + shape.getDividingSpace();
            mLinePath.lineTo(canvas.getWidth(), y);
        }
        mLinePath.lineTo(canvas.getWidth(), canvas.getHeight());
        drawShape.recycle();
        mShapePaths[RIGHT_BOUNDARY_SHAPE_INDEX] = new Path(mShapePath);
        mLinePaths[RIGHT_BOUNDARY_SHAPE_INDEX] = new Path(mLinePath);
    }

    protected void drawBottom(Canvas canvas, Paint paint, IBoundaryShape shape) {
        mLinePath.reset();
        mShapePath.reset();
        final Bitmap drawShape = getShape(shape);
        float x = shape.getDividingSpace();
        final int count = shape.getCount() == -1 ?
                calculateItemCount(shape.getWidth(), shape.getDividingSpace(), canvas.getWidth()) : shape.getCount();
        if (shape.getStartDrawWeight() != 0) {
            x = shape.getStartDrawWeight() * canvas.getWidth() - shape.getWidth() / 2;
        } else {
            float adjustSpace = adjustHorizontalSpace(canvas.getWidth(), count, shape);
            x += adjustSpace;
        }
        mLinePath.moveTo(0, canvas.getHeight());
        mLinePath.lineTo(x, canvas.getHeight());
        Log.e(TAG, "drawBottom: " + count);
        for (int i = 0; i < count; ++i) {
            canvas.drawBitmap(drawShape, x, shape.getMarginBounds().bottom + canvas.getHeight(), paint);
            RectF rect = new RectF(x, canvas.getHeight() - shape.getHeight() / 2, x + shape.getWidth(), canvas.getHeight() + shape.getHeight() / 2);
            mShapePath.addArc(rect,
                    0, 360);
            mLinePath.moveTo(x + shape.getHeight(), canvas.getHeight());
            x = x + shape.getWidth() + shape.getDividingSpace();
            mLinePath.lineTo(x, canvas.getHeight());
        }
        mLinePath.lineTo(canvas.getWidth(), canvas.getHeight());
        drawShape.recycle();
        mShapePaths[BOTTOM_BOUNDARY_SHAPE_INDEX] = new Path(mShapePath);
        mLinePaths[BOTTOM_BOUNDARY_SHAPE_INDEX] = new Path(mLinePath);
    }

    private int calculateItemCount(int itemWidth, float space, int contentWidth) {
        if ((itemWidth + space) > 0) {
            return (int) (contentWidth / (itemWidth + space));
        } else {
            return 0;
        }
    }

    private float adjustHorizontalSpace(int canvasWidth, int count, IBoundaryShape shape) {
        float space = canvasWidth - shape.getWidth() * count - shape.getDividingSpace() * (count + 1);
        return space / 2;
    }

    private float adjustVerticalSpace(int canvasHeight, int count, IBoundaryShape shape) {
        float space = canvasHeight - shape.getHeight() * count - shape.getDividingSpace() * (count + 1);
        return space / 2;
    }

    public static final class Builder {
        private Drawable background;
        private boolean hasBoundaryLine;
        private int boundaryLineColor;
        private int boundaryLineSize;
        private IBoundaryShape[] boundaryShapes = new IBoundaryShape[4];

        public Builder(Drawable background) {
            this.background = background;
        }

        public Builder setBoundaryShape(IBoundaryShape leftShape,
                                        IBoundaryShape topShape,
                                        IBoundaryShape rightShape,
                                        IBoundaryShape bottomShape) {
            boundaryShapes[LEFT_BOUNDARY_SHAPE_INDEX] = leftShape;
            boundaryShapes[TOP_BOUNDARY_SHAPE_INDEX] = topShape;
            boundaryShapes[RIGHT_BOUNDARY_SHAPE_INDEX] = rightShape;
            boundaryShapes[BOTTOM_BOUNDARY_SHAPE_INDEX] = bottomShape;
            return this;
        }

        public Builder setLeftBoundaryShape(IBoundaryShape shape) {
            boundaryShapes[LEFT_BOUNDARY_SHAPE_INDEX] = shape;
            return this;
        }

        public Builder setTopBoundaryShape(IBoundaryShape shape) {
            boundaryShapes[TOP_BOUNDARY_SHAPE_INDEX] = shape;
            return this;
        }

        public Builder setRightBoundaryShape(IBoundaryShape shape) {
            boundaryShapes[RIGHT_BOUNDARY_SHAPE_INDEX] = shape;
            return this;
        }

        public Builder setBottomBoundaryShape(IBoundaryShape shape) {
            boundaryShapes[BOTTOM_BOUNDARY_SHAPE_INDEX] = shape;
            return this;
        }

        public Builder setBoundaryLine(boolean enable) {
            hasBoundaryLine = enable;
            return this;
        }

        public Builder setBoundaryLineColor(int color) {
            boundaryLineColor = color;
            return this;
        }

        public Builder setBoundaryLineSize(int px) {
            boundaryLineSize = px;
            return this;
        }

        public SimpleTicketDrawable create() {
            return new SimpleTicketDrawable(this);
        }
    }


}
