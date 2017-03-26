package com.peeredge.core.common.ModelInterfaces;

import com.peeredge.core.common.ListenerInterfaces.CallEvents;
import com.peeredge.core.common.Models.CallState;
import com.peeredge.core.common.Models.CallType;

/**
 * Created by samson on 3/25/17.
 */
public interface Call {

    /**
     * will have transitioned through the disconnected state and will no longer exist.
     */
    String getID();

    /**
     * will Return .
     */
    CallType getType();

    /**
     * will have transitioned through the disconnected state and will no longer exist.
     */
    boolean isVideoEnabled();

    /**
     * will have transitioned through the disconnected state and will no longer exist.
     */
    boolean isOnHold();

    /**
     * will have transitioned through the disconnected state and will no longer exist.
     */
    boolean isActive();

    /**
     * will have transitioned through the disconnected state and will no longer exist.
     */
    boolean isIncoming();

    /**
     * will have transitioned through the disconnected state and will no longer exist.
     */
    boolean isTerminated();

    /**
     * will have transitioned through the disconnected state and will no longer exist.
     */
    String getRemoteCallNumber();


    /**
     * will have transitioned through the disconnected state and will no longer exist.
     */
    String getRemoteDisplay();

    /**
     * will have transitioned through the disconnected state and will no longer exist.
     */
    int getCallState();






    /**
     * will have transitioned through the disconnected state and will no longer exist.
     */
    void addCallEventListener(CallEvents.CallStateListener listener);

    /**
     * will have transitioned through the disconnected state and will no longer exist.
     */
    void removeCallEventListener(CallEvents.CallStateListener listener);

    /**
     * will have transitioned through the disconnected state and will no longer exist.
     */
    void removeAllCallEventListener();


    /**
     * will have transitioned through the disconnected state and will no longer exist.
     */
    void hold();

    /**
     * will have transitioned through the disconnected state and will no longer exist.
     */
    void unHold();

    /**
     * will have transitioned through the disconnected state and will no longer exist.
     */
    void accept();

    /**
     * Should be used for rejecting incoming call or terminating call.
     */
    void hangup();

    /**
     * Should be used for rejecting incoming call or terminating call.
     */
    void sendDtmf(char digit);




}
