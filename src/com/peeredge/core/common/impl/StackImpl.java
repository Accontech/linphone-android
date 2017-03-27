package com.peeredge.core.common.impl;

import android.content.Context;
import android.content.Intent;
import android.telecom.InCallService;

import com.peeredge.core.common.Contants;
import com.peeredge.core.common.ContextProvider;
import com.peeredge.core.common.ListenerInterfaces.AudioEventListener;
import com.peeredge.core.common.ListenerInterfaces.CallEvents;
import com.peeredge.core.common.ListenerInterfaces.CallStackEvents;
import com.peeredge.core.common.ModelInterfaces.AccountConfig;
import com.peeredge.core.common.ModelInterfaces.Call;
import com.peeredge.core.common.Models.CallState;
import com.peeredge.core.common.Models.CallType;
import com.peeredge.core.common.Stack;

import org.linphone.LinphoneService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by samson on 3/25/17.
 */
public class StackImpl implements Stack , CallEvents.CallStateListener{
    private Class<?> cls;
    private Map<String, Call> calls = new HashMap<>();
    private ArrayList<CallStackEvents.IncomingCallListener> incomingCallListeners = new ArrayList<>();
    private Map<CallType, Set<AudioEventListener>> audioEventListener = new HashMap<>();
    private InCallService inCallService;
    private int audioRoute;
    private boolean isMuted;
   // private LinphoneManager linphoneManager;

    public StackImpl()
    {
        audioEventListener.put(CallType.GSM, new HashSet<AudioEventListener>());
        audioEventListener.put(CallType.LINPHONE, new HashSet<AudioEventListener>());
        if(!LinphoneService.isReady())
        {
            ContextProvider.getContext().startService(new Intent(Intent.ACTION_MAIN).setClass(ContextProvider.getContext(), LinphoneService.class));
        }
    }
    @Override
    public void setInCallService(InCallService service) {
        if(service != null)
            inCallService = service;
    }

    @Override
    public void clearInCallService() {
        inCallService = null;
    }

    @Override
    public void configure(AccountConfig config) {
        //configure sip Account
    }

    @Override
    public void addNewCallListener(CallStackEvents.IncomingCallListener listener) {
        if(listener != null) {
            incomingCallListeners.add(listener);
        }

    }

    @Override
    public void removeIncomingCallListener(CallStackEvents.IncomingCallListener listener) {
        if(incomingCallListeners.contains(listener))
            incomingCallListeners.remove(listener);
    }

    @Override
    public void addAudioEventListener(Call call, AudioEventListener listener) {
        Set<AudioEventListener> listeners = audioEventListener.get(call.getType());
        if (listener != null &&  listeners != null)
        {
            listeners.add(listener);
        }
    }

    @Override
    public void removeAudioEventListener(Call call, AudioEventListener listener) {
        Set<AudioEventListener> listeners = audioEventListener.get(call.getType());
        if(listeners != null && listeners.contains(listener))
            listeners.remove(listener);
    }


    @Override
    public int getAudioSelectedRoute(CallType call) {
        return audioRoute;
    }

    @Override
    public boolean isMicMuted(CallType call) {
        return isMuted;
    }

    @Override
    public void setIncomingCallActivity(Class<?> cls) {
        this.cls = cls;
    }

    @Override
    public void add_new_call(Call call) {
        if(!call.isTerminated() && !calls.containsKey(call.getID()))
        {
            calls.put(call.getID(), call);
            call.addCallEventListener(this);
            if(call.isIncoming())
            {
                if(incomingCallListeners.size() == 0)
                {
                    //start activity
                    startCallActivity(call);
                }
                else
                {
                    notifyNewCall(call);
                }

            }
            else
            {
                if(incomingCallListeners.size() == 0)
                {
                    startCallActivity(call);
                }
                else
                {
                    notifyNewCall(call);
                }

            }

        }
    }


    private void hold_calls_except(Call call) {
        for(Call callItem : calls.values())
        {
            if(callItem != call && call.getCallState() == CallState.ACTIVE)
                callItem.hold();
        }
    }

    private void startCallActivity(Call call)
    {
        Context context = ContextProvider.getContext();
        Intent intent = new Intent(context, cls);
        intent.putExtra(Contants.CALL_ID_EXTRA, call.getID());
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
    @Override
    public Call getCallById(String id) {
        return calls.get(id);
    }

    @Override
    public Call[] getActiveCalls() {
        if(calls.size() == 0)
            return null;
        ArrayList<Call> callsArrayList = new ArrayList<>();
        for (Call call : calls.values())
        {
            if(!call.isTerminated())
                callsArrayList.add(call);
        }
        if(callsArrayList.size() == 0)
            return null;
        return callsArrayList.toArray(new Call[callsArrayList.size()]);
    }

    @Override
    public void on_audio_route_changed(CallType callType, int route) {
        audioRoute = route;
        notifyAudioRouteChanged(callType, route);
    }

    @Override
    public void on_mute_changed(CallType callType, boolean isMuted) {
        this.isMuted = isMuted;
        notifyMicStateChanged(callType, isMuted);
    }


    @Override
    public void setMute(Call call, boolean isMuted) {
        if(call.getType() == CallType.GSM)
        {
            inCallService.setMuted(isMuted);
        }
        else if(call.getType() == CallType.LINPHONE)
        {

        }
    }

    @Override
    public void selectAudioRoute(Call call, int audioRoute) {
        if(call.getType() == CallType.GSM)
        {
            inCallService.setAudioRoute(audioRoute);
        }
        else if(call.getType() == CallType.LINPHONE)
        {

        }
    }

    @Override
    public void onStateChange(Call call) {
        if(call.isTerminated())
        {
            //call.removeCallEventListener(this);
            calls.remove(call.getID());
        }
        if(call.getCallState() == CallState.ACTIVE)
            hold_calls_except(call);
    }




    private void notifyNewCall(Call call)
    {
        for (int i = incomingCallListeners.size() -1; i >= 0; i--)
        {
            CallStackEvents.IncomingCallListener listener = incomingCallListeners.get(i);
            if(listener.onNewCall(call))
                break;
        }
    }

    private void notifyAudioRouteChanged(CallType type, int audioRoute)
    {
        Set<AudioEventListener> listeners = audioEventListener.get(type);
        for(AudioEventListener listener : listeners)
            listener.onAudioRouteChanged(audioRoute);
    }

    private void notifyMicStateChanged(CallType type, boolean isMuted)
    {
        Set<AudioEventListener> listeners = audioEventListener.get(type);
        for(AudioEventListener listener : listeners)
            listener.onMicStateChanged(isMuted);
    }
}
