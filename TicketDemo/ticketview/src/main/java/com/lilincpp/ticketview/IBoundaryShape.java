package com.lilincpp.ticketview;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by colin on 2018/1/5.
 */

public interface IBoundaryShape {

    void draw(Canvas canvas, Paint paint);

    int getCount();

    float getDividingSpace();

    int getWidth();

    int getHeight();

    Rect getMarginBounds();

    TicketParam.DrawGravity getStartDrawGravity();

}
