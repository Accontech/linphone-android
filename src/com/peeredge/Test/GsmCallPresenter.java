package com.peeredge.Test;

import android.telecom.Call;
import android.telecom.CallAudioState;
import android.telecom.InCallService;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by samson on 3/25/17.
 */
public class GsmCallPresenter {
    Set<Call> calls= new HashSet<>();

    CallAudioState lastAudioState;
    InCallService inCallService;

    public void setInCallService(InCallService inCallService)
    {
        this.inCallService = inCallService;
    }


    public void onCallAudioStateChanged(CallAudioState audioState) {
        lastAudioState = audioState;
    }

    public void onBringToForeground(boolean showDialpad) {
    }

    public void onCallAdded(Call call) {
        calls.add(call);
    }

    public void onCallRemoved(Call call) {
        calls.remove(call);
    }




    public void onCanAddCallChanged(boolean canAddCall) {
//        InCallPresenter.getInstance().onCanAddCallChanged(canAddCall);
    }



}
