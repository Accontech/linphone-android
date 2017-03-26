package com.peeredge.test;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.peeredge.core.common.ModelInterfaces.Call;
import com.peeredge.core.common.Models.CallState;
import com.peeredge.core.common.Models.CallType;

import org.linphone.R;

import java.util.ArrayList;

/**
 * Created by samson on 3/27/17.
 */

public class CallsAdapter extends RecyclerView.Adapter<CallsAdapter.CallsViewHolder> {
    private final int TYPE_INCOMING = 1;
    private final int TYPE_ACTIVE = 2;
    private Call[] calls;
    private int selectedCallPossition;
    private ClickListener listener;
    public void setCalls(Call[] calls, Call selectedCall)
    {
        this.calls = calls;
        selectedCallPossition = -1;
        if(calls != null) {
            for (int i = 0; i < calls.length; i++) {
                if (selectedCall == calls[i]) {
                    selectedCallPossition = i;
                    break;
                }
            }
        }
        notifyDataSetChanged();
    }

    public void setListener(ClickListener listener) {
        this.listener = listener;
    }

    @Override
    public CallsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(viewType == TYPE_INCOMING
                ? R.layout.test_call_item_incoming : R.layout.test_call_item_general, parent,false);
        return new CallsViewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(CallsViewHolder holder, int position) {
        holder.poss= position;
        Call call = calls[position];
        holder.number.setText(call.getRemoteDisplay());
        holder.type.setText(call.getType()== CallType.GSM ? "GSM" : "SIP");

        if(holder.viewType == TYPE_ACTIVE)
        {
            holder.status.setText(CallState.toString(call.getCallState()));
        }

        if (selectedCallPossition == position)
            holder.rootView.setBackgroundColor(Color.GRAY);
        else
            holder.rootView.setBackgroundColor(Color.TRANSPARENT);
    }

    @Override
    public int getItemViewType(int position) {
        //return super.getItemViewType(position);
        Call call = calls[position];
        if(call.getCallState() == CallState.INCOMING)
        {
            return TYPE_INCOMING;
        }
        return TYPE_ACTIVE;

    }

    @Override
    public int getItemCount() {
        return calls== null ? 0 : calls.length;
    }

    class CallsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        int poss;
        int viewType;
        Button accept, decline, hangup;
        TextView status, type, number;
        View rootView;
        public CallsViewHolder(View itemView, int viewType) {
            super(itemView);
            rootView = itemView;
            this.viewType = viewType;
            number = (TextView) itemView.findViewById(R.id.txt_number);
            type = (TextView) itemView.findViewById(R.id.txt_type);
            if(viewType == TYPE_ACTIVE)
            {
                hangup = (Button) itemView.findViewById(R.id.btn_hangup);
                status = (TextView) itemView.findViewById(R.id.txt_status);
                hangup.setOnClickListener(this);
            }
            else
            {
                accept = (Button) itemView.findViewById(R.id.btn_accept);
                decline = (Button) itemView.findViewById(R.id.btn_reject);
                accept.setOnClickListener(this);
                decline.setOnClickListener(this);
            }
            rootView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Call call = calls[poss];
            switch (view.getId())
            {
                case R.id.btn_hangup:
                case  R.id.btn_reject:
                    call.hangup();
                    return;
                case R.id.btn_accept:
                    call.accept();
                return;
            }
            if (listener != null)
            {
                listener.onItemClicked(call, view);
            }
        }
    }

    public interface ClickListener
    {
        void onItemClicked(Call call, View view);
    }


}
