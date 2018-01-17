package com.lilincpp.ticketview;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

/**
 * Created by colin on 2018/1/5.
 */

public interface IBoundaryShape {

    RectF getBounds();

    float getWidth();

    float getHeight();

    float getSpace();

    int getQuantity();

    float getStartDrawPositionWeight();

    boolean startDrawPositionInCenter();

    Style getStyle();

    public enum Style {
        ROUND,
        RECT
    }
}
