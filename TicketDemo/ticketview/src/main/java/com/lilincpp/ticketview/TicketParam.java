package com.lilincpp.ticketview;

/**
 * Created by colin on 2018/1/5.
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

        public static DrawGravity parseOf(int value) {
            if (value == 1) {
                return START;
            } else if (value == 2) {
                return CENTER;
            } else {
                return START;
            }
        }
    }
}
