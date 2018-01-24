package com.test.ticketdemo;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.graphics.drawable.shapes.RoundRectShape;
import android.graphics.drawable.shapes.Shape;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lilincpp.ticketview.support.drawable.SimpleBoundaryDrawable;
import com.lilincpp.ticketview.support.shape.SimpleBoundaryShape;
import com.lilincpp.ticketview.support.view.TicketView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        LinearLayout two = findViewById(R.id.ll_two);
//        two.setBackground(builder.create());
//        two.setElevation(8f);

        two.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        SimpleBoundaryDrawable.Builder builder = new SimpleBoundaryDrawable.Builder();
        builder.setBoundaryShape(
                new SimpleBoundaryShape(),
                new SimpleBoundaryShape(),
                new SimpleBoundaryShape(),
                new SimpleBoundaryShape()
        );
        builder.setShadowPx(16);
        two.setBackground(builder.create());

    }

    private static final String TAG = "MainActivity";


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }
}
