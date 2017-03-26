package com.peeredge.core.unknown;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.peeredge.core.common.ContextProvider;
import com.peeredge.core.common.ListenerInterfaces.CallEvents;
import com.peeredge.core.common.ListenerInterfaces.CallStackEvents;
import com.peeredge.core.common.ModelInterfaces.*;
import com.peeredge.core.common.ModelInterfaces.Call;
import com.peeredge.core.common.Models.CallState;
import com.peeredge.core.common.Stack;
import com.peeredge.core.common.StackProvider;

import org.linphone.R;


public class MainActivity extends Activity implements View.OnClickListener, CallStackEvents.IncomingCallListener, CallEvents.CallStateListener {

    Stack stack;
    View answer, reject, hangup, hold, unhold, mute, unmute;
    TextView status;
    com.peeredge.core.common.ModelInterfaces.Call call;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        ContextProvider._context = getApplicationContext();
        stack = StackProvider.getStack();
        answer = findViewById(R.id.btn_accept);
        reject = findViewById(R.id.btn_reject);
        hangup = findViewById(R.id.btn_hangup);
        hold = findViewById(R.id.btn_hold);
        unhold = findViewById(R.id.btn_unhold);
        mute = findViewById(R.id.btn_mute);
        unmute = findViewById(R.id.btn_unmute);
        status = (TextView) findViewById(R.id.textView);

        answer.setOnClickListener(this);
        reject.setOnClickListener(this);
        hangup.setOnClickListener(this);
        hold.setOnClickListener(this);
        mute.setOnClickListener(this);
        answer.setOnClickListener(this);
        unmute.setOnClickListener(this);
    }

    boolean hasHigherPriority(Call call1, Call call2)
    {
        if(call2 == null && call1 != null)
            return true;
        if (call1.isIncoming())
            return true;
        if(call1.isActive() && !call2.isIncoming())
            return true;

        return false;
    }
    void getCall()
    {
        call = null;
        if(stack.getActiveCalls() != null)
        {
            Call[] calls = stack.getActiveCalls();
            for (Call callitem : calls)
            {
                if (hasHigherPriority(callitem, call))
                    call = callitem;
            }

        }
        showUI(call);
    }

    void showUI(Call call)
    {
        if(call == null)
            status.setText("There is no Call");
        else
            status.setText(CallState.toString(call.getCallState()));
    }

    @Override
    protected void onResume() {
        super.onResume();
        getCall();
        stack.addNewCallListener(this);
        if(call != null)
            call.addCallEventListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        stack.removeIncomingCallListener(this);
        if(call != null)
            call.removeCallEventListener(this);
        call = null;
    }

    @Override
    public void onClick(View view) {
        if(call == null)
            return;
        switch (view.getId())
        {
            case R.id.btn_accept:
                call.accept();
                break;
            case R.id.btn_reject:
                call.hangup();
                break;
            case R.id.btn_hangup:
                call.hangup();
                break;
            case R.id.btn_hold:
                call.hold();
                break;
            case R.id.btn_unhold:
                call.unHold();
                break;
            case R.id.btn_mute:
                stack.setMute(call, true);
                break;
            case R.id.btn_unmute:
                stack.setMute(call, false);
                break;
        }

    }

    @Override
    public boolean onNewCall(Call call) {
        getCall();
        this.call.addCallEventListener(this);
        return false;
    }

    @Override
    public void onStateChange(Call call) {
        if (call.getCallState() == CallState.DISCONNECTED)
        {
            getCall();
            return;
        }
        showUI(call);
    }
}
