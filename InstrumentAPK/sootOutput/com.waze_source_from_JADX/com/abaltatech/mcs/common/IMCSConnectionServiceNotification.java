package com.abaltatech.mcs.common;

public interface IMCSConnectionServiceNotification extends IMCSConnectionClosedNotification {
    void onConnectionEstablished(IMCSDataLayer iMCSDataLayer) throws ;

    void onConnectionRequestFailed() throws ;
}
