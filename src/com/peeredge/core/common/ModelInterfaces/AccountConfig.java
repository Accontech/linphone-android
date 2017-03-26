package com.peeredge.core.common.ModelInterfaces;

/**
 * Created by samson on 3/25/17.
 */
public interface AccountConfig {
    String getUsername();
    String getPassword();
    String getSipDomain();
    int getSipPassword();
    String getTransport();
}
