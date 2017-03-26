package com.peeredge.core.linphone;

import com.peeredge.core.common.ListenerInterfaces.CallEvents;
import com.peeredge.core.common.ModelInterfaces.Call;
import com.peeredge.core.common.Models.CallType;

/**
 * Created by samson on 3/25/17.
 */
public class LinphoneCall implements Call{
    org.linphone.core.LinphoneCall nativeCall;
    public LinphoneCall(org.linphone.core.LinphoneCall call)
    {
        nativeCall = call;
    }

    @Override
    public String getID() {
        return null;
    }

    @Override
    public CallType getType() {
        return null;
    }

    @Override
    public boolean isVideoEnabled() {
        return false;
    }

    @Override
    public boolean isOnHold() {
        return false;
    }

    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public boolean isIncoming() {
        return false;
    }

    @Override
    public boolean isTerminated() {
        return false;
    }

    @Override
    public String getRemoteCallNumber() {
        return null;
    }

    @Override
    public String getRemoteDisplay() {
        return null;
    }

    @Override
    public int getCallState() {
        return 0;
    }

    @Override
    public void addCallEventListener(CallEvents.CallStateListener listener) {

    }

    @Override
    public void removeCallEventListener(CallEvents.CallStateListener listener) {

    }

    @Override
    public void removeAllCallEventListener() {

    }

    @Override
    public void hold() {

    }

    @Override
    public void unHold() {

    }

    @Override
    public void accept() {

    }

    @Override
    public void hangup() {

    }

    @Override
    public void sendDtmf(char digit) {

    }

}
