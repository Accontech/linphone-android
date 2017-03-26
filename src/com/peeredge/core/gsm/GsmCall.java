package com.peeredge.core.gsm;

import android.telecom.InCallService;
import android.telecom.VideoProfile;

import com.peeredge.core.common.ListenerInterfaces.CallEvents;
import com.peeredge.core.common.ModelInterfaces.Call;
import com.peeredge.core.common.Models.CallState;
import com.peeredge.core.common.Models.CallType;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by samson on 3/25/17.
 */
public class GsmCall  implements Call {
    private static int sIdCounter = 0;

    private String mID;
    android.telecom.Call mNativeCall;
    private int mCallState;
    private boolean mIsIncoming;
    private String mRemoteNumber;
    private GsmCallCallback callCallback = new GsmCallCallback();
    private volatile Set<CallEvents.CallStateListener> listeners = new HashSet<>();
    public GsmCall(android.telecom.Call call)
    {
        mNativeCall = call;
        mNativeCall.registerCallback(callCallback);
        mRemoteNumber = "111";
        mID = "prefix" + sIdCounter ++;

        int translatedState = translateState(call.getState());
        if(mCallState != translatedState)
        {
            setCallState(translatedState);
        }
    }

    @Override
    public String getID() {
        return mID;
    }

    @Override
    public CallType getType() {
        return CallType.GSM;
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
        return mRemoteNumber;
    }

    @Override
    public String getRemoteDisplay() {
        return mRemoteNumber;
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
        mNativeCall.hold();
    }

    @Override
    public void unHold() {
        mNativeCall.unhold();
    }

    @Override
    public void accept() {
        mNativeCall.answer(VideoProfile.STATE_AUDIO_ONLY);
    }

    @Override
    public void hangup() {
        if(getCallState() == CallState.INCOMING)
        {
            mNativeCall.reject(false, null);
        }
        else
            mNativeCall.disconnect();
    }

    @Override
    public void sendDtmf(char digit) {

    }

    private void setCallState(int translatedState) {
        mCallState = translatedState;
        if(mCallState == CallState.INCOMING)
            mIsIncoming = true;

        Iterator <CallEvents.CallStateListener> iterator = listeners.iterator();
        while (iterator.hasNext())
        {
            CallEvents.CallStateListener listener = iterator.next();
            listener.onStateChange(this);
        }
    }



    private static int translateState(int state) {
        switch (state) {
            case android.telecom.Call.STATE_NEW:
            case android.telecom.Call.STATE_CONNECTING:
                return CallState.CONNECTING;
            case android.telecom.Call.STATE_SELECT_PHONE_ACCOUNT:
                return CallState.SELECT_ACCOUNT;
            case android.telecom.Call.STATE_DIALING:
                return CallState.CONNECTING;
            case android.telecom.Call.STATE_RINGING:
                return CallState.INCOMING;
            case android.telecom.Call.STATE_ACTIVE:
                return CallState.ACTIVE;
            case android.telecom.Call.STATE_HOLDING:
                return CallState.ONHOLD;
            case android.telecom.Call.STATE_DISCONNECTED:
                return CallState.DISCONNECTED;
            case android.telecom.Call.STATE_DISCONNECTING:
                return CallState.DISCONNECTING;
            default:
                return CallState.INVALID;
        }
    }


    public class GsmCallCallback extends android.telecom.Call.Callback {
        @Override
        public void onStateChanged(android.telecom.Call call, int state) {
            super.onStateChanged(call, state);
            int translatedState = translateState(state);
            if(mCallState != translatedState)
            {
                setCallState(translatedState);
            }


        }

        @Override
        public void onParentChanged(android.telecom.Call call, android.telecom.Call parent) {
            super.onParentChanged(call, parent);
        }

        @Override
        public void onChildrenChanged(android.telecom.Call call, List<android.telecom.Call> children) {
            super.onChildrenChanged(call, children);
        }

        @Override
        public void onDetailsChanged(android.telecom.Call call, android.telecom.Call.Details details) {
            super.onDetailsChanged(call, details);
        }

        @Override
        public void onCannedTextResponsesLoaded(android.telecom.Call call, List<String> cannedTextResponses) {
            super.onCannedTextResponsesLoaded(call, cannedTextResponses);
        }

        @Override
        public void onPostDialWait(android.telecom.Call call, String remainingPostDialSequence) {
            super.onPostDialWait(call, remainingPostDialSequence);
        }

        @Override
        public void onVideoCallChanged(android.telecom.Call call, InCallService.VideoCall videoCall) {
            super.onVideoCallChanged(call, videoCall);
        }

        @Override
        public void onCallDestroyed(android.telecom.Call call) {
            super.onCallDestroyed(call);
            mNativeCall.unregisterCallback(callCallback);
            listeners.clear();

        }

        @Override
        public void onConferenceableCallsChanged(android.telecom.Call call, List<android.telecom.Call> conferenceableCalls) {
            super.onConferenceableCallsChanged(call, conferenceableCalls);
        }
    }


}
