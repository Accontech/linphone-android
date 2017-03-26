package com.peeredge.core.common.Models;

/**
 * Created by samson on 3/25/17.
 */
public class CallState {

    //public static final int INVALID = 0;
    //public static final int NEW = 1;            /* The call is new. */
    //public static final int IDLE = 2;           /* The call is idle.  Nothing active */

    public static final int ACTIVE = 3;         /* There is an active call */
    public static final int INCOMING = 4;       /* A normal incoming phone call */
    public static final int ONHOLD = 8;         /* An active phone call placed on hold */
    public static final int DISCONNECTING = 9;  /* A call is being ended. */
    public static final int DISCONNECTED = 10;  /* State after a call disconnects */
    //public static final int CONFERENCED = 11;   /* Call part of a conference call */
    public static final int CONNECTING = 13;    /* Waiting for Telecom broadcast to finish */
   // public static final int BLOCKED = 14;       /* The number was found on the block list */
    public static final int RINGING = 33;
    public static final int ONREMOTEHOLD = 34;
    public static final int SELECT_ACCOUNT = 44;
    public static final int INVALID = 45;


    public static String toString(int state) {
        switch (state) {

            case ACTIVE:
                return "ACTIVE";
            case INCOMING:
                return "INCOMING";
            case ONHOLD:
                return "ONHOLD";
            case DISCONNECTING:
                return "DISCONNECTING";
            case DISCONNECTED:
                return "DISCONNECTED";
            case CONNECTING:
                return "CONNECTING";
            default:
                return "UNKNOWN";
        }
    }
}
