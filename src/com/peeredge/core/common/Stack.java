package com.peeredge.core.common;

import android.telecom.InCallService;

import com.peeredge.core.common.ListenerInterfaces.AudioEventListener;
import com.peeredge.core.common.ListenerInterfaces.CallStackEvents;
import com.peeredge.core.common.ModelInterfaces.AccountConfig;
import com.peeredge.core.common.ModelInterfaces.Call;
import com.peeredge.core.common.Models.CallType;
import com.peeredge.core.gsm.GsmCall;

/**
 * Created by samson on 3/25/17.
 */
public interface Stack {

    void setInCallService(InCallService service);
    void clearInCallService();

    void configure(AccountConfig config);
    void addNewCallListener(CallStackEvents.IncomingCallListener listener);
    void removeIncomingCallListener(CallStackEvents.IncomingCallListener listener);






    /**
     * Activity will be started only if there is no listener for incoming call.
     */
    void setIncomingCallActivity(Class<?> cls);

    /**
     * INTERNAL USE .
     */
    void add_new_call(Call call);

    Call getCallById(String id);
    Call[] getActiveCalls();


    //AUDIO SECTION
    //INTERNAL USER
    void on_audio_route_changed(CallType callType, int route);
    void on_mute_changed(CallType callType, boolean isMuted);
    //Public user
    void addAudioEventListener(Call call, AudioEventListener listener);
    void removeAudioEventListener(Call call, AudioEventListener listener);
    int getAudioSelectedRoute(CallType callType);
    boolean isMicMuted(CallType callType);
    void setMute(Call call, boolean digit);
    void selectAudioRoute(Call call, int route);


}
