package com.test.ticketdemo;

import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
                new SimpleTicketDrawable.Builder(two.getBackground());
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

    }
}
