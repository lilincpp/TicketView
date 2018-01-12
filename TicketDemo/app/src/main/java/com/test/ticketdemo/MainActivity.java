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
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        LinearLayout two = findViewById(R.id.ll_two);
//        two.setBackground(builder.create());
//        two.setElevation(8f);

        CardView cardView = findViewById(R.id.cardview);
        cardView.setBackground(buildBackground());
        ViewCompat.setElevation(two,9f);
        two.setLayerType(View.LAYER_TYPE_SOFTWARE,null);
        two.setBackground(new MyDrawable());

    }

    private Drawable buildBackground() {
        return new ShapeDrawable(new RectShape() {
            @Override
            public void draw(Canvas canvas, Paint paint) {
                paint.setColor(Color.WHITE);
                paint.setStyle(Paint.Style.FILL);
                canvas.drawPath(buildConvexPath(), paint);
            }

            @Override
            public void getOutline(Outline outline) {
                outline.setConvexPath(buildConvexPath());
            }

            private Path buildConvexPath() {
//                Path path = new Path();
//                path.lineTo(rect().left, rect().top);
//                path.lineTo(rect().right, rect().top);
//                path.lineTo(rect().left, rect().bottom);
//                path.close();

                Path path = new Path();
                path.moveTo(rect().left,rect().top);
                path.lineTo(rect().left,50);
//                path.arcTo(rect().left,50,50,100,0,120,false);
                path.lineTo(rect().left,rect().bottom);
                path.lineTo(rect().right,rect().bottom);
                path.lineTo(rect().left,rect().top);
                path.close();
                return path;
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }
}
