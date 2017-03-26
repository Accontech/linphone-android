package com.peeredge.core.unknown;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.peeredge.core.common.ContextProvider;
import com.peeredge.core.common.ListenerInterfaces.CallEvents;
import com.peeredge.core.common.ListenerInterfaces.CallStackEvents;
import com.peeredge.core.common.ModelInterfaces.Call;
import com.peeredge.core.common.Models.CallState;
import com.peeredge.core.common.Stack;
import com.peeredge.core.common.StackProvider;
import com.peeredge.test.CallsAdapter;

import org.linphone.LinphoneActivity;
import org.linphone.R;


public class MainActivity extends Activity implements View.OnClickListener, CallStackEvents.IncomingCallListener, CallEvents.CallStateListener {

    Stack stack;
    View answer, reject, hangup, hold, unhold, mute, unmute;
    TextView status;
    TextView number;
    CallsAdapter adapter = new CallsAdapter();
    com.peeredge.core.common.ModelInterfaces.Call call;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        ContextProvider._context = getApplicationContext();
        stack = StackProvider.getStack();

        findViewById(R.id.btn_linphone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, LinphoneActivity.class));
            }
        });
        answer = findViewById(R.id.btn_accept);
        reject = findViewById(R.id.btn_reject);
        hangup = findViewById(R.id.btn_hangup);
        hold = findViewById(R.id.btn_hold);
        unhold = findViewById(R.id.btn_unhold);
        mute = findViewById(R.id.btn_mute);
        unmute = findViewById(R.id.btn_unmute);
        status = (TextView) findViewById(R.id.textView);
        number = (TextView) findViewById(R.id.txt_number);

        answer.setOnClickListener(this);
        reject.setOnClickListener(this);
        hangup.setOnClickListener(this);
        hold.setOnClickListener(this);
        mute.setOnClickListener(this);
        answer.setOnClickListener(this);
        unmute.setOnClickListener(this);
        unhold.setOnClickListener(this);

        LinearLayoutManager linearLayoutManagerVertical =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_calls);
        recyclerView.setLayoutManager(linearLayoutManagerVertical);
        recyclerView.setAdapter(adapter);
        adapter.setListener(new CallsAdapter.ClickListener() {
            @Override
            public void onItemClicked(Call call, View view) {
                MainActivity.this.call = call;
                invalidateCallUI();
            }
        });


    }

    boolean hasHigherPriority(Call call1, Call call2)
    {
        if(call2 == null && call1 != null)
            return true;
        if (call1.isIncoming())
            return true;
        if(call2.isIncoming())
            return false;
        if(call1.isActive() && !call2.isActive())
            return true;

        return false;
    }
    void invalidateCallUI()
    {
        Call[] calls = stack.getActiveCalls();
        if(call != null && call.isTerminated())
        {
            call = null;
        }

        if(stack.getActiveCalls() != null)
        {

            if (call == null)
            {
                call = calls[0];
            }

        }
        showUI(call);
        adapter.setCalls(calls, call);
    }

    void showUI(Call call)
    {
        if(call == null) {
            status.setText("There is no Call");
            number.setText("Number");

        }
        else {
            status.setText(call.getType() + " : " + CallState.toString(call.getCallState()));
            number.setText(call.getRemoteDisplay());
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        invalidateCallUI();
        stack.addNewCallListener(this);
        addCallListeners();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stack.removeIncomingCallListener(this);
        removeCallListeners();
        call = null;
    }

    void addCallListeners()
    {
        Call[] calls = stack.getActiveCalls();
        if(calls != null)
        {
            for (Call call : calls)
                call.addCallEventListener(this);
        }
    }
    void removeCallListeners()
    {
        Call[] calls = stack.getActiveCalls();
        if(calls != null)
        {
            for (Call call : calls)
                call.removeCallEventListener(this);
        }
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
        invalidateCallUI();
        this.call.addCallEventListener(this);
        return false;
    }

    @Override
    public void onStateChange(Call call) {
        invalidateCallUI();
    }
}
