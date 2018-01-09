package com.test.ticketdemo;

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

        SimpleTicketDrawable.Builder builder =
                new SimpleTicketDrawable.Builder(two.getBackground());
        SimpleRoundShape leftAndRight = new SimpleRoundShape();
        SimpleRoundShape bottomAndTop = new SimpleRoundShape();
        bottomAndTop.setRadius(leftAndRight.getRadius() * 4);
        bottomAndTop.setQuantity(1);
        bottomAndTop.setStartDrawGravity(TicketParam.DrawGravity.CENTER);
        builder.setBoundaryShape(
                leftAndRight,
                bottomAndTop,
                leftAndRight,
                bottomAndTop
        );
        two.setBackground(builder.create());
    }
}
