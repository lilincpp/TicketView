package com.lilincpp.ticketview;

/**
 * Created by Administrator on 2018/1/5.
 */

public final class TicketParam {

    private TicketParam() {

    }

    public enum DrawGravity {

        START(1),
        CENTER(2);

        DrawGravity(int value) {
            this.value = value;
        }

        public final int value;
    }
}
