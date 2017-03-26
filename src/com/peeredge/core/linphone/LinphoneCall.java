package com.peeredge.core.linphone;

import com.peeredge.core.common.ListenerInterfaces.CallEvents;
import com.peeredge.core.common.ModelInterfaces.Call;
import com.peeredge.core.common.Models.CallState;
import com.peeredge.core.common.Models.CallType;

import org.linphone.LinphoneManager;
import org.linphone.core.LinphoneCallStats;
import org.linphone.core.LinphoneCore;
import org.linphone.core.LinphoneCoreListener;
import org.linphone.core.LinphoneCoreListenerBase;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by samson on 3/25/17.
 */
public class LinphoneCall implements Call{
    private org.linphone.core.LinphoneCall nativeCall;
    private int mCallState;
    private boolean mIsIncoming;
    private volatile Set<CallEvents.CallStateListener> listeners = new HashSet<>();

    public LinphoneCall(org.linphone.core.LinphoneCall call)
    {
        nativeCall = call;
        LinphoneCore lc = LinphoneManager.getLc();
        lc.addListener();

    }

    @Override
    public String getID() {
        return nativeCall.getCallLog().getCallId();
    }

    @Override
    public CallType getType() {
        return CallType.LINPHONE;
    }

    @Override
    public boolean isVideoEnabled() {
        return nativeCall.getVideoStats().equals(LinphoneCallStats.MediaType.Video) ;
    }

    @Override
    public boolean isOnHold() {
        return mCallState == CallState.ONHOLD;
    }

    @Override
    public boolean isActive() {
        return mCallState == CallState.CONNECTING || mCallState == CallState.ACTIVE;
    }

    @Override
    public boolean isIncoming() {
        return mIsIncoming;
    }

    @Override
    public boolean isTerminated() {
         return mCallState == CallState.DISCONNECTED || mCallState == CallState.DISCONNECTING || mCallState == CallState.INVALID;;
    }

    @Override
    public String getRemoteCallNumber() {
        return nativeCall.getRemoteContact();
    }

    @Override
    public String getRemoteDisplay() {
        return nativeCall.getRemoteContact();
    }

    @Override
    public int getCallState() {
        return mCallState;
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



    class CallEventLisener extends LinphoneCoreListenerBase
    {
        @Override
        public void callState(LinphoneCore lc, org.linphone.core.LinphoneCall call, org.linphone.core.LinphoneCall.State state, String message) {
            if(call != nativeCall)
                return;
        }
    }


}
