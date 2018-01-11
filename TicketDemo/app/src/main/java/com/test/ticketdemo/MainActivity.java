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
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.widget.LinearLayout;
import android.widget.TableRow;

import com.lilincpp.ticketview.TicketParam;
import com.lilincpp.ticketview.support.drawable.SimpleTicketDrawable;
import com.lilincpp.ticketview.support.shape.SimpleRoundShape;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        LinearLayout two = findViewById(R.id.ll_two);
//
        SimpleTicketDrawable.Builder builder =
                new SimpleTicketDrawable.Builder(buildBackground());
        SimpleRoundShape leftAndRight = new SimpleRoundShape();
        SimpleRoundShape bottomAndTop = new SimpleRoundShape();
        bottomAndTop.setRadius(leftAndRight.getRadius() * 4);
        bottomAndTop.setQuantity(1);
        bottomAndTop.setStartDrawGravity(0.5f);
        builder.setBoundaryShape(
                leftAndRight,
                bottomAndTop,
                leftAndRight,
                bottomAndTop
        );
        two.setBackground(builder.create());
        two.setElevation(8f);

        CardView cardView = findViewById(R.id.cardview);
        cardView.setBackground(buildBackground());
        ViewCompat.setElevation(two,9f);
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
}
