package com.peeredge.core.linphone;

import com.peeredge.core.common.ListenerInterfaces.CallEvents;
import com.peeredge.core.common.ModelInterfaces.Call;
import com.peeredge.core.common.Models.CallState;
import com.peeredge.core.common.Models.CallType;

import org.linphone.LinphoneManager;
import org.linphone.core.LinphoneCallStats;
import org.linphone.core.LinphoneCore;
import org.linphone.core.LinphoneCoreException;
import org.linphone.core.LinphoneCoreListener;
import org.linphone.core.LinphoneCoreListenerBase;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by samson on 3/25/17.
 */
public class LinphoneCall implements Call{
    private org.linphone.core.LinphoneCall nativeCall;
    private int mCallState;
    private boolean mIsIncoming;
    private volatile Set<CallEvents.CallStateListener> listeners = new HashSet<>();
    private LinphoneCore lc;
    private CallEventLisener listener = new CallEventLisener();

    public LinphoneCall(org.linphone.core.LinphoneCall call)
    {
        nativeCall = call;
        lc = LinphoneManager.getLc();
        lc.addListener(listener);

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
         return mCallState == CallState.DISCONNECTED || mCallState == CallState.DISCONNECTING || mCallState == CallState.INVALID;
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
        if(listener != null)
            listeners.add(listener);
    }

    @Override
    public void removeCallEventListener(CallEvents.CallStateListener listener) {
        if(listeners.contains(listener))
            listeners.remove(listener);

    }

    @Override
    public void removeAllCallEventListener() {

    }

    @Override
    public void hold() {
        lc.pauseCall(nativeCall);

    }

    @Override
    public void unHold() {
        lc.resumeCall(nativeCall);
    }

    @Override
    public void accept() {
        try {
            lc.acceptCall(nativeCall);
        } catch (LinphoneCoreException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void hangup() {
        LinphoneManager.getLc().terminateCall(nativeCall);
    }

    @Override
    public void sendDtmf(char digit) {

    }

    @Override
    public int getDuration() {
        return 0;
    }


    private void setCallState(int translatedState) {
        mCallState = translatedState;
        if(mCallState == CallState.INCOMING)
            mIsIncoming = true;

        Iterator<CallEvents.CallStateListener> iterator = listeners.iterator();
        while (iterator.hasNext())
        {
            CallEvents.CallStateListener listener = iterator.next();
            listener.onStateChange(this);
        }
        if (mCallState == CallState.DISCONNECTED) {
            lc.removeListener(listener);
            listeners.clear();
        }
    }


    private static int translateState(org.linphone.core.LinphoneCall.State state) {
        if( state == org.linphone.core.LinphoneCall.State.IncomingReceived ) return CallState.INCOMING ;
        else if( state == org.linphone.core.LinphoneCall.State.OutgoingInit) return CallState.CONNECTING ;
        else if( state == org.linphone.core.LinphoneCall.State.OutgoingProgress) return CallState.CONNECTING ;
        else if( state == org.linphone.core.LinphoneCall.State.OutgoingRinging) return CallState.CONNECTING ;
        else if( state == org.linphone.core.LinphoneCall.State.OutgoingEarlyMedia) return CallState.CONNECTING ;
        else if( state == org.linphone.core.LinphoneCall.State.Connected) return CallState.ACTIVE ;
        else if( state == org.linphone.core.LinphoneCall.State.StreamsRunning) return CallState.ACTIVE ;
        else if( state == org.linphone.core.LinphoneCall.State.Pausing) return CallState.ONHOLD ;
        else if( state == org.linphone.core.LinphoneCall.State.Paused ) return CallState.ONHOLD ;
        else if( state == org.linphone.core.LinphoneCall.State.Resuming) return CallState.ACTIVE ;
        else if( state == org.linphone.core.LinphoneCall.State.Refered ) return -1 ;
        else if( state == org.linphone.core.LinphoneCall.State.Error ) return CallState.DISCONNECTED ;
        else if( state == org.linphone.core.LinphoneCall.State.CallEnd) return CallState.DISCONNECTED ;
        else if( state == org.linphone.core.LinphoneCall.State.PausedByRemote) return CallState.ACTIVE ;
        else if( state == org.linphone.core.LinphoneCall.State.CallUpdatedByRemote) return CallState.ACTIVE ;
        else if( state == org.linphone.core.LinphoneCall.State.CallIncomingEarlyMedia ) return -1 ;
        else if( state == org.linphone.core.LinphoneCall.State.CallUpdating ) return -1 ;
        else if( state == org.linphone.core.LinphoneCall.State.CallReleased ) return CallState.DISCONNECTED ;
        else if( state == org.linphone.core.LinphoneCall.State.CallEarlyUpdatedByRemote) return -1 ;

        return -1;
    }

    class CallEventLisener extends LinphoneCoreListenerBase
    {
        @Override
        public void callState(LinphoneCore lc, org.linphone.core.LinphoneCall call, org.linphone.core.LinphoneCall.State state, String message) {
            if(call != nativeCall)
                return;
            int translatedState = translateState(state);
            if(translatedState < 0)
                return;

            if (mCallState != translatedState)
                setCallState(translatedState);
            //CallUpdatedByRemote accept call update
        }
    }


}
