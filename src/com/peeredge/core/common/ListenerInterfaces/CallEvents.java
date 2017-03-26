package com.peeredge.core.common.ListenerInterfaces;

import com.peeredge.core.common.ModelInterfaces.Call;

/**
 * Created by samson on 3/25/17.
 */
public class CallEvents {
    public interface CallStateListener {
        public void onStateChange(Call call);
    }
}
