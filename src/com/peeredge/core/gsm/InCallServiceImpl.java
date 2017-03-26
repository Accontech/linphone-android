package com.peeredge.core.gsm;

/**
 * Created by samson on 3/24/17.
 */
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.telecom.Call;
import android.telecom.CallAudioState;
import android.telecom.InCallService;
import android.util.Log;

import com.peeredge.core.common.ContextProvider;
import com.peeredge.core.common.Models.CallType;
import com.peeredge.core.common.Stack;
import com.peeredge.core.common.StackProvider;

import com.peeredge.core.unknown.MainActivity;

/**
 * Used to receive updates about calls from the Telecom component.  This service is bound to
 * Telecom while there exist calls which potentially require UI. This includes ringing (incoming),
 * dialing (outgoing), and active calls. When the last call is disconnected, Telecom will unbind to
 * the service triggering InCallActivity (via CallList) to finish soon after.
 */
public class InCallServiceImpl extends InCallService {

    Stack stack;
    @Override
    public void onCallAudioStateChanged(CallAudioState audioState) {


        if(stack.getAudioSelectedRoute(CallType.GSM) != audioState.getRoute())
        {
            stack.on_audio_route_changed(CallType.GSM, audioState.getRoute());
        }

        if(stack.isMicMuted(CallType.GSM) != audioState.isMuted())
            stack.on_mute_changed(CallType.GSM, audioState.isMuted());
    }

    @Override
    public void onBringToForeground(boolean showDialpad) {
        //InCallPresenter.getInstance().onBringToForeground(showDialpad);
    }

    @Override
    public void onCallAdded(Call call) {

        // terminate call and ask
        stack.add_new_call(new GsmCall(call));

       // InCallPresenter.getInstance().onCallAdded(call);
    }

    @Override
    public void onCallRemoved(Call call) {

       // InCallPresenter.getInstance().onCallRemoved(call);
    }

    @Override
    public void onCanAddCallChanged(boolean canAddCall) {
//        InCallPresenter.getInstance().onCanAddCallChanged(canAddCall);
    }

    @Override
    public IBinder onBind(Intent intent) {
        final Context context = getApplicationContext();
//        final ContactInfoCache contactInfoCache = ContactInfoCache.getInstance(context);
//        InCallPresenter.getInstance().setUp(
//                getApplicationContext(),
//                CallList.getInstance(),
//                new ExternalCallList(),
//                AudioModeProvider.getInstance(),
//                new StatusBarNotifier(context, contactInfoCache),
//                new ExternalCallNotifier(context, contactInfoCache),
//                contactInfoCache,
//                new ProximitySensor(
//                        context,
//                        AudioModeProvider.getInstance(),
//                        new AccelerometerListener(context))
//        );
//        InCallPresenter.getInstance().onServiceBind();
//        InCallPresenter.getInstance().maybeStartRevealAnimation(intent);
//        TelecomAdapter.getInstance().setInCallService(this);

        ContextProvider._context = getApplicationContext();
        stack = StackProvider.getStack();
        stack.setInCallService(this);


        return super.onBind(intent);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        super.onUnbind(intent);
        stack.clearInCallService();
//        InCallPresenter.getInstance().onServiceUnbind();
        tearDown();

        return false;
    }

    private void tearDown() {
        Log.v("test", "tearDown");
        // Tear down the InCall system
//        TelecomAdapter.getInstance().clearInCallService();
//        InCallPresenter.getInstance().tearDown();
    }
}