package com.peeredge.core.common.ListenerInterfaces;

/**
 * Created by samson on 3/25/17.
 */
public interface AudioEventListener {
    void onAudioRouteChanged(int route);
    void onMicStateChanged(boolean mic);
}
